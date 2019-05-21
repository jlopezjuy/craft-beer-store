package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.PuntoDeVenta;
import com.craftbeerstore.application.repository.PuntoDeVentaRepository;
import com.craftbeerstore.application.repository.search.PuntoDeVentaSearchRepository;
import com.craftbeerstore.application.service.PuntoDeVentaService;
import com.craftbeerstore.application.service.dto.PuntoDeVentaDTO;
import com.craftbeerstore.application.service.mapper.PuntoDeVentaMapper;
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
 * Test class for the PuntoDeVentaResource REST controller.
 *
 * @see PuntoDeVentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class PuntoDeVentaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_DE_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_DE_ENTREGA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    @Autowired
    private PuntoDeVentaRepository puntoDeVentaRepository;

    @Autowired
    private PuntoDeVentaMapper puntoDeVentaMapper;

    @Autowired
    private PuntoDeVentaService puntoDeVentaService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.PuntoDeVentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PuntoDeVentaSearchRepository mockPuntoDeVentaSearchRepository;

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

    private MockMvc restPuntoDeVentaMockMvc;

    private PuntoDeVenta puntoDeVenta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuntoDeVentaResource puntoDeVentaResource = new PuntoDeVentaResource(puntoDeVentaService);
        this.restPuntoDeVentaMockMvc = MockMvcBuilders.standaloneSetup(puntoDeVentaResource)
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
    public static PuntoDeVenta createEntity(EntityManager em) {
        PuntoDeVenta puntoDeVenta = new PuntoDeVenta()
            .nombre(DEFAULT_NOMBRE)
            .direccionDeEntrega(DEFAULT_DIRECCION_DE_ENTREGA)
            .localidad(DEFAULT_LOCALIDAD)
            .notas(DEFAULT_NOTAS);
        return puntoDeVenta;
    }

    @Before
    public void initTest() {
        puntoDeVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuntoDeVenta() throws Exception {
        int databaseSizeBeforeCreate = puntoDeVentaRepository.findAll().size();

        // Create the PuntoDeVenta
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);
        restPuntoDeVentaMockMvc.perform(post("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the PuntoDeVenta in the database
        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeCreate + 1);
        PuntoDeVenta testPuntoDeVenta = puntoDeVentaList.get(puntoDeVentaList.size() - 1);
        assertThat(testPuntoDeVenta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPuntoDeVenta.getDireccionDeEntrega()).isEqualTo(DEFAULT_DIRECCION_DE_ENTREGA);
        assertThat(testPuntoDeVenta.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testPuntoDeVenta.getNotas()).isEqualTo(DEFAULT_NOTAS);

        // Validate the PuntoDeVenta in Elasticsearch
        verify(mockPuntoDeVentaSearchRepository, times(1)).save(testPuntoDeVenta);
    }

    @Test
    @Transactional
    public void createPuntoDeVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puntoDeVentaRepository.findAll().size();

        // Create the PuntoDeVenta with an existing ID
        puntoDeVenta.setId(1L);
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntoDeVentaMockMvc.perform(post("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PuntoDeVenta in the database
        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PuntoDeVenta in Elasticsearch
        verify(mockPuntoDeVentaSearchRepository, times(0)).save(puntoDeVenta);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = puntoDeVentaRepository.findAll().size();
        // set the field null
        puntoDeVenta.setNombre(null);

        // Create the PuntoDeVenta, which fails.
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);

        restPuntoDeVentaMockMvc.perform(post("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionDeEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = puntoDeVentaRepository.findAll().size();
        // set the field null
        puntoDeVenta.setDireccionDeEntrega(null);

        // Create the PuntoDeVenta, which fails.
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);

        restPuntoDeVentaMockMvc.perform(post("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = puntoDeVentaRepository.findAll().size();
        // set the field null
        puntoDeVenta.setLocalidad(null);

        // Create the PuntoDeVenta, which fails.
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);

        restPuntoDeVentaMockMvc.perform(post("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPuntoDeVentas() throws Exception {
        // Initialize the database
        puntoDeVentaRepository.saveAndFlush(puntoDeVenta);

        // Get all the puntoDeVentaList
        restPuntoDeVentaMockMvc.perform(get("/api/punto-de-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntoDeVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].direccionDeEntrega").value(hasItem(DEFAULT_DIRECCION_DE_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }
    
    @Test
    @Transactional
    public void getPuntoDeVenta() throws Exception {
        // Initialize the database
        puntoDeVentaRepository.saveAndFlush(puntoDeVenta);

        // Get the puntoDeVenta
        restPuntoDeVentaMockMvc.perform(get("/api/punto-de-ventas/{id}", puntoDeVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puntoDeVenta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.direccionDeEntrega").value(DEFAULT_DIRECCION_DE_ENTREGA.toString()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD.toString()))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPuntoDeVenta() throws Exception {
        // Get the puntoDeVenta
        restPuntoDeVentaMockMvc.perform(get("/api/punto-de-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuntoDeVenta() throws Exception {
        // Initialize the database
        puntoDeVentaRepository.saveAndFlush(puntoDeVenta);

        int databaseSizeBeforeUpdate = puntoDeVentaRepository.findAll().size();

        // Update the puntoDeVenta
        PuntoDeVenta updatedPuntoDeVenta = puntoDeVentaRepository.findById(puntoDeVenta.getId()).get();
        // Disconnect from session so that the updates on updatedPuntoDeVenta are not directly saved in db
        em.detach(updatedPuntoDeVenta);
        updatedPuntoDeVenta
            .nombre(UPDATED_NOMBRE)
            .direccionDeEntrega(UPDATED_DIRECCION_DE_ENTREGA)
            .localidad(UPDATED_LOCALIDAD)
            .notas(UPDATED_NOTAS);
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(updatedPuntoDeVenta);

        restPuntoDeVentaMockMvc.perform(put("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isOk());

        // Validate the PuntoDeVenta in the database
        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeUpdate);
        PuntoDeVenta testPuntoDeVenta = puntoDeVentaList.get(puntoDeVentaList.size() - 1);
        assertThat(testPuntoDeVenta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuntoDeVenta.getDireccionDeEntrega()).isEqualTo(UPDATED_DIRECCION_DE_ENTREGA);
        assertThat(testPuntoDeVenta.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testPuntoDeVenta.getNotas()).isEqualTo(UPDATED_NOTAS);

        // Validate the PuntoDeVenta in Elasticsearch
        verify(mockPuntoDeVentaSearchRepository, times(1)).save(testPuntoDeVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingPuntoDeVenta() throws Exception {
        int databaseSizeBeforeUpdate = puntoDeVentaRepository.findAll().size();

        // Create the PuntoDeVenta
        PuntoDeVentaDTO puntoDeVentaDTO = puntoDeVentaMapper.toDto(puntoDeVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuntoDeVentaMockMvc.perform(put("/api/punto-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PuntoDeVenta in the database
        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PuntoDeVenta in Elasticsearch
        verify(mockPuntoDeVentaSearchRepository, times(0)).save(puntoDeVenta);
    }

    @Test
    @Transactional
    public void deletePuntoDeVenta() throws Exception {
        // Initialize the database
        puntoDeVentaRepository.saveAndFlush(puntoDeVenta);

        int databaseSizeBeforeDelete = puntoDeVentaRepository.findAll().size();

        // Delete the puntoDeVenta
        restPuntoDeVentaMockMvc.perform(delete("/api/punto-de-ventas/{id}", puntoDeVenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PuntoDeVenta> puntoDeVentaList = puntoDeVentaRepository.findAll();
        assertThat(puntoDeVentaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PuntoDeVenta in Elasticsearch
        verify(mockPuntoDeVentaSearchRepository, times(1)).deleteById(puntoDeVenta.getId());
    }

    @Test
    @Transactional
    public void searchPuntoDeVenta() throws Exception {
        // Initialize the database
        puntoDeVentaRepository.saveAndFlush(puntoDeVenta);
        when(mockPuntoDeVentaSearchRepository.search(queryStringQuery("id:" + puntoDeVenta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(puntoDeVenta), PageRequest.of(0, 1), 1));
        // Search the puntoDeVenta
        restPuntoDeVentaMockMvc.perform(get("/api/_search/punto-de-ventas?query=id:" + puntoDeVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntoDeVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccionDeEntrega").value(hasItem(DEFAULT_DIRECCION_DE_ENTREGA)))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntoDeVenta.class);
        PuntoDeVenta puntoDeVenta1 = new PuntoDeVenta();
        puntoDeVenta1.setId(1L);
        PuntoDeVenta puntoDeVenta2 = new PuntoDeVenta();
        puntoDeVenta2.setId(puntoDeVenta1.getId());
        assertThat(puntoDeVenta1).isEqualTo(puntoDeVenta2);
        puntoDeVenta2.setId(2L);
        assertThat(puntoDeVenta1).isNotEqualTo(puntoDeVenta2);
        puntoDeVenta1.setId(null);
        assertThat(puntoDeVenta1).isNotEqualTo(puntoDeVenta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntoDeVentaDTO.class);
        PuntoDeVentaDTO puntoDeVentaDTO1 = new PuntoDeVentaDTO();
        puntoDeVentaDTO1.setId(1L);
        PuntoDeVentaDTO puntoDeVentaDTO2 = new PuntoDeVentaDTO();
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO2.setId(puntoDeVentaDTO1.getId());
        assertThat(puntoDeVentaDTO1).isEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO2.setId(2L);
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO1.setId(null);
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(puntoDeVentaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(puntoDeVentaMapper.fromId(null)).isNull();
    }
}
