package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.repository.CompraInsumoRepository;
import com.craftbeerstore.application.service.CompraInsumoService;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
import com.craftbeerstore.application.service.mapper.CompraInsumoMapper;
import com.craftbeerstore.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompraInsumoResource REST controller.
 *
 * @see CompraInsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class CompraInsumoResourceIntTest {

    private static final String DEFAULT_NRO_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_NRO_FACTURA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GASTO_DE_ENVIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_GASTO_DE_ENVIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IMPUESTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPUESTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private CompraInsumoRepository compraInsumoRepository;

    @Autowired
    private CompraInsumoMapper compraInsumoMapper;

    @Autowired
    private CompraInsumoService compraInsumoService;

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

    private MockMvc restCompraInsumoMockMvc;

    private CompraInsumo compraInsumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraInsumoResource compraInsumoResource = new CompraInsumoResource(compraInsumoService);
        this.restCompraInsumoMockMvc = MockMvcBuilders.standaloneSetup(compraInsumoResource)
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
    public static CompraInsumo createEntity(EntityManager em) {
        CompraInsumo compraInsumo = new CompraInsumo()
            .nroFactura(DEFAULT_NRO_FACTURA)
            .fecha(DEFAULT_FECHA)
            .subtotal(DEFAULT_SUBTOTAL)
            .gastoDeEnvio(DEFAULT_GASTO_DE_ENVIO)
            .impuesto(DEFAULT_IMPUESTO)
            .total(DEFAULT_TOTAL);
        return compraInsumo;
    }

    @Before
    public void initTest() {
        compraInsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompraInsumo() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);
        restCompraInsumoMockMvc.perform(post("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeCreate + 1);
        CompraInsumo testCompraInsumo = compraInsumoList.get(compraInsumoList.size() - 1);
        assertThat(testCompraInsumo.getNroFactura()).isEqualTo(DEFAULT_NRO_FACTURA);
        assertThat(testCompraInsumo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCompraInsumo.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCompraInsumo.getGastoDeEnvio()).isEqualTo(DEFAULT_GASTO_DE_ENVIO);
        assertThat(testCompraInsumo.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testCompraInsumo.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCompraInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo with an existing ID
        compraInsumo.setId(1L);
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraInsumoMockMvc.perform(post("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompraInsumos() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        // Get all the compraInsumoList
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroFactura").value(hasItem(DEFAULT_NRO_FACTURA.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].gastoDeEnvio").value(hasItem(DEFAULT_GASTO_DE_ENVIO.intValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        // Get the compraInsumo
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos/{id}", compraInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compraInsumo.getId().intValue()))
            .andExpect(jsonPath("$.nroFactura").value(DEFAULT_NRO_FACTURA.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.gastoDeEnvio").value(DEFAULT_GASTO_DE_ENVIO.intValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompraInsumo() throws Exception {
        // Get the compraInsumo
        restCompraInsumoMockMvc.perform(get("/api/compra-insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        int databaseSizeBeforeUpdate = compraInsumoRepository.findAll().size();

        // Update the compraInsumo
        CompraInsumo updatedCompraInsumo = compraInsumoRepository.findById(compraInsumo.getId()).get();
        // Disconnect from session so that the updates on updatedCompraInsumo are not directly saved in db
        em.detach(updatedCompraInsumo);
        updatedCompraInsumo
            .nroFactura(UPDATED_NRO_FACTURA)
            .fecha(UPDATED_FECHA)
            .subtotal(UPDATED_SUBTOTAL)
            .gastoDeEnvio(UPDATED_GASTO_DE_ENVIO)
            .impuesto(UPDATED_IMPUESTO)
            .total(UPDATED_TOTAL);
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(updatedCompraInsumo);

        restCompraInsumoMockMvc.perform(put("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isOk());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeUpdate);
        CompraInsumo testCompraInsumo = compraInsumoList.get(compraInsumoList.size() - 1);
        assertThat(testCompraInsumo.getNroFactura()).isEqualTo(UPDATED_NRO_FACTURA);
        assertThat(testCompraInsumo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCompraInsumo.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCompraInsumo.getGastoDeEnvio()).isEqualTo(UPDATED_GASTO_DE_ENVIO);
        assertThat(testCompraInsumo.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testCompraInsumo.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCompraInsumo() throws Exception {
        int databaseSizeBeforeUpdate = compraInsumoRepository.findAll().size();

        // Create the CompraInsumo
        CompraInsumoDTO compraInsumoDTO = compraInsumoMapper.toDto(compraInsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraInsumoMockMvc.perform(put("/api/compra-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumo in the database
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompraInsumo() throws Exception {
        // Initialize the database
        compraInsumoRepository.saveAndFlush(compraInsumo);

        int databaseSizeBeforeDelete = compraInsumoRepository.findAll().size();

        // Delete the compraInsumo
        restCompraInsumoMockMvc.perform(delete("/api/compra-insumos/{id}", compraInsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompraInsumo> compraInsumoList = compraInsumoRepository.findAll();
        assertThat(compraInsumoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumo.class);
        CompraInsumo compraInsumo1 = new CompraInsumo();
        compraInsumo1.setId(1L);
        CompraInsumo compraInsumo2 = new CompraInsumo();
        compraInsumo2.setId(compraInsumo1.getId());
        assertThat(compraInsumo1).isEqualTo(compraInsumo2);
        compraInsumo2.setId(2L);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
        compraInsumo1.setId(null);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDTO.class);
        CompraInsumoDTO compraInsumoDTO1 = new CompraInsumoDTO();
        compraInsumoDTO1.setId(1L);
        CompraInsumoDTO compraInsumoDTO2 = new CompraInsumoDTO();
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(compraInsumoDTO1.getId());
        assertThat(compraInsumoDTO1).isEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(2L);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO1.setId(null);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compraInsumoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compraInsumoMapper.fromId(null)).isNull();
    }
}
