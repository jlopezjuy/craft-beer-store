package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import com.craftbeerstore.application.repository.CompraInsumoDetalleRepository;
import com.craftbeerstore.application.service.CompraInsumoDetalleService;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;
import com.craftbeerstore.application.service.mapper.CompraInsumoDetalleMapper;
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
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.Unidad;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
/**
 * Test class for the CompraInsumoDetalleResource REST controller.
 *
 * @see CompraInsumoDetalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class CompraInsumoDetalleResourceIntTest {

    private static final Unidad DEFAULT_UNIDAD = Unidad.KILOGRAMO;
    private static final Unidad UPDATED_UNIDAD = Unidad.GRAMO;

    private static final String DEFAULT_CODIGO_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_REFERENCIA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_STOCK = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_UNITARIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(2);

    private static final TipoInsumo DEFAULT_TIPO = TipoInsumo.MALTA;
    private static final TipoInsumo UPDATED_TIPO = TipoInsumo.LUPULO;

    @Autowired
    private CompraInsumoDetalleRepository compraInsumoDetalleRepository;

    @Autowired
    private CompraInsumoDetalleMapper compraInsumoDetalleMapper;

    @Autowired
    private CompraInsumoDetalleService compraInsumoDetalleService;

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

    private MockMvc restCompraInsumoDetalleMockMvc;

    private CompraInsumoDetalle compraInsumoDetalle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraInsumoDetalleResource compraInsumoDetalleResource = new CompraInsumoDetalleResource(compraInsumoDetalleService);
        this.restCompraInsumoDetalleMockMvc = MockMvcBuilders.standaloneSetup(compraInsumoDetalleResource)
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
    public static CompraInsumoDetalle createEntity(EntityManager em) {
        CompraInsumoDetalle compraInsumoDetalle = new CompraInsumoDetalle()
            .unidad(DEFAULT_UNIDAD)
            .codigoReferencia(DEFAULT_CODIGO_REFERENCIA)
            .stock(DEFAULT_STOCK)
            .precioUnitario(DEFAULT_PRECIO_UNITARIO)
            .precioTotal(DEFAULT_PRECIO_TOTAL)
            .tipo(DEFAULT_TIPO);
        return compraInsumoDetalle;
    }

    @Before
    public void initTest() {
        compraInsumoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompraInsumoDetalle() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoDetalleRepository.findAll().size();

        // Create the CompraInsumoDetalle
        CompraInsumoDetalleDTO compraInsumoDetalleDTO = compraInsumoDetalleMapper.toDto(compraInsumoDetalle);
        restCompraInsumoDetalleMockMvc.perform(post("/api/compra-insumo-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDetalleDTO)))
            .andExpect(status().isCreated());

        // Validate the CompraInsumoDetalle in the database
        List<CompraInsumoDetalle> compraInsumoDetalleList = compraInsumoDetalleRepository.findAll();
        assertThat(compraInsumoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        CompraInsumoDetalle testCompraInsumoDetalle = compraInsumoDetalleList.get(compraInsumoDetalleList.size() - 1);
        assertThat(testCompraInsumoDetalle.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testCompraInsumoDetalle.getCodigoReferencia()).isEqualTo(DEFAULT_CODIGO_REFERENCIA);
        assertThat(testCompraInsumoDetalle.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testCompraInsumoDetalle.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
        assertThat(testCompraInsumoDetalle.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
        assertThat(testCompraInsumoDetalle.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createCompraInsumoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraInsumoDetalleRepository.findAll().size();

        // Create the CompraInsumoDetalle with an existing ID
        compraInsumoDetalle.setId(1L);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO = compraInsumoDetalleMapper.toDto(compraInsumoDetalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraInsumoDetalleMockMvc.perform(post("/api/compra-insumo-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumoDetalle in the database
        List<CompraInsumoDetalle> compraInsumoDetalleList = compraInsumoDetalleRepository.findAll();
        assertThat(compraInsumoDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompraInsumoDetalles() throws Exception {
        // Initialize the database
        compraInsumoDetalleRepository.saveAndFlush(compraInsumoDetalle);

        // Get all the compraInsumoDetalleList
        restCompraInsumoDetalleMockMvc.perform(get("/api/compra-insumo-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compraInsumoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD.toString())))
            .andExpect(jsonPath("$.[*].codigoReferencia").value(hasItem(DEFAULT_CODIGO_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getCompraInsumoDetalle() throws Exception {
        // Initialize the database
        compraInsumoDetalleRepository.saveAndFlush(compraInsumoDetalle);

        // Get the compraInsumoDetalle
        restCompraInsumoDetalleMockMvc.perform(get("/api/compra-insumo-detalles/{id}", compraInsumoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compraInsumoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD.toString()))
            .andExpect(jsonPath("$.codigoReferencia").value(DEFAULT_CODIGO_REFERENCIA.toString()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO.intValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompraInsumoDetalle() throws Exception {
        // Get the compraInsumoDetalle
        restCompraInsumoDetalleMockMvc.perform(get("/api/compra-insumo-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompraInsumoDetalle() throws Exception {
        // Initialize the database
        compraInsumoDetalleRepository.saveAndFlush(compraInsumoDetalle);

        int databaseSizeBeforeUpdate = compraInsumoDetalleRepository.findAll().size();

        // Update the compraInsumoDetalle
        CompraInsumoDetalle updatedCompraInsumoDetalle = compraInsumoDetalleRepository.findById(compraInsumoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedCompraInsumoDetalle are not directly saved in db
        em.detach(updatedCompraInsumoDetalle);
        updatedCompraInsumoDetalle
            .unidad(UPDATED_UNIDAD)
            .codigoReferencia(UPDATED_CODIGO_REFERENCIA)
            .stock(UPDATED_STOCK)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .precioTotal(UPDATED_PRECIO_TOTAL)
            .tipo(UPDATED_TIPO);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO = compraInsumoDetalleMapper.toDto(updatedCompraInsumoDetalle);

        restCompraInsumoDetalleMockMvc.perform(put("/api/compra-insumo-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDetalleDTO)))
            .andExpect(status().isOk());

        // Validate the CompraInsumoDetalle in the database
        List<CompraInsumoDetalle> compraInsumoDetalleList = compraInsumoDetalleRepository.findAll();
        assertThat(compraInsumoDetalleList).hasSize(databaseSizeBeforeUpdate);
        CompraInsumoDetalle testCompraInsumoDetalle = compraInsumoDetalleList.get(compraInsumoDetalleList.size() - 1);
        assertThat(testCompraInsumoDetalle.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testCompraInsumoDetalle.getCodigoReferencia()).isEqualTo(UPDATED_CODIGO_REFERENCIA);
        assertThat(testCompraInsumoDetalle.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testCompraInsumoDetalle.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
        assertThat(testCompraInsumoDetalle.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
        assertThat(testCompraInsumoDetalle.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCompraInsumoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = compraInsumoDetalleRepository.findAll().size();

        // Create the CompraInsumoDetalle
        CompraInsumoDetalleDTO compraInsumoDetalleDTO = compraInsumoDetalleMapper.toDto(compraInsumoDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraInsumoDetalleMockMvc.perform(put("/api/compra-insumo-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraInsumoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompraInsumoDetalle in the database
        List<CompraInsumoDetalle> compraInsumoDetalleList = compraInsumoDetalleRepository.findAll();
        assertThat(compraInsumoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompraInsumoDetalle() throws Exception {
        // Initialize the database
        compraInsumoDetalleRepository.saveAndFlush(compraInsumoDetalle);

        int databaseSizeBeforeDelete = compraInsumoDetalleRepository.findAll().size();

        // Delete the compraInsumoDetalle
        restCompraInsumoDetalleMockMvc.perform(delete("/api/compra-insumo-detalles/{id}", compraInsumoDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompraInsumoDetalle> compraInsumoDetalleList = compraInsumoDetalleRepository.findAll();
        assertThat(compraInsumoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDetalle.class);
        CompraInsumoDetalle compraInsumoDetalle1 = new CompraInsumoDetalle();
        compraInsumoDetalle1.setId(1L);
        CompraInsumoDetalle compraInsumoDetalle2 = new CompraInsumoDetalle();
        compraInsumoDetalle2.setId(compraInsumoDetalle1.getId());
        assertThat(compraInsumoDetalle1).isEqualTo(compraInsumoDetalle2);
        compraInsumoDetalle2.setId(2L);
        assertThat(compraInsumoDetalle1).isNotEqualTo(compraInsumoDetalle2);
        compraInsumoDetalle1.setId(null);
        assertThat(compraInsumoDetalle1).isNotEqualTo(compraInsumoDetalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDetalleDTO.class);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO1 = new CompraInsumoDetalleDTO();
        compraInsumoDetalleDTO1.setId(1L);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO2 = new CompraInsumoDetalleDTO();
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO2.setId(compraInsumoDetalleDTO1.getId());
        assertThat(compraInsumoDetalleDTO1).isEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO2.setId(2L);
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO1.setId(null);
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compraInsumoDetalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compraInsumoDetalleMapper.fromId(null)).isNull();
    }
}
