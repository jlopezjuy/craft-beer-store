package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.Presentacion;
import com.craftbeerstore.application.repository.PresentacionRepository;
import com.craftbeerstore.application.service.PresentacionService;
import com.craftbeerstore.application.service.dto.PresentacionDTO;
import com.craftbeerstore.application.service.mapper.PresentacionMapper;
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

import com.craftbeerstore.application.domain.enumeration.TipoPresentacion;
/**
 * Integration tests for the {@link PresentacionResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class PresentacionResourceIT {

    private static final TipoPresentacion DEFAULT_TIPO_PRESENTACION = TipoPresentacion.BOTELLA_330;
    private static final TipoPresentacion UPDATED_TIPO_PRESENTACION = TipoPresentacion.BOTELLA_355;

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_COSTO_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_COSTO_UNITARIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_VENTA_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_VENTA_UNITARIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_VENTA_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_VENTA_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_COSTO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_COSTO_TOTAL = new BigDecimal(2);

    @Autowired
    private PresentacionRepository presentacionRepository;

    @Autowired
    private PresentacionMapper presentacionMapper;

    @Autowired
    private PresentacionService presentacionService;

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

    private MockMvc restPresentacionMockMvc;

    private Presentacion presentacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PresentacionResource presentacionResource = new PresentacionResource(presentacionService);
        this.restPresentacionMockMvc = MockMvcBuilders.standaloneSetup(presentacionResource)
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
    public static Presentacion createEntity(EntityManager em) {
        Presentacion presentacion = new Presentacion()
            .tipoPresentacion(DEFAULT_TIPO_PRESENTACION)
            .cantidad(DEFAULT_CANTIDAD)
            .fecha(DEFAULT_FECHA)
            .costoUnitario(DEFAULT_COSTO_UNITARIO)
            .precioVentaUnitario(DEFAULT_PRECIO_VENTA_UNITARIO)
            .precioVentaTotal(DEFAULT_PRECIO_VENTA_TOTAL)
            .precioCostoTotal(DEFAULT_PRECIO_COSTO_TOTAL);
        return presentacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presentacion createUpdatedEntity(EntityManager em) {
        Presentacion presentacion = new Presentacion()
            .tipoPresentacion(UPDATED_TIPO_PRESENTACION)
            .cantidad(UPDATED_CANTIDAD)
            .fecha(UPDATED_FECHA)
            .costoUnitario(UPDATED_COSTO_UNITARIO)
            .precioVentaUnitario(UPDATED_PRECIO_VENTA_UNITARIO)
            .precioVentaTotal(UPDATED_PRECIO_VENTA_TOTAL)
            .precioCostoTotal(UPDATED_PRECIO_COSTO_TOTAL);
        return presentacion;
    }

    @BeforeEach
    public void initTest() {
        presentacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresentacion() throws Exception {
        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();

        // Create the Presentacion
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);
        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate + 1);
        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
        assertThat(testPresentacion.getTipoPresentacion()).isEqualTo(DEFAULT_TIPO_PRESENTACION);
        assertThat(testPresentacion.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testPresentacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPresentacion.getCostoUnitario()).isEqualTo(DEFAULT_COSTO_UNITARIO);
        assertThat(testPresentacion.getPrecioVentaUnitario()).isEqualTo(DEFAULT_PRECIO_VENTA_UNITARIO);
        assertThat(testPresentacion.getPrecioVentaTotal()).isEqualTo(DEFAULT_PRECIO_VENTA_TOTAL);
        assertThat(testPresentacion.getPrecioCostoTotal()).isEqualTo(DEFAULT_PRECIO_COSTO_TOTAL);
    }

    @Test
    @Transactional
    public void createPresentacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();

        // Create the Presentacion with an existing ID
        presentacion.setId(1L);
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoPresentacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setTipoPresentacion(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setCantidad(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setFecha(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostoUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setCostoUnitario(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioVentaUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setPrecioVentaUnitario(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioVentaTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setPrecioVentaTotal(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioCostoTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentacionRepository.findAll().size();
        // set the field null
        presentacion.setPrecioCostoTotal(null);

        // Create the Presentacion, which fails.
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPresentacions() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        // Get all the presentacionList
        restPresentacionMockMvc.perform(get("/api/presentacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presentacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPresentacion").value(hasItem(DEFAULT_TIPO_PRESENTACION.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].costoUnitario").value(hasItem(DEFAULT_COSTO_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].precioVentaUnitario").value(hasItem(DEFAULT_PRECIO_VENTA_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].precioVentaTotal").value(hasItem(DEFAULT_PRECIO_VENTA_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].precioCostoTotal").value(hasItem(DEFAULT_PRECIO_COSTO_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getPresentacion() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", presentacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presentacion.getId().intValue()))
            .andExpect(jsonPath("$.tipoPresentacion").value(DEFAULT_TIPO_PRESENTACION.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.costoUnitario").value(DEFAULT_COSTO_UNITARIO.intValue()))
            .andExpect(jsonPath("$.precioVentaUnitario").value(DEFAULT_PRECIO_VENTA_UNITARIO.intValue()))
            .andExpect(jsonPath("$.precioVentaTotal").value(DEFAULT_PRECIO_VENTA_TOTAL.intValue()))
            .andExpect(jsonPath("$.precioCostoTotal").value(DEFAULT_PRECIO_COSTO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPresentacion() throws Exception {
        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresentacion() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();

        // Update the presentacion
        Presentacion updatedPresentacion = presentacionRepository.findById(presentacion.getId()).get();
        // Disconnect from session so that the updates on updatedPresentacion are not directly saved in db
        em.detach(updatedPresentacion);
        updatedPresentacion
            .tipoPresentacion(UPDATED_TIPO_PRESENTACION)
            .cantidad(UPDATED_CANTIDAD)
            .fecha(UPDATED_FECHA)
            .costoUnitario(UPDATED_COSTO_UNITARIO)
            .precioVentaUnitario(UPDATED_PRECIO_VENTA_UNITARIO)
            .precioVentaTotal(UPDATED_PRECIO_VENTA_TOTAL)
            .precioCostoTotal(UPDATED_PRECIO_COSTO_TOTAL);
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(updatedPresentacion);

        restPresentacionMockMvc.perform(put("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isOk());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate);
        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
        assertThat(testPresentacion.getTipoPresentacion()).isEqualTo(UPDATED_TIPO_PRESENTACION);
        assertThat(testPresentacion.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPresentacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPresentacion.getCostoUnitario()).isEqualTo(UPDATED_COSTO_UNITARIO);
        assertThat(testPresentacion.getPrecioVentaUnitario()).isEqualTo(UPDATED_PRECIO_VENTA_UNITARIO);
        assertThat(testPresentacion.getPrecioVentaTotal()).isEqualTo(UPDATED_PRECIO_VENTA_TOTAL);
        assertThat(testPresentacion.getPrecioCostoTotal()).isEqualTo(UPDATED_PRECIO_COSTO_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPresentacion() throws Exception {
        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();

        // Create the Presentacion
        PresentacionDTO presentacionDTO = presentacionMapper.toDto(presentacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPresentacionMockMvc.perform(put("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePresentacion() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        int databaseSizeBeforeDelete = presentacionRepository.findAll().size();

        // Delete the presentacion
        restPresentacionMockMvc.perform(delete("/api/presentacions/{id}", presentacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
