package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.repository.RecetaRepository;
import com.craftbeerstore.application.repository.search.RecetaSearchRepository;
import com.craftbeerstore.application.service.RecetaService;
import com.craftbeerstore.application.service.dto.RecetaDTO;
import com.craftbeerstore.application.service.mapper.RecetaMapper;
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

/**
 * Test class for the RecetaResource REST controller.
 *
 * @see RecetaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class RecetaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_BREW_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_BREW_MASTER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BATCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_BATCH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TEMPERATURA_DE_MACERADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TEMPERATURA_DE_MACERADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OG = new BigDecimal(1);
    private static final BigDecimal UPDATED_OG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FG = new BigDecimal(1);
    private static final BigDecimal UPDATED_FG = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ABV = new BigDecimal(1);
    private static final BigDecimal UPDATED_ABV = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IBU = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SRM = new BigDecimal(1);
    private static final BigDecimal UPDATED_SRM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EMPASTE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EMPASTE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TEMPERATURA_MACERADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TEMPERATURA_MACERADO = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private RecetaMapper recetaMapper;

    @Autowired
    private RecetaService recetaService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.RecetaSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecetaSearchRepository mockRecetaSearchRepository;

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

    private MockMvc restRecetaMockMvc;

    private Receta receta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecetaResource recetaResource = new RecetaResource(recetaService);
        this.restRecetaMockMvc = MockMvcBuilders.standaloneSetup(recetaResource)
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
    public static Receta createEntity(EntityManager em) {
        Receta receta = new Receta()
            .nombre(DEFAULT_NOMBRE)
            .brewMaster(DEFAULT_BREW_MASTER)
            .batch(DEFAULT_BATCH)
            .temperaturaDeMacerado(DEFAULT_TEMPERATURA_DE_MACERADO)
            .og(DEFAULT_OG)
            .fg(DEFAULT_FG)
            .abv(DEFAULT_ABV)
            .ibu(DEFAULT_IBU)
            .srm(DEFAULT_SRM)
            .empaste(DEFAULT_EMPASTE)
            .temperaturaMacerado(DEFAULT_TEMPERATURA_MACERADO)
            .fecha(DEFAULT_FECHA);
        return receta;
    }

    @Before
    public void initTest() {
        receta = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceta() throws Exception {
        int databaseSizeBeforeCreate = recetaRepository.findAll().size();

        // Create the Receta
        RecetaDTO recetaDTO = recetaMapper.toDto(receta);
        restRecetaMockMvc.perform(post("/api/recetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaDTO)))
            .andExpect(status().isCreated());

        // Validate the Receta in the database
        List<Receta> recetaList = recetaRepository.findAll();
        assertThat(recetaList).hasSize(databaseSizeBeforeCreate + 1);
        Receta testReceta = recetaList.get(recetaList.size() - 1);
        assertThat(testReceta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testReceta.getBrewMaster()).isEqualTo(DEFAULT_BREW_MASTER);
        assertThat(testReceta.getBatch()).isEqualTo(DEFAULT_BATCH);
        assertThat(testReceta.getTemperaturaDeMacerado()).isEqualTo(DEFAULT_TEMPERATURA_DE_MACERADO);
        assertThat(testReceta.getOg()).isEqualTo(DEFAULT_OG);
        assertThat(testReceta.getFg()).isEqualTo(DEFAULT_FG);
        assertThat(testReceta.getAbv()).isEqualTo(DEFAULT_ABV);
        assertThat(testReceta.getIbu()).isEqualTo(DEFAULT_IBU);
        assertThat(testReceta.getSrm()).isEqualTo(DEFAULT_SRM);
        assertThat(testReceta.getEmpaste()).isEqualTo(DEFAULT_EMPASTE);
        assertThat(testReceta.getTemperaturaMacerado()).isEqualTo(DEFAULT_TEMPERATURA_MACERADO);
        assertThat(testReceta.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the Receta in Elasticsearch
        verify(mockRecetaSearchRepository, times(1)).save(testReceta);
    }

    @Test
    @Transactional
    public void createRecetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recetaRepository.findAll().size();

        // Create the Receta with an existing ID
        receta.setId(1L);
        RecetaDTO recetaDTO = recetaMapper.toDto(receta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecetaMockMvc.perform(post("/api/recetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receta in the database
        List<Receta> recetaList = recetaRepository.findAll();
        assertThat(recetaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Receta in Elasticsearch
        verify(mockRecetaSearchRepository, times(0)).save(receta);
    }

    @Test
    @Transactional
    public void getAllRecetas() throws Exception {
        // Initialize the database
        recetaRepository.saveAndFlush(receta);

        // Get all the recetaList
        restRecetaMockMvc.perform(get("/api/recetas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].brewMaster").value(hasItem(DEFAULT_BREW_MASTER.toString())))
            .andExpect(jsonPath("$.[*].batch").value(hasItem(DEFAULT_BATCH.intValue())))
            .andExpect(jsonPath("$.[*].temperaturaDeMacerado").value(hasItem(DEFAULT_TEMPERATURA_DE_MACERADO.intValue())))
            .andExpect(jsonPath("$.[*].og").value(hasItem(DEFAULT_OG.intValue())))
            .andExpect(jsonPath("$.[*].fg").value(hasItem(DEFAULT_FG.intValue())))
            .andExpect(jsonPath("$.[*].abv").value(hasItem(DEFAULT_ABV.intValue())))
            .andExpect(jsonPath("$.[*].ibu").value(hasItem(DEFAULT_IBU.intValue())))
            .andExpect(jsonPath("$.[*].srm").value(hasItem(DEFAULT_SRM.intValue())))
            .andExpect(jsonPath("$.[*].empaste").value(hasItem(DEFAULT_EMPASTE.intValue())))
            .andExpect(jsonPath("$.[*].temperaturaMacerado").value(hasItem(DEFAULT_TEMPERATURA_MACERADO.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getReceta() throws Exception {
        // Initialize the database
        recetaRepository.saveAndFlush(receta);

        // Get the receta
        restRecetaMockMvc.perform(get("/api/recetas/{id}", receta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.brewMaster").value(DEFAULT_BREW_MASTER.toString()))
            .andExpect(jsonPath("$.batch").value(DEFAULT_BATCH.intValue()))
            .andExpect(jsonPath("$.temperaturaDeMacerado").value(DEFAULT_TEMPERATURA_DE_MACERADO.intValue()))
            .andExpect(jsonPath("$.og").value(DEFAULT_OG.intValue()))
            .andExpect(jsonPath("$.fg").value(DEFAULT_FG.intValue()))
            .andExpect(jsonPath("$.abv").value(DEFAULT_ABV.intValue()))
            .andExpect(jsonPath("$.ibu").value(DEFAULT_IBU.intValue()))
            .andExpect(jsonPath("$.srm").value(DEFAULT_SRM.intValue()))
            .andExpect(jsonPath("$.empaste").value(DEFAULT_EMPASTE.intValue()))
            .andExpect(jsonPath("$.temperaturaMacerado").value(DEFAULT_TEMPERATURA_MACERADO.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceta() throws Exception {
        // Get the receta
        restRecetaMockMvc.perform(get("/api/recetas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceta() throws Exception {
        // Initialize the database
        recetaRepository.saveAndFlush(receta);

        int databaseSizeBeforeUpdate = recetaRepository.findAll().size();

        // Update the receta
        Receta updatedReceta = recetaRepository.findById(receta.getId()).get();
        // Disconnect from session so that the updates on updatedReceta are not directly saved in db
        em.detach(updatedReceta);
        updatedReceta
            .nombre(UPDATED_NOMBRE)
            .brewMaster(UPDATED_BREW_MASTER)
            .batch(UPDATED_BATCH)
            .temperaturaDeMacerado(UPDATED_TEMPERATURA_DE_MACERADO)
            .og(UPDATED_OG)
            .fg(UPDATED_FG)
            .abv(UPDATED_ABV)
            .ibu(UPDATED_IBU)
            .srm(UPDATED_SRM)
            .empaste(UPDATED_EMPASTE)
            .temperaturaMacerado(UPDATED_TEMPERATURA_MACERADO)
            .fecha(UPDATED_FECHA);
        RecetaDTO recetaDTO = recetaMapper.toDto(updatedReceta);

        restRecetaMockMvc.perform(put("/api/recetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaDTO)))
            .andExpect(status().isOk());

        // Validate the Receta in the database
        List<Receta> recetaList = recetaRepository.findAll();
        assertThat(recetaList).hasSize(databaseSizeBeforeUpdate);
        Receta testReceta = recetaList.get(recetaList.size() - 1);
        assertThat(testReceta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testReceta.getBrewMaster()).isEqualTo(UPDATED_BREW_MASTER);
        assertThat(testReceta.getBatch()).isEqualTo(UPDATED_BATCH);
        assertThat(testReceta.getTemperaturaDeMacerado()).isEqualTo(UPDATED_TEMPERATURA_DE_MACERADO);
        assertThat(testReceta.getOg()).isEqualTo(UPDATED_OG);
        assertThat(testReceta.getFg()).isEqualTo(UPDATED_FG);
        assertThat(testReceta.getAbv()).isEqualTo(UPDATED_ABV);
        assertThat(testReceta.getIbu()).isEqualTo(UPDATED_IBU);
        assertThat(testReceta.getSrm()).isEqualTo(UPDATED_SRM);
        assertThat(testReceta.getEmpaste()).isEqualTo(UPDATED_EMPASTE);
        assertThat(testReceta.getTemperaturaMacerado()).isEqualTo(UPDATED_TEMPERATURA_MACERADO);
        assertThat(testReceta.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the Receta in Elasticsearch
        verify(mockRecetaSearchRepository, times(1)).save(testReceta);
    }

    @Test
    @Transactional
    public void updateNonExistingReceta() throws Exception {
        int databaseSizeBeforeUpdate = recetaRepository.findAll().size();

        // Create the Receta
        RecetaDTO recetaDTO = recetaMapper.toDto(receta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetaMockMvc.perform(put("/api/recetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receta in the database
        List<Receta> recetaList = recetaRepository.findAll();
        assertThat(recetaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Receta in Elasticsearch
        verify(mockRecetaSearchRepository, times(0)).save(receta);
    }

    @Test
    @Transactional
    public void deleteReceta() throws Exception {
        // Initialize the database
        recetaRepository.saveAndFlush(receta);

        int databaseSizeBeforeDelete = recetaRepository.findAll().size();

        // Delete the receta
        restRecetaMockMvc.perform(delete("/api/recetas/{id}", receta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Receta> recetaList = recetaRepository.findAll();
        assertThat(recetaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Receta in Elasticsearch
        verify(mockRecetaSearchRepository, times(1)).deleteById(receta.getId());
    }

    @Test
    @Transactional
    public void searchReceta() throws Exception {
        // Initialize the database
        recetaRepository.saveAndFlush(receta);
        when(mockRecetaSearchRepository.search(queryStringQuery("id:" + receta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(receta), PageRequest.of(0, 1), 1));
        // Search the receta
        restRecetaMockMvc.perform(get("/api/_search/recetas?query=id:" + receta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].brewMaster").value(hasItem(DEFAULT_BREW_MASTER)))
            .andExpect(jsonPath("$.[*].batch").value(hasItem(DEFAULT_BATCH.intValue())))
            .andExpect(jsonPath("$.[*].temperaturaDeMacerado").value(hasItem(DEFAULT_TEMPERATURA_DE_MACERADO.intValue())))
            .andExpect(jsonPath("$.[*].og").value(hasItem(DEFAULT_OG.intValue())))
            .andExpect(jsonPath("$.[*].fg").value(hasItem(DEFAULT_FG.intValue())))
            .andExpect(jsonPath("$.[*].abv").value(hasItem(DEFAULT_ABV.intValue())))
            .andExpect(jsonPath("$.[*].ibu").value(hasItem(DEFAULT_IBU.intValue())))
            .andExpect(jsonPath("$.[*].srm").value(hasItem(DEFAULT_SRM.intValue())))
            .andExpect(jsonPath("$.[*].empaste").value(hasItem(DEFAULT_EMPASTE.intValue())))
            .andExpect(jsonPath("$.[*].temperaturaMacerado").value(hasItem(DEFAULT_TEMPERATURA_MACERADO.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receta.class);
        Receta receta1 = new Receta();
        receta1.setId(1L);
        Receta receta2 = new Receta();
        receta2.setId(receta1.getId());
        assertThat(receta1).isEqualTo(receta2);
        receta2.setId(2L);
        assertThat(receta1).isNotEqualTo(receta2);
        receta1.setId(null);
        assertThat(receta1).isNotEqualTo(receta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaDTO.class);
        RecetaDTO recetaDTO1 = new RecetaDTO();
        recetaDTO1.setId(1L);
        RecetaDTO recetaDTO2 = new RecetaDTO();
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
        recetaDTO2.setId(recetaDTO1.getId());
        assertThat(recetaDTO1).isEqualTo(recetaDTO2);
        recetaDTO2.setId(2L);
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
        recetaDTO1.setId(null);
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recetaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recetaMapper.fromId(null)).isNull();
    }
}
