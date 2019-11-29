package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.repository.LoteRepository;
import com.craftbeerstore.application.service.LoteService;
import com.craftbeerstore.application.service.dto.LoteDTO;
import com.craftbeerstore.application.service.mapper.LoteMapper;
import com.craftbeerstore.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.EstadoLote;
/**
 * Integration tests for the {@link LoteResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class LoteResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_COCCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COCCION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_COCCION = 1;
    private static final Integer UPDATED_COCCION = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESCUENTA_STOCK = false;
    private static final Boolean UPDATED_DESCUENTA_STOCK = true;

    private static final EstadoLote DEFAULT_ESTADO = EstadoLote.EN_PROCESO;
    private static final EstadoLote UPDATED_ESTADO = EstadoLote.PLANIFICADO;

    private static final BigDecimal DEFAULT_LITROS_ESTIMADOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS_ESTIMADOS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LITROS_EN_TANQUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS_EN_TANQUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LITROS_ENVASADOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS_ENVASADOS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LITROS_DISPONIBLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS_DISPONIBLE = new BigDecimal(2);

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private LoteMapper loteMapper;

    @Autowired
    private LoteService loteService;

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

    private MockMvc restLoteMockMvc;

    private Lote lote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoteResource loteResource = new LoteResource(loteService);
        this.restLoteMockMvc = MockMvcBuilders.standaloneSetup(loteResource)
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
    public static Lote createEntity(EntityManager em) {
        Lote lote = new Lote()
            .codigo(DEFAULT_CODIGO)
            .fechaCoccion(DEFAULT_FECHA_COCCION)
            .coccion(DEFAULT_COCCION)
            .descripcion(DEFAULT_DESCRIPCION)
            .descuentaStock(DEFAULT_DESCUENTA_STOCK)
            .estado(DEFAULT_ESTADO)
            .litrosEstimados(DEFAULT_LITROS_ESTIMADOS)
            .litrosEnTanque(DEFAULT_LITROS_EN_TANQUE)
            .litrosEnvasados(DEFAULT_LITROS_ENVASADOS)
            .litrosDisponible(DEFAULT_LITROS_DISPONIBLE);
        return lote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createUpdatedEntity(EntityManager em) {
        Lote lote = new Lote()
            .codigo(UPDATED_CODIGO)
            .fechaCoccion(UPDATED_FECHA_COCCION)
            .coccion(UPDATED_COCCION)
            .descripcion(UPDATED_DESCRIPCION)
            .descuentaStock(UPDATED_DESCUENTA_STOCK)
            .estado(UPDATED_ESTADO)
            .litrosEstimados(UPDATED_LITROS_ESTIMADOS)
            .litrosEnTanque(UPDATED_LITROS_EN_TANQUE)
            .litrosEnvasados(UPDATED_LITROS_ENVASADOS)
            .litrosDisponible(UPDATED_LITROS_DISPONIBLE);
        return lote;
    }

    @BeforeEach
    public void initTest() {
        lote = createEntity(em);
    }

    @Test
    @Transactional
    public void createLote() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);
        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
            .andExpect(status().isCreated());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate + 1);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLote.getFechaCoccion()).isEqualTo(DEFAULT_FECHA_COCCION);
        assertThat(testLote.getCoccion()).isEqualTo(DEFAULT_COCCION);
        assertThat(testLote.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testLote.isDescuentaStock()).isEqualTo(DEFAULT_DESCUENTA_STOCK);
        assertThat(testLote.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testLote.getLitrosEstimados()).isEqualTo(DEFAULT_LITROS_ESTIMADOS);
        assertThat(testLote.getLitrosEnTanque()).isEqualTo(DEFAULT_LITROS_EN_TANQUE);
        assertThat(testLote.getLitrosEnvasados()).isEqualTo(DEFAULT_LITROS_ENVASADOS);
        assertThat(testLote.getLitrosDisponible()).isEqualTo(DEFAULT_LITROS_DISPONIBLE);
    }

    @Test
    @Transactional
    public void createLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote with an existing ID
        lote.setId(1L);
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLotes() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get all the loteList
        restLoteMockMvc.perform(get("/api/lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].fechaCoccion").value(hasItem(DEFAULT_FECHA_COCCION.toString())))
            .andExpect(jsonPath("$.[*].coccion").value(hasItem(DEFAULT_COCCION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].descuentaStock").value(hasItem(DEFAULT_DESCUENTA_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].litrosEstimados").value(hasItem(DEFAULT_LITROS_ESTIMADOS.intValue())))
            .andExpect(jsonPath("$.[*].litrosEnTanque").value(hasItem(DEFAULT_LITROS_EN_TANQUE.intValue())))
            .andExpect(jsonPath("$.[*].litrosEnvasados").value(hasItem(DEFAULT_LITROS_ENVASADOS.intValue())))
            .andExpect(jsonPath("$.[*].litrosDisponible").value(hasItem(DEFAULT_LITROS_DISPONIBLE.intValue())));
    }
    
    @Test
    @Transactional
    public void getLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", lote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.fechaCoccion").value(DEFAULT_FECHA_COCCION.toString()))
            .andExpect(jsonPath("$.coccion").value(DEFAULT_COCCION))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.descuentaStock").value(DEFAULT_DESCUENTA_STOCK.booleanValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.litrosEstimados").value(DEFAULT_LITROS_ESTIMADOS.intValue()))
            .andExpect(jsonPath("$.litrosEnTanque").value(DEFAULT_LITROS_EN_TANQUE.intValue()))
            .andExpect(jsonPath("$.litrosEnvasados").value(DEFAULT_LITROS_ENVASADOS.intValue()))
            .andExpect(jsonPath("$.litrosDisponible").value(DEFAULT_LITROS_DISPONIBLE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLote() throws Exception {
        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Update the lote
        Lote updatedLote = loteRepository.findById(lote.getId()).get();
        // Disconnect from session so that the updates on updatedLote are not directly saved in db
        em.detach(updatedLote);
        updatedLote
            .codigo(UPDATED_CODIGO)
            .fechaCoccion(UPDATED_FECHA_COCCION)
            .coccion(UPDATED_COCCION)
            .descripcion(UPDATED_DESCRIPCION)
            .descuentaStock(UPDATED_DESCUENTA_STOCK)
            .estado(UPDATED_ESTADO)
            .litrosEstimados(UPDATED_LITROS_ESTIMADOS)
            .litrosEnTanque(UPDATED_LITROS_EN_TANQUE)
            .litrosEnvasados(UPDATED_LITROS_ENVASADOS)
            .litrosDisponible(UPDATED_LITROS_DISPONIBLE);
        LoteDTO loteDTO = loteMapper.toDto(updatedLote);

        restLoteMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
            .andExpect(status().isOk());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLote.getFechaCoccion()).isEqualTo(UPDATED_FECHA_COCCION);
        assertThat(testLote.getCoccion()).isEqualTo(UPDATED_COCCION);
        assertThat(testLote.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testLote.isDescuentaStock()).isEqualTo(UPDATED_DESCUENTA_STOCK);
        assertThat(testLote.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLote.getLitrosEstimados()).isEqualTo(UPDATED_LITROS_ESTIMADOS);
        assertThat(testLote.getLitrosEnTanque()).isEqualTo(UPDATED_LITROS_EN_TANQUE);
        assertThat(testLote.getLitrosEnvasados()).isEqualTo(UPDATED_LITROS_ENVASADOS);
        assertThat(testLote.getLitrosDisponible()).isEqualTo(UPDATED_LITROS_DISPONIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingLote() throws Exception {
        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Create the Lote
        LoteDTO loteDTO = loteMapper.toDto(lote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoteMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeDelete = loteRepository.findAll().size();

        // Delete the lote
        restLoteMockMvc.perform(delete("/api/lotes/{id}", lote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
