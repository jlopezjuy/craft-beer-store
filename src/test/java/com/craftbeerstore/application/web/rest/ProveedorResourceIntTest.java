package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Proveedor;
import com.craftbeerstore.application.repository.ProveedorRepository;
import com.craftbeerstore.application.repository.search.ProveedorSearchRepository;
import com.craftbeerstore.application.service.ProveedorService;
import com.craftbeerstore.application.service.dto.ProveedorDTO;
import com.craftbeerstore.application.service.mapper.ProveedorMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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

/**
 * Test class for the ProveedorResource REST controller.
 *
 * @see ProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class ProveedorResourceIntTest {

    private static final String DEFAULT_NOMBRE_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROVEEDOR = "BBBBBBBBBB";

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "(186)205-6359";
    private static final String UPDATED_TELEFONO = "(942)262-5281";

    private static final LocalDate DEFAULT_FECHA_ALTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ALTA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "R@R\\3RLIDJ";
    private static final String UPDATED_EMAIL = "JW@1\\cTDHFY";

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapper proveedorMapper;

    @Autowired
    private ProveedorService proveedorService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.ProveedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProveedorSearchRepository mockProveedorSearchRepository;

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

    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProveedorResource proveedorResource = new ProveedorResource(proveedorService);
        this.restProveedorMockMvc = MockMvcBuilders.standaloneSetup(proveedorResource)
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
    public static Proveedor createEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
            .nombreProveedor(DEFAULT_NOMBRE_PROVEEDOR)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .cuit(DEFAULT_CUIT)
            .telefono(DEFAULT_TELEFONO)
            .fechaAlta(DEFAULT_FECHA_ALTA)
            .domicilio(DEFAULT_DOMICILIO)
            .email(DEFAULT_EMAIL)
            .notas(DEFAULT_NOTAS);
        return proveedor;
    }

    @Before
    public void initTest() {
        proveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombreProveedor()).isEqualTo(DEFAULT_NOMBRE_PROVEEDOR);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testProveedor.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedor.getFechaAlta()).isEqualTo(DEFAULT_FECHA_ALTA);
        assertThat(testProveedor.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testProveedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProveedor.getNotas()).isEqualTo(DEFAULT_NOTAS);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).save(testProveedor);
    }

    @Test
    @Transactional
    public void createProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor with an existing ID
        proveedor.setId(1L);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(0)).save(proveedor);
    }

    @Test
    @Transactional
    public void checkNombreProveedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setNombreProveedor(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setRazonSocial(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setCuit(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setTelefono(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaAltaIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setFechaAlta(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setDomicilio(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setEmail(null);

        // Create the Proveedor, which fails.
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        restProveedorMockMvc.perform(post("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedorList
        restProveedorMockMvc.perform(get("/api/proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProveedor").value(hasItem(DEFAULT_NOMBRE_PROVEEDOR.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }
    
    @Test
    @Transactional
    public void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.nombreProveedor").value(DEFAULT_NOMBRE_PROVEEDOR.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.fechaAlta").value(DEFAULT_FECHA_ALTA.toString()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findById(proveedor.getId()).get();
        // Disconnect from session so that the updates on updatedProveedor are not directly saved in db
        em.detach(updatedProveedor);
        updatedProveedor
            .nombreProveedor(UPDATED_NOMBRE_PROVEEDOR)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .cuit(UPDATED_CUIT)
            .telefono(UPDATED_TELEFONO)
            .fechaAlta(UPDATED_FECHA_ALTA)
            .domicilio(UPDATED_DOMICILIO)
            .email(UPDATED_EMAIL)
            .notas(UPDATED_NOTAS);
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(updatedProveedor);

        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedorList.get(proveedorList.size() - 1);
        assertThat(testProveedor.getNombreProveedor()).isEqualTo(UPDATED_NOMBRE_PROVEEDOR);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testProveedor.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedor.getFechaAlta()).isEqualTo(UPDATED_FECHA_ALTA);
        assertThat(testProveedor.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testProveedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProveedor.getNotas()).isEqualTo(UPDATED_NOTAS);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).save(testProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingProveedor() throws Exception {
        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Create the Proveedor
        ProveedorDTO proveedorDTO = proveedorMapper.toDto(proveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc.perform(put("/api/proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(0)).save(proveedor);
    }

    @Test
    @Transactional
    public void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Delete the proveedor
        restProveedorMockMvc.perform(delete("/api/proveedors/{id}", proveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proveedor> proveedorList = proveedorRepository.findAll();
        assertThat(proveedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Proveedor in Elasticsearch
        verify(mockProveedorSearchRepository, times(1)).deleteById(proveedor.getId());
    }

    @Test
    @Transactional
    public void searchProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);
        when(mockProveedorSearchRepository.search(queryStringQuery("id:" + proveedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(proveedor), PageRequest.of(0, 1), 1));
        // Search the proveedor
        restProveedorMockMvc.perform(get("/api/_search/proveedors?query=id:" + proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProveedor").value(hasItem(DEFAULT_NOMBRE_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAlta").value(hasItem(DEFAULT_FECHA_ALTA.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proveedor.class);
        Proveedor proveedor1 = new Proveedor();
        proveedor1.setId(1L);
        Proveedor proveedor2 = new Proveedor();
        proveedor2.setId(proveedor1.getId());
        assertThat(proveedor1).isEqualTo(proveedor2);
        proveedor2.setId(2L);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
        proveedor1.setId(null);
        assertThat(proveedor1).isNotEqualTo(proveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProveedorDTO.class);
        ProveedorDTO proveedorDTO1 = new ProveedorDTO();
        proveedorDTO1.setId(1L);
        ProveedorDTO proveedorDTO2 = new ProveedorDTO();
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO2.setId(proveedorDTO1.getId());
        assertThat(proveedorDTO1).isEqualTo(proveedorDTO2);
        proveedorDTO2.setId(2L);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
        proveedorDTO1.setId(null);
        assertThat(proveedorDTO1).isNotEqualTo(proveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proveedorMapper.fromId(null)).isNull();
    }
}
