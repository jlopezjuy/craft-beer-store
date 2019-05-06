package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Movimientos;
import com.craftbeerstore.application.repository.MovimientosRepository;
import com.craftbeerstore.application.repository.search.MovimientosSearchRepository;
import com.craftbeerstore.application.service.MovimientosService;
import com.craftbeerstore.application.service.dto.MovimientosDTO;
import com.craftbeerstore.application.service.mapper.MovimientosMapper;
import com.craftbeerstore.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;
import com.craftbeerstore.application.domain.enumeration.EstadoMovimiento;
/**
 * Test class for the MovimientosResource REST controller.
 *
 * @see MovimientosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class MovimientosResourceIntTest {

    private static final TipoMovimiento DEFAULT_TIPO_MOVIMIENTO = TipoMovimiento.PRESUPUESTO;
    private static final TipoMovimiento UPDATED_TIPO_MOVIMIENTO = TipoMovimiento.VENTA;

    private static final LocalDate DEFAULT_FECHA_MOVIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOVIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(2);

    private static final String DEFAULT_NUMERO_MOVIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_MOVIMIENTO = "BBBBBBBBBB";

    private static final EstadoMovimiento DEFAULT_ESTADO = EstadoMovimiento.ACTIVO;
    private static final EstadoMovimiento UPDATED_ESTADO = EstadoMovimiento.INACTIVO;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private MovimientosMapper movimientosMapper;

    @Autowired
    private MovimientosService movimientosService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.MovimientosSearchRepositoryMockConfiguration
     */
    @Autowired
    private MovimientosSearchRepository mockMovimientosSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMovimientosMockMvc;

    private Movimientos movimientos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovimientosResource movimientosResource = new MovimientosResource(movimientosService);
        this.restMovimientosMockMvc = MockMvcBuilders.standaloneSetup(movimientosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimientos createEntity(EntityManager em) {
        Movimientos movimientos = new Movimientos()
            .tipoMovimiento(DEFAULT_TIPO_MOVIMIENTO)
            .fechaMovimiento(DEFAULT_FECHA_MOVIMIENTO)
            .precioTotal(DEFAULT_PRECIO_TOTAL)
            .numeroMovimiento(DEFAULT_NUMERO_MOVIMIENTO)
            .estado(DEFAULT_ESTADO);
        return movimientos;
    }

    @Before
    public void initTest() {
        movimientos = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovimientos() throws Exception {
        int databaseSizeBeforeCreate = movimientosRepository.findAll().size();

        // Create the Movimientos
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);
        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isCreated());

        // Validate the Movimientos in the database
        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeCreate + 1);
        Movimientos testMovimientos = movimientosList.get(movimientosList.size() - 1);
        assertThat(testMovimientos.getTipoMovimiento()).isEqualTo(DEFAULT_TIPO_MOVIMIENTO);
        assertThat(testMovimientos.getFechaMovimiento()).isEqualTo(DEFAULT_FECHA_MOVIMIENTO);
        assertThat(testMovimientos.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
        assertThat(testMovimientos.getNumeroMovimiento()).isEqualTo(DEFAULT_NUMERO_MOVIMIENTO);
        assertThat(testMovimientos.getEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the Movimientos in Elasticsearch
        verify(mockMovimientosSearchRepository, times(1)).save(testMovimientos);
    }

    @Test
    @Transactional
    public void createMovimientosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movimientosRepository.findAll().size();

        // Create the Movimientos with an existing ID
        movimientos.setId(1L);
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movimientos in the database
        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Movimientos in Elasticsearch
        verify(mockMovimientosSearchRepository, times(0)).save(movimientos);
    }

    @Test
    @Transactional
    public void checkTipoMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientosRepository.findAll().size();
        // set the field null
        movimientos.setTipoMovimiento(null);

        // Create the Movimientos, which fails.
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientosRepository.findAll().size();
        // set the field null
        movimientos.setFechaMovimiento(null);

        // Create the Movimientos, which fails.
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientosRepository.findAll().size();
        // set the field null
        movimientos.setPrecioTotal(null);

        // Create the Movimientos, which fails.
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientosRepository.findAll().size();
        // set the field null
        movimientos.setNumeroMovimiento(null);

        // Create the Movimientos, which fails.
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimientosRepository.findAll().size();
        // set the field null
        movimientos.setEstado(null);

        // Create the Movimientos, which fails.
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        restMovimientosMockMvc.perform(post("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovimientos() throws Exception {
        // Initialize the database
        movimientosRepository.saveAndFlush(movimientos);

        // Get all the movimientosList
        restMovimientosMockMvc.perform(get("/api/movimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimientos.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMovimiento").value(hasItem(DEFAULT_TIPO_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaMovimiento").value(hasItem(DEFAULT_FECHA_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].numeroMovimiento").value(hasItem(DEFAULT_NUMERO_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getMovimientos() throws Exception {
        // Initialize the database
        movimientosRepository.saveAndFlush(movimientos);

        // Get the movimientos
        restMovimientosMockMvc.perform(get("/api/movimientos/{id}", movimientos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movimientos.getId().intValue()))
            .andExpect(jsonPath("$.tipoMovimiento").value(DEFAULT_TIPO_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.fechaMovimiento").value(DEFAULT_FECHA_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.intValue()))
            .andExpect(jsonPath("$.numeroMovimiento").value(DEFAULT_NUMERO_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovimientos() throws Exception {
        // Get the movimientos
        restMovimientosMockMvc.perform(get("/api/movimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovimientos() throws Exception {
        // Initialize the database
        movimientosRepository.saveAndFlush(movimientos);

        int databaseSizeBeforeUpdate = movimientosRepository.findAll().size();

        // Update the movimientos
        Movimientos updatedMovimientos = movimientosRepository.findById(movimientos.getId()).get();
        // Disconnect from session so that the updates on updatedMovimientos are not directly saved in db
        em.detach(updatedMovimientos);
        updatedMovimientos
            .tipoMovimiento(UPDATED_TIPO_MOVIMIENTO)
            .fechaMovimiento(UPDATED_FECHA_MOVIMIENTO)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .numeroMovimiento(UPDATED_NUMERO_MOVIMIENTO)
            .estado(UPDATED_ESTADO);
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(updatedMovimientos);

        restMovimientosMockMvc.perform(put("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isOk());

        // Validate the Movimientos in the database
        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeUpdate);
        Movimientos testMovimientos = movimientosList.get(movimientosList.size() - 1);
        assertThat(testMovimientos.getTipoMovimiento()).isEqualTo(UPDATED_TIPO_MOVIMIENTO);
        assertThat(testMovimientos.getFechaMovimiento()).isEqualTo(UPDATED_FECHA_MOVIMIENTO);
        assertThat(testMovimientos.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
        assertThat(testMovimientos.getNumeroMovimiento()).isEqualTo(UPDATED_NUMERO_MOVIMIENTO);
        assertThat(testMovimientos.getEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the Movimientos in Elasticsearch
        verify(mockMovimientosSearchRepository, times(1)).save(testMovimientos);
    }

    @Test
    @Transactional
    public void updateNonExistingMovimientos() throws Exception {
        int databaseSizeBeforeUpdate = movimientosRepository.findAll().size();

        // Create the Movimientos
        MovimientosDTO movimientosDTO = movimientosMapper.toDto(movimientos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimientosMockMvc.perform(put("/api/movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Movimientos in the database
        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Movimientos in Elasticsearch
        verify(mockMovimientosSearchRepository, times(0)).save(movimientos);
    }

    @Test
    @Transactional
    public void deleteMovimientos() throws Exception {
        // Initialize the database
        movimientosRepository.saveAndFlush(movimientos);

        int databaseSizeBeforeDelete = movimientosRepository.findAll().size();

        // Delete the movimientos
        restMovimientosMockMvc.perform(delete("/api/movimientos/{id}", movimientos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Movimientos> movimientosList = movimientosRepository.findAll();
        assertThat(movimientosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Movimientos in Elasticsearch
        verify(mockMovimientosSearchRepository, times(1)).deleteById(movimientos.getId());
    }

    @Test
    @Transactional
    public void searchMovimientos() throws Exception {
        // Initialize the database
        movimientosRepository.saveAndFlush(movimientos);
        when(mockMovimientosSearchRepository.search(queryStringQuery("id:" + movimientos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(movimientos), PageRequest.of(0, 1), 1));
        // Search the movimientos
        restMovimientosMockMvc.perform(get("/api/_search/movimientos?query=id:" + movimientos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimientos.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMovimiento").value(hasItem(DEFAULT_TIPO_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaMovimiento").value(hasItem(DEFAULT_FECHA_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].numeroMovimiento").value(hasItem(DEFAULT_NUMERO_MOVIMIENTO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movimientos.class);
        Movimientos movimientos1 = new Movimientos();
        movimientos1.setId(1L);
        Movimientos movimientos2 = new Movimientos();
        movimientos2.setId(movimientos1.getId());
        assertThat(movimientos1).isEqualTo(movimientos2);
        movimientos2.setId(2L);
        assertThat(movimientos1).isNotEqualTo(movimientos2);
        movimientos1.setId(null);
        assertThat(movimientos1).isNotEqualTo(movimientos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientosDTO.class);
        MovimientosDTO movimientosDTO1 = new MovimientosDTO();
        movimientosDTO1.setId(1L);
        MovimientosDTO movimientosDTO2 = new MovimientosDTO();
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
        movimientosDTO2.setId(movimientosDTO1.getId());
        assertThat(movimientosDTO1).isEqualTo(movimientosDTO2);
        movimientosDTO2.setId(2L);
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
        movimientosDTO1.setId(null);
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movimientosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movimientosMapper.fromId(null)).isNull();
    }
}
