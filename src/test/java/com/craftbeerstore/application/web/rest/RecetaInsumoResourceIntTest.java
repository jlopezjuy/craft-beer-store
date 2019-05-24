package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.RecetaInsumo;
import com.craftbeerstore.application.repository.RecetaInsumoRepository;
import com.craftbeerstore.application.repository.search.RecetaInsumoSearchRepository;
import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
import com.craftbeerstore.application.service.mapper.RecetaInsumoMapper;
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
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
/**
 * Test class for the RecetaInsumoResource REST controller.
 *
 * @see RecetaInsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class RecetaInsumoResourceIntTest {

    private static final TipoInsumo DEFAULT_TIPO_INSUMO = TipoInsumo.MALTA;
    private static final TipoInsumo UPDATED_TIPO_INSUMO = TipoInsumo.LUPULO;

    @Autowired
    private RecetaInsumoRepository recetaInsumoRepository;

    @Autowired
    private RecetaInsumoMapper recetaInsumoMapper;

    @Autowired
    private RecetaInsumoService recetaInsumoService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.RecetaInsumoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecetaInsumoSearchRepository mockRecetaInsumoSearchRepository;

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

    private MockMvc restRecetaInsumoMockMvc;

    private RecetaInsumo recetaInsumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecetaInsumoResource recetaInsumoResource = new RecetaInsumoResource(recetaInsumoService);
        this.restRecetaInsumoMockMvc = MockMvcBuilders.standaloneSetup(recetaInsumoResource)
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
    public static RecetaInsumo createEntity(EntityManager em) {
        RecetaInsumo recetaInsumo = new RecetaInsumo()
            .tipoInsumo(DEFAULT_TIPO_INSUMO);
        return recetaInsumo;
    }

    @Before
    public void initTest() {
        recetaInsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecetaInsumo() throws Exception {
        int databaseSizeBeforeCreate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);
        restRecetaInsumoMockMvc.perform(post("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeCreate + 1);
        RecetaInsumo testRecetaInsumo = recetaInsumoList.get(recetaInsumoList.size() - 1);
        assertThat(testRecetaInsumo.getTipoInsumo()).isEqualTo(DEFAULT_TIPO_INSUMO);

        // Validate the RecetaInsumo in Elasticsearch
        verify(mockRecetaInsumoSearchRepository, times(1)).save(testRecetaInsumo);
    }

    @Test
    @Transactional
    public void createRecetaInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo with an existing ID
        recetaInsumo.setId(1L);
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecetaInsumoMockMvc.perform(post("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeCreate);

        // Validate the RecetaInsumo in Elasticsearch
        verify(mockRecetaInsumoSearchRepository, times(0)).save(recetaInsumo);
    }

    @Test
    @Transactional
    public void getAllRecetaInsumos() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        // Get all the recetaInsumoList
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recetaInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoInsumo").value(hasItem(DEFAULT_TIPO_INSUMO.toString())));
    }
    
    @Test
    @Transactional
    public void getRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        // Get the recetaInsumo
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos/{id}", recetaInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recetaInsumo.getId().intValue()))
            .andExpect(jsonPath("$.tipoInsumo").value(DEFAULT_TIPO_INSUMO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecetaInsumo() throws Exception {
        // Get the recetaInsumo
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        int databaseSizeBeforeUpdate = recetaInsumoRepository.findAll().size();

        // Update the recetaInsumo
        RecetaInsumo updatedRecetaInsumo = recetaInsumoRepository.findById(recetaInsumo.getId()).get();
        // Disconnect from session so that the updates on updatedRecetaInsumo are not directly saved in db
        em.detach(updatedRecetaInsumo);
        updatedRecetaInsumo
            .tipoInsumo(UPDATED_TIPO_INSUMO);
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(updatedRecetaInsumo);

        restRecetaInsumoMockMvc.perform(put("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isOk());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeUpdate);
        RecetaInsumo testRecetaInsumo = recetaInsumoList.get(recetaInsumoList.size() - 1);
        assertThat(testRecetaInsumo.getTipoInsumo()).isEqualTo(UPDATED_TIPO_INSUMO);

        // Validate the RecetaInsumo in Elasticsearch
        verify(mockRecetaInsumoSearchRepository, times(1)).save(testRecetaInsumo);
    }

    @Test
    @Transactional
    public void updateNonExistingRecetaInsumo() throws Exception {
        int databaseSizeBeforeUpdate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetaInsumoMockMvc.perform(put("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RecetaInsumo in Elasticsearch
        verify(mockRecetaInsumoSearchRepository, times(0)).save(recetaInsumo);
    }

    @Test
    @Transactional
    public void deleteRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        int databaseSizeBeforeDelete = recetaInsumoRepository.findAll().size();

        // Delete the recetaInsumo
        restRecetaInsumoMockMvc.perform(delete("/api/receta-insumos/{id}", recetaInsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RecetaInsumo in Elasticsearch
        verify(mockRecetaInsumoSearchRepository, times(1)).deleteById(recetaInsumo.getId());
    }

    @Test
    @Transactional
    public void searchRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);
        when(mockRecetaInsumoSearchRepository.search(queryStringQuery("id:" + recetaInsumo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recetaInsumo), PageRequest.of(0, 1), 1));
        // Search the recetaInsumo
        restRecetaInsumoMockMvc.perform(get("/api/_search/receta-insumos?query=id:" + recetaInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recetaInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoInsumo").value(hasItem(DEFAULT_TIPO_INSUMO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumo.class);
        RecetaInsumo recetaInsumo1 = new RecetaInsumo();
        recetaInsumo1.setId(1L);
        RecetaInsumo recetaInsumo2 = new RecetaInsumo();
        recetaInsumo2.setId(recetaInsumo1.getId());
        assertThat(recetaInsumo1).isEqualTo(recetaInsumo2);
        recetaInsumo2.setId(2L);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
        recetaInsumo1.setId(null);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumoDTO.class);
        RecetaInsumoDTO recetaInsumoDTO1 = new RecetaInsumoDTO();
        recetaInsumoDTO1.setId(1L);
        RecetaInsumoDTO recetaInsumoDTO2 = new RecetaInsumoDTO();
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(recetaInsumoDTO1.getId());
        assertThat(recetaInsumoDTO1).isEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(2L);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO1.setId(null);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recetaInsumoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recetaInsumoMapper.fromId(null)).isNull();
    }
}
