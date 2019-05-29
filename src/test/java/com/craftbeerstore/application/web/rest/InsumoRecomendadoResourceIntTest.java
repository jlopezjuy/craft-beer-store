package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.InsumoRecomendado;
import com.craftbeerstore.application.repository.InsumoRecomendadoRepository;
import com.craftbeerstore.application.repository.search.InsumoRecomendadoSearchRepository;
import com.craftbeerstore.application.service.InsumoRecomendadoService;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;
import com.craftbeerstore.application.service.mapper.InsumoRecomendadoMapper;
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
 * Test class for the InsumoRecomendadoResource REST controller.
 *
 * @see InsumoRecomendadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class InsumoRecomendadoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final TipoInsumo DEFAULT_TIPO = TipoInsumo.MALTA;
    private static final TipoInsumo UPDATED_TIPO = TipoInsumo.LUPULO;

    @Autowired
    private InsumoRecomendadoRepository insumoRecomendadoRepository;

    @Autowired
    private InsumoRecomendadoMapper insumoRecomendadoMapper;

    @Autowired
    private InsumoRecomendadoService insumoRecomendadoService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.InsumoRecomendadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private InsumoRecomendadoSearchRepository mockInsumoRecomendadoSearchRepository;

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

    private MockMvc restInsumoRecomendadoMockMvc;

    private InsumoRecomendado insumoRecomendado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsumoRecomendadoResource insumoRecomendadoResource = new InsumoRecomendadoResource(insumoRecomendadoService);
        this.restInsumoRecomendadoMockMvc = MockMvcBuilders.standaloneSetup(insumoRecomendadoResource)
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
    public static InsumoRecomendado createEntity(EntityManager em) {
        InsumoRecomendado insumoRecomendado = new InsumoRecomendado()
            .nombre(DEFAULT_NOMBRE)
            .marca(DEFAULT_MARCA)
            .tipo(DEFAULT_TIPO);
        return insumoRecomendado;
    }

    @Before
    public void initTest() {
        insumoRecomendado = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsumoRecomendado() throws Exception {
        int databaseSizeBeforeCreate = insumoRecomendadoRepository.findAll().size();

        // Create the InsumoRecomendado
        InsumoRecomendadoDTO insumoRecomendadoDTO = insumoRecomendadoMapper.toDto(insumoRecomendado);
        restInsumoRecomendadoMockMvc.perform(post("/api/insumo-recomendados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoRecomendadoDTO)))
            .andExpect(status().isCreated());

        // Validate the InsumoRecomendado in the database
        List<InsumoRecomendado> insumoRecomendadoList = insumoRecomendadoRepository.findAll();
        assertThat(insumoRecomendadoList).hasSize(databaseSizeBeforeCreate + 1);
        InsumoRecomendado testInsumoRecomendado = insumoRecomendadoList.get(insumoRecomendadoList.size() - 1);
        assertThat(testInsumoRecomendado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInsumoRecomendado.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testInsumoRecomendado.getTipo()).isEqualTo(DEFAULT_TIPO);

        // Validate the InsumoRecomendado in Elasticsearch
        verify(mockInsumoRecomendadoSearchRepository, times(1)).save(testInsumoRecomendado);
    }

    @Test
    @Transactional
    public void createInsumoRecomendadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insumoRecomendadoRepository.findAll().size();

        // Create the InsumoRecomendado with an existing ID
        insumoRecomendado.setId(1L);
        InsumoRecomendadoDTO insumoRecomendadoDTO = insumoRecomendadoMapper.toDto(insumoRecomendado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsumoRecomendadoMockMvc.perform(post("/api/insumo-recomendados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoRecomendadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsumoRecomendado in the database
        List<InsumoRecomendado> insumoRecomendadoList = insumoRecomendadoRepository.findAll();
        assertThat(insumoRecomendadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the InsumoRecomendado in Elasticsearch
        verify(mockInsumoRecomendadoSearchRepository, times(0)).save(insumoRecomendado);
    }

    @Test
    @Transactional
    public void getAllInsumoRecomendados() throws Exception {
        // Initialize the database
        insumoRecomendadoRepository.saveAndFlush(insumoRecomendado);

        // Get all the insumoRecomendadoList
        restInsumoRecomendadoMockMvc.perform(get("/api/insumo-recomendados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insumoRecomendado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getInsumoRecomendado() throws Exception {
        // Initialize the database
        insumoRecomendadoRepository.saveAndFlush(insumoRecomendado);

        // Get the insumoRecomendado
        restInsumoRecomendadoMockMvc.perform(get("/api/insumo-recomendados/{id}", insumoRecomendado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insumoRecomendado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInsumoRecomendado() throws Exception {
        // Get the insumoRecomendado
        restInsumoRecomendadoMockMvc.perform(get("/api/insumo-recomendados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsumoRecomendado() throws Exception {
        // Initialize the database
        insumoRecomendadoRepository.saveAndFlush(insumoRecomendado);

        int databaseSizeBeforeUpdate = insumoRecomendadoRepository.findAll().size();

        // Update the insumoRecomendado
        InsumoRecomendado updatedInsumoRecomendado = insumoRecomendadoRepository.findById(insumoRecomendado.getId()).get();
        // Disconnect from session so that the updates on updatedInsumoRecomendado are not directly saved in db
        em.detach(updatedInsumoRecomendado);
        updatedInsumoRecomendado
            .nombre(UPDATED_NOMBRE)
            .marca(UPDATED_MARCA)
            .tipo(UPDATED_TIPO);
        InsumoRecomendadoDTO insumoRecomendadoDTO = insumoRecomendadoMapper.toDto(updatedInsumoRecomendado);

        restInsumoRecomendadoMockMvc.perform(put("/api/insumo-recomendados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoRecomendadoDTO)))
            .andExpect(status().isOk());

        // Validate the InsumoRecomendado in the database
        List<InsumoRecomendado> insumoRecomendadoList = insumoRecomendadoRepository.findAll();
        assertThat(insumoRecomendadoList).hasSize(databaseSizeBeforeUpdate);
        InsumoRecomendado testInsumoRecomendado = insumoRecomendadoList.get(insumoRecomendadoList.size() - 1);
        assertThat(testInsumoRecomendado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInsumoRecomendado.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testInsumoRecomendado.getTipo()).isEqualTo(UPDATED_TIPO);

        // Validate the InsumoRecomendado in Elasticsearch
        verify(mockInsumoRecomendadoSearchRepository, times(1)).save(testInsumoRecomendado);
    }

    @Test
    @Transactional
    public void updateNonExistingInsumoRecomendado() throws Exception {
        int databaseSizeBeforeUpdate = insumoRecomendadoRepository.findAll().size();

        // Create the InsumoRecomendado
        InsumoRecomendadoDTO insumoRecomendadoDTO = insumoRecomendadoMapper.toDto(insumoRecomendado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsumoRecomendadoMockMvc.perform(put("/api/insumo-recomendados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoRecomendadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InsumoRecomendado in the database
        List<InsumoRecomendado> insumoRecomendadoList = insumoRecomendadoRepository.findAll();
        assertThat(insumoRecomendadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InsumoRecomendado in Elasticsearch
        verify(mockInsumoRecomendadoSearchRepository, times(0)).save(insumoRecomendado);
    }

    @Test
    @Transactional
    public void deleteInsumoRecomendado() throws Exception {
        // Initialize the database
        insumoRecomendadoRepository.saveAndFlush(insumoRecomendado);

        int databaseSizeBeforeDelete = insumoRecomendadoRepository.findAll().size();

        // Delete the insumoRecomendado
        restInsumoRecomendadoMockMvc.perform(delete("/api/insumo-recomendados/{id}", insumoRecomendado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsumoRecomendado> insumoRecomendadoList = insumoRecomendadoRepository.findAll();
        assertThat(insumoRecomendadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InsumoRecomendado in Elasticsearch
        verify(mockInsumoRecomendadoSearchRepository, times(1)).deleteById(insumoRecomendado.getId());
    }

    @Test
    @Transactional
    public void searchInsumoRecomendado() throws Exception {
        // Initialize the database
        insumoRecomendadoRepository.saveAndFlush(insumoRecomendado);
        when(mockInsumoRecomendadoSearchRepository.search(queryStringQuery("id:" + insumoRecomendado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(insumoRecomendado), PageRequest.of(0, 1), 1));
        // Search the insumoRecomendado
        restInsumoRecomendadoMockMvc.perform(get("/api/_search/insumo-recomendados?query=id:" + insumoRecomendado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insumoRecomendado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumoRecomendado.class);
        InsumoRecomendado insumoRecomendado1 = new InsumoRecomendado();
        insumoRecomendado1.setId(1L);
        InsumoRecomendado insumoRecomendado2 = new InsumoRecomendado();
        insumoRecomendado2.setId(insumoRecomendado1.getId());
        assertThat(insumoRecomendado1).isEqualTo(insumoRecomendado2);
        insumoRecomendado2.setId(2L);
        assertThat(insumoRecomendado1).isNotEqualTo(insumoRecomendado2);
        insumoRecomendado1.setId(null);
        assertThat(insumoRecomendado1).isNotEqualTo(insumoRecomendado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumoRecomendadoDTO.class);
        InsumoRecomendadoDTO insumoRecomendadoDTO1 = new InsumoRecomendadoDTO();
        insumoRecomendadoDTO1.setId(1L);
        InsumoRecomendadoDTO insumoRecomendadoDTO2 = new InsumoRecomendadoDTO();
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO2.setId(insumoRecomendadoDTO1.getId());
        assertThat(insumoRecomendadoDTO1).isEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO2.setId(2L);
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO1.setId(null);
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(insumoRecomendadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(insumoRecomendadoMapper.fromId(null)).isNull();
    }
}
