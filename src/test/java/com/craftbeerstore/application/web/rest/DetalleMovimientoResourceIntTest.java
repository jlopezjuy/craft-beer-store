package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.DetalleMovimiento;
import com.craftbeerstore.application.repository.DetalleMovimientoRepository;
import com.craftbeerstore.application.service.DetalleMovimientoService;
import com.craftbeerstore.application.service.dto.DetalleMovimientoDTO;
import com.craftbeerstore.application.service.mapper.DetalleMovimientoMapper;
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
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetalleMovimientoResource REST controller.
 *
 * @see DetalleMovimientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class DetalleMovimientoResourceIntTest {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(2);

    @Autowired
    private DetalleMovimientoRepository detalleMovimientoRepository;

    @Autowired
    private DetalleMovimientoMapper detalleMovimientoMapper;

    @Autowired
    private DetalleMovimientoService detalleMovimientoService;

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

    private MockMvc restDetalleMovimientoMockMvc;

    private DetalleMovimiento detalleMovimiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleMovimientoResource detalleMovimientoResource = new DetalleMovimientoResource(detalleMovimientoService);
        this.restDetalleMovimientoMockMvc = MockMvcBuilders.standaloneSetup(detalleMovimientoResource)
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
    public static DetalleMovimiento createEntity(EntityManager em) {
        DetalleMovimiento detalleMovimiento = new DetalleMovimiento()
            .cantidad(DEFAULT_CANTIDAD)
            .precioTotal(DEFAULT_PRECIO_TOTAL);
        return detalleMovimiento;
    }

    @Before
    public void initTest() {
        detalleMovimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleMovimiento() throws Exception {
        int databaseSizeBeforeCreate = detalleMovimientoRepository.findAll().size();

        // Create the DetalleMovimiento
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(detalleMovimiento);
        restDetalleMovimientoMockMvc.perform(post("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleMovimiento in the database
        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleMovimiento testDetalleMovimiento = detalleMovimientoList.get(detalleMovimientoList.size() - 1);
        assertThat(testDetalleMovimiento.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetalleMovimiento.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void createDetalleMovimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleMovimientoRepository.findAll().size();

        // Create the DetalleMovimiento with an existing ID
        detalleMovimiento.setId(1L);
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(detalleMovimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleMovimientoMockMvc.perform(post("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleMovimiento in the database
        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleMovimientoRepository.findAll().size();
        // set the field null
        detalleMovimiento.setCantidad(null);

        // Create the DetalleMovimiento, which fails.
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(detalleMovimiento);

        restDetalleMovimientoMockMvc.perform(post("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleMovimientoRepository.findAll().size();
        // set the field null
        detalleMovimiento.setPrecioTotal(null);

        // Create the DetalleMovimiento, which fails.
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(detalleMovimiento);

        restDetalleMovimientoMockMvc.perform(post("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalleMovimientos() throws Exception {
        // Initialize the database
        detalleMovimientoRepository.saveAndFlush(detalleMovimiento);

        // Get all the detalleMovimientoList
        restDetalleMovimientoMockMvc.perform(get("/api/detalle-movimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleMovimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getDetalleMovimiento() throws Exception {
        // Initialize the database
        detalleMovimientoRepository.saveAndFlush(detalleMovimiento);

        // Get the detalleMovimiento
        restDetalleMovimientoMockMvc.perform(get("/api/detalle-movimientos/{id}", detalleMovimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleMovimiento.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleMovimiento() throws Exception {
        // Get the detalleMovimiento
        restDetalleMovimientoMockMvc.perform(get("/api/detalle-movimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleMovimiento() throws Exception {
        // Initialize the database
        detalleMovimientoRepository.saveAndFlush(detalleMovimiento);

        int databaseSizeBeforeUpdate = detalleMovimientoRepository.findAll().size();

        // Update the detalleMovimiento
        DetalleMovimiento updatedDetalleMovimiento = detalleMovimientoRepository.findById(detalleMovimiento.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleMovimiento are not directly saved in db
        em.detach(updatedDetalleMovimiento);
        updatedDetalleMovimiento
            .cantidad(UPDATED_CANTIDAD)
            .precioTotal(UPDATED_PRECIO_TOTAL);
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(updatedDetalleMovimiento);

        restDetalleMovimientoMockMvc.perform(put("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleMovimiento in the database
        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeUpdate);
        DetalleMovimiento testDetalleMovimiento = detalleMovimientoList.get(detalleMovimientoList.size() - 1);
        assertThat(testDetalleMovimiento.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetalleMovimiento.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleMovimiento() throws Exception {
        int databaseSizeBeforeUpdate = detalleMovimientoRepository.findAll().size();

        // Create the DetalleMovimiento
        DetalleMovimientoDTO detalleMovimientoDTO = detalleMovimientoMapper.toDto(detalleMovimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleMovimientoMockMvc.perform(put("/api/detalle-movimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleMovimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleMovimiento in the database
        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleMovimiento() throws Exception {
        // Initialize the database
        detalleMovimientoRepository.saveAndFlush(detalleMovimiento);

        int databaseSizeBeforeDelete = detalleMovimientoRepository.findAll().size();

        // Delete the detalleMovimiento
        restDetalleMovimientoMockMvc.perform(delete("/api/detalle-movimientos/{id}", detalleMovimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetalleMovimiento> detalleMovimientoList = detalleMovimientoRepository.findAll();
        assertThat(detalleMovimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleMovimiento.class);
        DetalleMovimiento detalleMovimiento1 = new DetalleMovimiento();
        detalleMovimiento1.setId(1L);
        DetalleMovimiento detalleMovimiento2 = new DetalleMovimiento();
        detalleMovimiento2.setId(detalleMovimiento1.getId());
        assertThat(detalleMovimiento1).isEqualTo(detalleMovimiento2);
        detalleMovimiento2.setId(2L);
        assertThat(detalleMovimiento1).isNotEqualTo(detalleMovimiento2);
        detalleMovimiento1.setId(null);
        assertThat(detalleMovimiento1).isNotEqualTo(detalleMovimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleMovimientoDTO.class);
        DetalleMovimientoDTO detalleMovimientoDTO1 = new DetalleMovimientoDTO();
        detalleMovimientoDTO1.setId(1L);
        DetalleMovimientoDTO detalleMovimientoDTO2 = new DetalleMovimientoDTO();
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO2.setId(detalleMovimientoDTO1.getId());
        assertThat(detalleMovimientoDTO1).isEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO2.setId(2L);
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO1.setId(null);
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detalleMovimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detalleMovimientoMapper.fromId(null)).isNull();
    }
}
