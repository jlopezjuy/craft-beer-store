package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Estilos;
import com.craftbeerstore.application.repository.EstilosRepository;
import com.craftbeerstore.application.repository.search.EstilosSearchRepository;
import com.craftbeerstore.application.service.EstilosService;
import com.craftbeerstore.application.service.dto.EstilosDTO;
import com.craftbeerstore.application.service.mapper.EstilosMapper;
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
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstilosResource REST controller.
 *
 * @see EstilosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EstilosResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_ESTILO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ESTILO = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA_ESTILO = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA_ESTILO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ABV_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_ABV_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ABV_MAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_ABV_MAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ABV_MED = new BigDecimal(1);
    private static final BigDecimal UPDATED_ABV_MED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IBU_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IBU_MAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU_MAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IBU_MED = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU_MED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SRM_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_SRM_MIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SRM_MAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_SRM_MAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SRM_MED = new BigDecimal(1);
    private static final BigDecimal UPDATED_SRM_MED = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_EJEMPLO_NOMBRE_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_EJEMPLO_NOMBRE_COMERCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_EJEMPLO_IMAGEN_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_EJEMPLO_IMAGEN_COMERCIAL = "BBBBBBBBBB";

    @Autowired
    private EstilosRepository estilosRepository;

    @Autowired
    private EstilosMapper estilosMapper;

    @Autowired
    private EstilosService estilosService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.EstilosSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstilosSearchRepository mockEstilosSearchRepository;

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

    private MockMvc restEstilosMockMvc;

    private Estilos estilos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstilosResource estilosResource = new EstilosResource(estilosService);
        this.restEstilosMockMvc = MockMvcBuilders.standaloneSetup(estilosResource)
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
    public static Estilos createEntity(EntityManager em) {
        Estilos estilos = new Estilos()
            .number(DEFAULT_NUMBER)
            .nombreEstilo(DEFAULT_NOMBRE_ESTILO)
            .categoriaEstilo(DEFAULT_CATEGORIA_ESTILO)
            .abvMin(DEFAULT_ABV_MIN)
            .abvMax(DEFAULT_ABV_MAX)
            .abvMed(DEFAULT_ABV_MED)
            .ibuMin(DEFAULT_IBU_MIN)
            .ibuMax(DEFAULT_IBU_MAX)
            .ibuMed(DEFAULT_IBU_MED)
            .srmMin(DEFAULT_SRM_MIN)
            .srmMax(DEFAULT_SRM_MAX)
            .srmMed(DEFAULT_SRM_MED)
            .descripcion(DEFAULT_DESCRIPCION)
            .ejemploNombreComercial(DEFAULT_EJEMPLO_NOMBRE_COMERCIAL)
            .ejemploImagenComercial(DEFAULT_EJEMPLO_IMAGEN_COMERCIAL);
        return estilos;
    }

    @Before
    public void initTest() {
        estilos = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstilos() throws Exception {
        int databaseSizeBeforeCreate = estilosRepository.findAll().size();

        // Create the Estilos
        EstilosDTO estilosDTO = estilosMapper.toDto(estilos);
        restEstilosMockMvc.perform(post("/api/estilos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estilosDTO)))
            .andExpect(status().isCreated());

        // Validate the Estilos in the database
        List<Estilos> estilosList = estilosRepository.findAll();
        assertThat(estilosList).hasSize(databaseSizeBeforeCreate + 1);
        Estilos testEstilos = estilosList.get(estilosList.size() - 1);
        assertThat(testEstilos.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testEstilos.getNombreEstilo()).isEqualTo(DEFAULT_NOMBRE_ESTILO);
        assertThat(testEstilos.getCategoriaEstilo()).isEqualTo(DEFAULT_CATEGORIA_ESTILO);
        assertThat(testEstilos.getAbvMin()).isEqualTo(DEFAULT_ABV_MIN);
        assertThat(testEstilos.getAbvMax()).isEqualTo(DEFAULT_ABV_MAX);
        assertThat(testEstilos.getAbvMed()).isEqualTo(DEFAULT_ABV_MED);
        assertThat(testEstilos.getIbuMin()).isEqualTo(DEFAULT_IBU_MIN);
        assertThat(testEstilos.getIbuMax()).isEqualTo(DEFAULT_IBU_MAX);
        assertThat(testEstilos.getIbuMed()).isEqualTo(DEFAULT_IBU_MED);
        assertThat(testEstilos.getSrmMin()).isEqualTo(DEFAULT_SRM_MIN);
        assertThat(testEstilos.getSrmMax()).isEqualTo(DEFAULT_SRM_MAX);
        assertThat(testEstilos.getSrmMed()).isEqualTo(DEFAULT_SRM_MED);
        assertThat(testEstilos.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEstilos.getEjemploNombreComercial()).isEqualTo(DEFAULT_EJEMPLO_NOMBRE_COMERCIAL);
        assertThat(testEstilos.getEjemploImagenComercial()).isEqualTo(DEFAULT_EJEMPLO_IMAGEN_COMERCIAL);

        // Validate the Estilos in Elasticsearch
        verify(mockEstilosSearchRepository, times(1)).save(testEstilos);
    }

    @Test
    @Transactional
    public void createEstilosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estilosRepository.findAll().size();

        // Create the Estilos with an existing ID
        estilos.setId(1L);
        EstilosDTO estilosDTO = estilosMapper.toDto(estilos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstilosMockMvc.perform(post("/api/estilos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estilosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estilos in the database
        List<Estilos> estilosList = estilosRepository.findAll();
        assertThat(estilosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Estilos in Elasticsearch
        verify(mockEstilosSearchRepository, times(0)).save(estilos);
    }

    @Test
    @Transactional
    public void getAllEstilos() throws Exception {
        // Initialize the database
        estilosRepository.saveAndFlush(estilos);

        // Get all the estilosList
        restEstilosMockMvc.perform(get("/api/estilos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estilos.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].nombreEstilo").value(hasItem(DEFAULT_NOMBRE_ESTILO.toString())))
            .andExpect(jsonPath("$.[*].categoriaEstilo").value(hasItem(DEFAULT_CATEGORIA_ESTILO.toString())))
            .andExpect(jsonPath("$.[*].abvMin").value(hasItem(DEFAULT_ABV_MIN.intValue())))
            .andExpect(jsonPath("$.[*].abvMax").value(hasItem(DEFAULT_ABV_MAX.intValue())))
            .andExpect(jsonPath("$.[*].abvMed").value(hasItem(DEFAULT_ABV_MED.intValue())))
            .andExpect(jsonPath("$.[*].ibuMin").value(hasItem(DEFAULT_IBU_MIN.intValue())))
            .andExpect(jsonPath("$.[*].ibuMax").value(hasItem(DEFAULT_IBU_MAX.intValue())))
            .andExpect(jsonPath("$.[*].ibuMed").value(hasItem(DEFAULT_IBU_MED.intValue())))
            .andExpect(jsonPath("$.[*].srmMin").value(hasItem(DEFAULT_SRM_MIN.intValue())))
            .andExpect(jsonPath("$.[*].srmMax").value(hasItem(DEFAULT_SRM_MAX.intValue())))
            .andExpect(jsonPath("$.[*].srmMed").value(hasItem(DEFAULT_SRM_MED.intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].ejemploNombreComercial").value(hasItem(DEFAULT_EJEMPLO_NOMBRE_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].ejemploImagenComercial").value(hasItem(DEFAULT_EJEMPLO_IMAGEN_COMERCIAL.toString())));
    }
    
    @Test
    @Transactional
    public void getEstilos() throws Exception {
        // Initialize the database
        estilosRepository.saveAndFlush(estilos);

        // Get the estilos
        restEstilosMockMvc.perform(get("/api/estilos/{id}", estilos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estilos.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.nombreEstilo").value(DEFAULT_NOMBRE_ESTILO.toString()))
            .andExpect(jsonPath("$.categoriaEstilo").value(DEFAULT_CATEGORIA_ESTILO.toString()))
            .andExpect(jsonPath("$.abvMin").value(DEFAULT_ABV_MIN.intValue()))
            .andExpect(jsonPath("$.abvMax").value(DEFAULT_ABV_MAX.intValue()))
            .andExpect(jsonPath("$.abvMed").value(DEFAULT_ABV_MED.intValue()))
            .andExpect(jsonPath("$.ibuMin").value(DEFAULT_IBU_MIN.intValue()))
            .andExpect(jsonPath("$.ibuMax").value(DEFAULT_IBU_MAX.intValue()))
            .andExpect(jsonPath("$.ibuMed").value(DEFAULT_IBU_MED.intValue()))
            .andExpect(jsonPath("$.srmMin").value(DEFAULT_SRM_MIN.intValue()))
            .andExpect(jsonPath("$.srmMax").value(DEFAULT_SRM_MAX.intValue()))
            .andExpect(jsonPath("$.srmMed").value(DEFAULT_SRM_MED.intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.ejemploNombreComercial").value(DEFAULT_EJEMPLO_NOMBRE_COMERCIAL.toString()))
            .andExpect(jsonPath("$.ejemploImagenComercial").value(DEFAULT_EJEMPLO_IMAGEN_COMERCIAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstilos() throws Exception {
        // Get the estilos
        restEstilosMockMvc.perform(get("/api/estilos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstilos() throws Exception {
        // Initialize the database
        estilosRepository.saveAndFlush(estilos);

        int databaseSizeBeforeUpdate = estilosRepository.findAll().size();

        // Update the estilos
        Estilos updatedEstilos = estilosRepository.findById(estilos.getId()).get();
        // Disconnect from session so that the updates on updatedEstilos are not directly saved in db
        em.detach(updatedEstilos);
        updatedEstilos
            .number(UPDATED_NUMBER)
            .nombreEstilo(UPDATED_NOMBRE_ESTILO)
            .categoriaEstilo(UPDATED_CATEGORIA_ESTILO)
            .abvMin(UPDATED_ABV_MIN)
            .abvMax(UPDATED_ABV_MAX)
            .abvMed(UPDATED_ABV_MED)
            .ibuMin(UPDATED_IBU_MIN)
            .ibuMax(UPDATED_IBU_MAX)
            .ibuMed(UPDATED_IBU_MED)
            .srmMin(UPDATED_SRM_MIN)
            .srmMax(UPDATED_SRM_MAX)
            .srmMed(UPDATED_SRM_MED)
            .descripcion(UPDATED_DESCRIPCION)
            .ejemploNombreComercial(UPDATED_EJEMPLO_NOMBRE_COMERCIAL)
            .ejemploImagenComercial(UPDATED_EJEMPLO_IMAGEN_COMERCIAL);
        EstilosDTO estilosDTO = estilosMapper.toDto(updatedEstilos);

        restEstilosMockMvc.perform(put("/api/estilos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estilosDTO)))
            .andExpect(status().isOk());

        // Validate the Estilos in the database
        List<Estilos> estilosList = estilosRepository.findAll();
        assertThat(estilosList).hasSize(databaseSizeBeforeUpdate);
        Estilos testEstilos = estilosList.get(estilosList.size() - 1);
        assertThat(testEstilos.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testEstilos.getNombreEstilo()).isEqualTo(UPDATED_NOMBRE_ESTILO);
        assertThat(testEstilos.getCategoriaEstilo()).isEqualTo(UPDATED_CATEGORIA_ESTILO);
        assertThat(testEstilos.getAbvMin()).isEqualTo(UPDATED_ABV_MIN);
        assertThat(testEstilos.getAbvMax()).isEqualTo(UPDATED_ABV_MAX);
        assertThat(testEstilos.getAbvMed()).isEqualTo(UPDATED_ABV_MED);
        assertThat(testEstilos.getIbuMin()).isEqualTo(UPDATED_IBU_MIN);
        assertThat(testEstilos.getIbuMax()).isEqualTo(UPDATED_IBU_MAX);
        assertThat(testEstilos.getIbuMed()).isEqualTo(UPDATED_IBU_MED);
        assertThat(testEstilos.getSrmMin()).isEqualTo(UPDATED_SRM_MIN);
        assertThat(testEstilos.getSrmMax()).isEqualTo(UPDATED_SRM_MAX);
        assertThat(testEstilos.getSrmMed()).isEqualTo(UPDATED_SRM_MED);
        assertThat(testEstilos.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEstilos.getEjemploNombreComercial()).isEqualTo(UPDATED_EJEMPLO_NOMBRE_COMERCIAL);
        assertThat(testEstilos.getEjemploImagenComercial()).isEqualTo(UPDATED_EJEMPLO_IMAGEN_COMERCIAL);

        // Validate the Estilos in Elasticsearch
        verify(mockEstilosSearchRepository, times(1)).save(testEstilos);
    }

    @Test
    @Transactional
    public void updateNonExistingEstilos() throws Exception {
        int databaseSizeBeforeUpdate = estilosRepository.findAll().size();

        // Create the Estilos
        EstilosDTO estilosDTO = estilosMapper.toDto(estilos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstilosMockMvc.perform(put("/api/estilos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estilosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estilos in the database
        List<Estilos> estilosList = estilosRepository.findAll();
        assertThat(estilosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Estilos in Elasticsearch
        verify(mockEstilosSearchRepository, times(0)).save(estilos);
    }

    @Test
    @Transactional
    public void deleteEstilos() throws Exception {
        // Initialize the database
        estilosRepository.saveAndFlush(estilos);

        int databaseSizeBeforeDelete = estilosRepository.findAll().size();

        // Delete the estilos
        restEstilosMockMvc.perform(delete("/api/estilos/{id}", estilos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estilos> estilosList = estilosRepository.findAll();
        assertThat(estilosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Estilos in Elasticsearch
        verify(mockEstilosSearchRepository, times(1)).deleteById(estilos.getId());
    }

    @Test
    @Transactional
    public void searchEstilos() throws Exception {
        // Initialize the database
        estilosRepository.saveAndFlush(estilos);
        when(mockEstilosSearchRepository.search(queryStringQuery("id:" + estilos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estilos), PageRequest.of(0, 1), 1));
        // Search the estilos
        restEstilosMockMvc.perform(get("/api/_search/estilos?query=id:" + estilos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estilos.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].nombreEstilo").value(hasItem(DEFAULT_NOMBRE_ESTILO)))
            .andExpect(jsonPath("$.[*].categoriaEstilo").value(hasItem(DEFAULT_CATEGORIA_ESTILO)))
            .andExpect(jsonPath("$.[*].abvMin").value(hasItem(DEFAULT_ABV_MIN.intValue())))
            .andExpect(jsonPath("$.[*].abvMax").value(hasItem(DEFAULT_ABV_MAX.intValue())))
            .andExpect(jsonPath("$.[*].abvMed").value(hasItem(DEFAULT_ABV_MED.intValue())))
            .andExpect(jsonPath("$.[*].ibuMin").value(hasItem(DEFAULT_IBU_MIN.intValue())))
            .andExpect(jsonPath("$.[*].ibuMax").value(hasItem(DEFAULT_IBU_MAX.intValue())))
            .andExpect(jsonPath("$.[*].ibuMed").value(hasItem(DEFAULT_IBU_MED.intValue())))
            .andExpect(jsonPath("$.[*].srmMin").value(hasItem(DEFAULT_SRM_MIN.intValue())))
            .andExpect(jsonPath("$.[*].srmMax").value(hasItem(DEFAULT_SRM_MAX.intValue())))
            .andExpect(jsonPath("$.[*].srmMed").value(hasItem(DEFAULT_SRM_MED.intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].ejemploNombreComercial").value(hasItem(DEFAULT_EJEMPLO_NOMBRE_COMERCIAL)))
            .andExpect(jsonPath("$.[*].ejemploImagenComercial").value(hasItem(DEFAULT_EJEMPLO_IMAGEN_COMERCIAL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estilos.class);
        Estilos estilos1 = new Estilos();
        estilos1.setId(1L);
        Estilos estilos2 = new Estilos();
        estilos2.setId(estilos1.getId());
        assertThat(estilos1).isEqualTo(estilos2);
        estilos2.setId(2L);
        assertThat(estilos1).isNotEqualTo(estilos2);
        estilos1.setId(null);
        assertThat(estilos1).isNotEqualTo(estilos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstilosDTO.class);
        EstilosDTO estilosDTO1 = new EstilosDTO();
        estilosDTO1.setId(1L);
        EstilosDTO estilosDTO2 = new EstilosDTO();
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
        estilosDTO2.setId(estilosDTO1.getId());
        assertThat(estilosDTO1).isEqualTo(estilosDTO2);
        estilosDTO2.setId(2L);
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
        estilosDTO1.setId(null);
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estilosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estilosMapper.fromId(null)).isNull();
    }
}
