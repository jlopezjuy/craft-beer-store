package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.EventoProducto;
import com.craftbeerstore.application.repository.EventoProductoRepository;
import com.craftbeerstore.application.repository.search.EventoProductoSearchRepository;
import com.craftbeerstore.application.service.EventoProductoService;
import com.craftbeerstore.application.service.dto.EventoProductoDTO;
import com.craftbeerstore.application.service.mapper.EventoProductoMapper;
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

/**
 * Test class for the EventoProductoResource REST controller.
 *
 * @see EventoProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EventoProductoResourceIntTest {

    private static final Long DEFAULT_CANTIDAD_DE_BARRILES = 1L;
    private static final Long UPDATED_CANTIDAD_DE_BARRILES = 2L;

    @Autowired
    private EventoProductoRepository eventoProductoRepository;

    @Autowired
    private EventoProductoMapper eventoProductoMapper;

    @Autowired
    private EventoProductoService eventoProductoService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.EventoProductoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EventoProductoSearchRepository mockEventoProductoSearchRepository;

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

    private MockMvc restEventoProductoMockMvc;

    private EventoProducto eventoProducto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoProductoResource eventoProductoResource = new EventoProductoResource(eventoProductoService);
        this.restEventoProductoMockMvc = MockMvcBuilders.standaloneSetup(eventoProductoResource)
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
    public static EventoProducto createEntity(EntityManager em) {
        EventoProducto eventoProducto = new EventoProducto()
            .cantidadDeBarriles(DEFAULT_CANTIDAD_DE_BARRILES);
        return eventoProducto;
    }

    @Before
    public void initTest() {
        eventoProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventoProducto() throws Exception {
        int databaseSizeBeforeCreate = eventoProductoRepository.findAll().size();

        // Create the EventoProducto
        EventoProductoDTO eventoProductoDTO = eventoProductoMapper.toDto(eventoProducto);
        restEventoProductoMockMvc.perform(post("/api/evento-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the EventoProducto in the database
        List<EventoProducto> eventoProductoList = eventoProductoRepository.findAll();
        assertThat(eventoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        EventoProducto testEventoProducto = eventoProductoList.get(eventoProductoList.size() - 1);
        assertThat(testEventoProducto.getCantidadDeBarriles()).isEqualTo(DEFAULT_CANTIDAD_DE_BARRILES);

        // Validate the EventoProducto in Elasticsearch
        verify(mockEventoProductoSearchRepository, times(1)).save(testEventoProducto);
    }

    @Test
    @Transactional
    public void createEventoProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoProductoRepository.findAll().size();

        // Create the EventoProducto with an existing ID
        eventoProducto.setId(1L);
        EventoProductoDTO eventoProductoDTO = eventoProductoMapper.toDto(eventoProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoProductoMockMvc.perform(post("/api/evento-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoProducto in the database
        List<EventoProducto> eventoProductoList = eventoProductoRepository.findAll();
        assertThat(eventoProductoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EventoProducto in Elasticsearch
        verify(mockEventoProductoSearchRepository, times(0)).save(eventoProducto);
    }

    @Test
    @Transactional
    public void getAllEventoProductos() throws Exception {
        // Initialize the database
        eventoProductoRepository.saveAndFlush(eventoProducto);

        // Get all the eventoProductoList
        restEventoProductoMockMvc.perform(get("/api/evento-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadDeBarriles").value(hasItem(DEFAULT_CANTIDAD_DE_BARRILES.intValue())));
    }
    
    @Test
    @Transactional
    public void getEventoProducto() throws Exception {
        // Initialize the database
        eventoProductoRepository.saveAndFlush(eventoProducto);

        // Get the eventoProducto
        restEventoProductoMockMvc.perform(get("/api/evento-productos/{id}", eventoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventoProducto.getId().intValue()))
            .andExpect(jsonPath("$.cantidadDeBarriles").value(DEFAULT_CANTIDAD_DE_BARRILES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEventoProducto() throws Exception {
        // Get the eventoProducto
        restEventoProductoMockMvc.perform(get("/api/evento-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventoProducto() throws Exception {
        // Initialize the database
        eventoProductoRepository.saveAndFlush(eventoProducto);

        int databaseSizeBeforeUpdate = eventoProductoRepository.findAll().size();

        // Update the eventoProducto
        EventoProducto updatedEventoProducto = eventoProductoRepository.findById(eventoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedEventoProducto are not directly saved in db
        em.detach(updatedEventoProducto);
        updatedEventoProducto
            .cantidadDeBarriles(UPDATED_CANTIDAD_DE_BARRILES);
        EventoProductoDTO eventoProductoDTO = eventoProductoMapper.toDto(updatedEventoProducto);

        restEventoProductoMockMvc.perform(put("/api/evento-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoProductoDTO)))
            .andExpect(status().isOk());

        // Validate the EventoProducto in the database
        List<EventoProducto> eventoProductoList = eventoProductoRepository.findAll();
        assertThat(eventoProductoList).hasSize(databaseSizeBeforeUpdate);
        EventoProducto testEventoProducto = eventoProductoList.get(eventoProductoList.size() - 1);
        assertThat(testEventoProducto.getCantidadDeBarriles()).isEqualTo(UPDATED_CANTIDAD_DE_BARRILES);

        // Validate the EventoProducto in Elasticsearch
        verify(mockEventoProductoSearchRepository, times(1)).save(testEventoProducto);
    }

    @Test
    @Transactional
    public void updateNonExistingEventoProducto() throws Exception {
        int databaseSizeBeforeUpdate = eventoProductoRepository.findAll().size();

        // Create the EventoProducto
        EventoProductoDTO eventoProductoDTO = eventoProductoMapper.toDto(eventoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoProductoMockMvc.perform(put("/api/evento-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoProducto in the database
        List<EventoProducto> eventoProductoList = eventoProductoRepository.findAll();
        assertThat(eventoProductoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EventoProducto in Elasticsearch
        verify(mockEventoProductoSearchRepository, times(0)).save(eventoProducto);
    }

    @Test
    @Transactional
    public void deleteEventoProducto() throws Exception {
        // Initialize the database
        eventoProductoRepository.saveAndFlush(eventoProducto);

        int databaseSizeBeforeDelete = eventoProductoRepository.findAll().size();

        // Delete the eventoProducto
        restEventoProductoMockMvc.perform(delete("/api/evento-productos/{id}", eventoProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EventoProducto> eventoProductoList = eventoProductoRepository.findAll();
        assertThat(eventoProductoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EventoProducto in Elasticsearch
        verify(mockEventoProductoSearchRepository, times(1)).deleteById(eventoProducto.getId());
    }

    @Test
    @Transactional
    public void searchEventoProducto() throws Exception {
        // Initialize the database
        eventoProductoRepository.saveAndFlush(eventoProducto);
        when(mockEventoProductoSearchRepository.search(queryStringQuery("id:" + eventoProducto.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(eventoProducto), PageRequest.of(0, 1), 1));
        // Search the eventoProducto
        restEventoProductoMockMvc.perform(get("/api/_search/evento-productos?query=id:" + eventoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidadDeBarriles").value(hasItem(DEFAULT_CANTIDAD_DE_BARRILES.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoProducto.class);
        EventoProducto eventoProducto1 = new EventoProducto();
        eventoProducto1.setId(1L);
        EventoProducto eventoProducto2 = new EventoProducto();
        eventoProducto2.setId(eventoProducto1.getId());
        assertThat(eventoProducto1).isEqualTo(eventoProducto2);
        eventoProducto2.setId(2L);
        assertThat(eventoProducto1).isNotEqualTo(eventoProducto2);
        eventoProducto1.setId(null);
        assertThat(eventoProducto1).isNotEqualTo(eventoProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoProductoDTO.class);
        EventoProductoDTO eventoProductoDTO1 = new EventoProductoDTO();
        eventoProductoDTO1.setId(1L);
        EventoProductoDTO eventoProductoDTO2 = new EventoProductoDTO();
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
        eventoProductoDTO2.setId(eventoProductoDTO1.getId());
        assertThat(eventoProductoDTO1).isEqualTo(eventoProductoDTO2);
        eventoProductoDTO2.setId(2L);
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
        eventoProductoDTO1.setId(null);
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventoProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventoProductoMapper.fromId(null)).isNull();
    }
}
