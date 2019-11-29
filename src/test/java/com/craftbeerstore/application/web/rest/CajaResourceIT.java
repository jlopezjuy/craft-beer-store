package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.repository.CajaRepository;
import com.craftbeerstore.application.service.CajaService;
import com.craftbeerstore.application.service.dto.CajaDTO;
import com.craftbeerstore.application.service.mapper.CajaMapper;
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
import org.springframework.util.Base64Utils;
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

import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;
import com.craftbeerstore.application.domain.enumeration.TipoPago;
/**
 * Integration tests for the {@link CajaResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class CajaResourceIT {

    private static final TipoMovimientoCaja DEFAULT_TIPO_MOVIMIENTO = TipoMovimientoCaja.INGRESO;
    private static final TipoMovimientoCaja UPDATED_TIPO_MOVIMIENTO = TipoMovimientoCaja.EGRESO;

    private static final TipoPago DEFAULT_TIPO_PAGO = TipoPago.EFECTIVO;
    private static final TipoPago UPDATED_TIPO_PAGO = TipoPago.TARJETA_CREDITO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALDO_CTA_CTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_CTA_CTE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IMPORTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPORTE = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private CajaMapper cajaMapper;

    @Autowired
    private CajaService cajaService;

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

    private MockMvc restCajaMockMvc;

    private Caja caja;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CajaResource cajaResource = new CajaResource(cajaService);
        this.restCajaMockMvc = MockMvcBuilders.standaloneSetup(cajaResource)
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
    public static Caja createEntity(EntityManager em) {
        Caja caja = new Caja()
            .tipoMovimiento(DEFAULT_TIPO_MOVIMIENTO)
            .tipoPago(DEFAULT_TIPO_PAGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .saldoCtaCte(DEFAULT_SALDO_CTA_CTE)
            .importe(DEFAULT_IMPORTE)
            .fecha(DEFAULT_FECHA);
        return caja;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caja createUpdatedEntity(EntityManager em) {
        Caja caja = new Caja()
            .tipoMovimiento(UPDATED_TIPO_MOVIMIENTO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .descripcion(UPDATED_DESCRIPCION)
            .saldoCtaCte(UPDATED_SALDO_CTA_CTE)
            .importe(UPDATED_IMPORTE)
            .fecha(UPDATED_FECHA);
        return caja;
    }

    @BeforeEach
    public void initTest() {
        caja = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaja() throws Exception {
        int databaseSizeBeforeCreate = cajaRepository.findAll().size();

        // Create the Caja
        CajaDTO cajaDTO = cajaMapper.toDto(caja);
        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isCreated());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate + 1);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getTipoMovimiento()).isEqualTo(DEFAULT_TIPO_MOVIMIENTO);
        assertThat(testCaja.getTipoPago()).isEqualTo(DEFAULT_TIPO_PAGO);
        assertThat(testCaja.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCaja.getSaldoCtaCte()).isEqualTo(DEFAULT_SALDO_CTA_CTE);
        assertThat(testCaja.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testCaja.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCajaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cajaRepository.findAll().size();

        // Create the Caja with an existing ID
        caja.setId(1L);
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoMovimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setTipoMovimiento(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setTipoPago(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImporteIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setImporte(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setFecha(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCajas() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get all the cajaList
        restCajaMockMvc.perform(get("/api/cajas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caja.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMovimiento").value(hasItem(DEFAULT_TIPO_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoPago").value(hasItem(DEFAULT_TIPO_PAGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].saldoCtaCte").value(hasItem(DEFAULT_SALDO_CTA_CTE.intValue())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get the caja
        restCajaMockMvc.perform(get("/api/cajas/{id}", caja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caja.getId().intValue()))
            .andExpect(jsonPath("$.tipoMovimiento").value(DEFAULT_TIPO_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.tipoPago").value(DEFAULT_TIPO_PAGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.saldoCtaCte").value(DEFAULT_SALDO_CTA_CTE.intValue()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaja() throws Exception {
        // Get the caja
        restCajaMockMvc.perform(get("/api/cajas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Update the caja
        Caja updatedCaja = cajaRepository.findById(caja.getId()).get();
        // Disconnect from session so that the updates on updatedCaja are not directly saved in db
        em.detach(updatedCaja);
        updatedCaja
            .tipoMovimiento(UPDATED_TIPO_MOVIMIENTO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .descripcion(UPDATED_DESCRIPCION)
            .saldoCtaCte(UPDATED_SALDO_CTA_CTE)
            .importe(UPDATED_IMPORTE)
            .fecha(UPDATED_FECHA);
        CajaDTO cajaDTO = cajaMapper.toDto(updatedCaja);

        restCajaMockMvc.perform(put("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isOk());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getTipoMovimiento()).isEqualTo(UPDATED_TIPO_MOVIMIENTO);
        assertThat(testCaja.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testCaja.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCaja.getSaldoCtaCte()).isEqualTo(UPDATED_SALDO_CTA_CTE);
        assertThat(testCaja.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testCaja.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Create the Caja
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajaMockMvc.perform(put("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeDelete = cajaRepository.findAll().size();

        // Delete the caja
        restCajaMockMvc.perform(delete("/api/cajas/{id}", caja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
