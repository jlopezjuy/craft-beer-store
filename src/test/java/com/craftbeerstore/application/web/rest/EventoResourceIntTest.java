package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Evento;
import com.craftbeerstore.application.repository.EventoRepository;
import com.craftbeerstore.application.repository.search.EventoSearchRepository;
import com.craftbeerstore.application.service.EventoService;
import com.craftbeerstore.application.service.dto.EventoDTO;
import com.craftbeerstore.application.service.mapper.EventoMapper;
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
 * Test class for the EventoResource REST controller.
 *
 * @see EventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EventoResourceIntTest {

    private static final String DEFAULT_NOMBRE_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EVENTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_EVENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EVENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CANTIDAD_BARRILES = 1L;
    private static final Long UPDATED_CANTIDAD_BARRILES = 2L;

    private static final BigDecimal DEFAULT_PRECIO_PINTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_PINTA = new BigDecimal(2);

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoMapper eventoMapper;

    @Autowired
    private EventoService eventoService;

    /**
     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
     *
     * @see com.craftbeerstore.application.repository.search.EventoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EventoSearchRepository mockEventoSearchRepository;

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

    private MockMvc restEventoMockMvc;

    private Evento evento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventoResource eventoResource = new EventoResource(eventoService);
        this.restEventoMockMvc = MockMvcBuilders.standaloneSetup(eventoResource)
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
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
            .nombreEvento(DEFAULT_NOMBRE_EVENTO)
            .fechaEvento(DEFAULT_FECHA_EVENTO)
            .cantidadBarriles(DEFAULT_CANTIDAD_BARRILES)
            .precioPinta(DEFAULT_PRECIO_PINTA);
        return evento;
    }

    @Before
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento
        EventoDTO eventoDTO = eventoMapper.toDto(evento);
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getNombreEvento()).isEqualTo(DEFAULT_NOMBRE_EVENTO);
        assertThat(testEvento.getFechaEvento()).isEqualTo(DEFAULT_FECHA_EVENTO);
        assertThat(testEvento.getCantidadBarriles()).isEqualTo(DEFAULT_CANTIDAD_BARRILES);
        assertThat(testEvento.getPrecioPinta()).isEqualTo(DEFAULT_PRECIO_PINTA);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).save(testEvento);
    }

    @Test
    @Transactional
    public void createEventoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento with an existing ID
        evento.setId(1L);
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(0)).save(evento);
    }

    @Test
    @Transactional
    public void checkNombreEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setNombreEvento(null);

        // Create the Evento, which fails.
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setFechaEvento(null);

        // Create the Evento, which fails.
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadBarrilesIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setCantidadBarriles(null);

        // Create the Evento, which fails.
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioPintaIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setPrecioPinta(null);

        // Create the Evento, which fails.
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        restEventoMockMvc.perform(post("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEvento").value(hasItem(DEFAULT_NOMBRE_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaEvento").value(hasItem(DEFAULT_FECHA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].cantidadBarriles").value(hasItem(DEFAULT_CANTIDAD_BARRILES.intValue())))
            .andExpect(jsonPath("$.[*].precioPinta").value(hasItem(DEFAULT_PRECIO_PINTA.intValue())));
    }
    
    @Test
    @Transactional
    public void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.nombreEvento").value(DEFAULT_NOMBRE_EVENTO.toString()))
            .andExpect(jsonPath("$.fechaEvento").value(DEFAULT_FECHA_EVENTO.toString()))
            .andExpect(jsonPath("$.cantidadBarriles").value(DEFAULT_CANTIDAD_BARRILES.intValue()))
            .andExpect(jsonPath("$.precioPinta").value(DEFAULT_PRECIO_PINTA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findById(evento.getId()).get();
        // Disconnect from session so that the updates on updatedEvento are not directly saved in db
        em.detach(updatedEvento);
        updatedEvento
            .nombreEvento(UPDATED_NOMBRE_EVENTO)
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .cantidadBarriles(UPDATED_CANTIDAD_BARRILES)
            .precioPinta(UPDATED_PRECIO_PINTA);
        EventoDTO eventoDTO = eventoMapper.toDto(updatedEvento);

        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getNombreEvento()).isEqualTo(UPDATED_NOMBRE_EVENTO);
        assertThat(testEvento.getFechaEvento()).isEqualTo(UPDATED_FECHA_EVENTO);
        assertThat(testEvento.getCantidadBarriles()).isEqualTo(UPDATED_CANTIDAD_BARRILES);
        assertThat(testEvento.getPrecioPinta()).isEqualTo(UPDATED_PRECIO_PINTA);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).save(testEvento);
    }

    @Test
    @Transactional
    public void updateNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Create the Evento
        EventoDTO eventoDTO = eventoMapper.toDto(evento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc.perform(put("/api/eventos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(0)).save(evento);
    }

    @Test
    @Transactional
    public void deleteEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Delete the evento
        restEventoMockMvc.perform(delete("/api/eventos/{id}", evento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Evento in Elasticsearch
        verify(mockEventoSearchRepository, times(1)).deleteById(evento.getId());
    }

    @Test
    @Transactional
    public void searchEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);
        when(mockEventoSearchRepository.search(queryStringQuery("id:" + evento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(evento), PageRequest.of(0, 1), 1));
        // Search the evento
        restEventoMockMvc.perform(get("/api/_search/eventos?query=id:" + evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEvento").value(hasItem(DEFAULT_NOMBRE_EVENTO)))
            .andExpect(jsonPath("$.[*].fechaEvento").value(hasItem(DEFAULT_FECHA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].cantidadBarriles").value(hasItem(DEFAULT_CANTIDAD_BARRILES.intValue())))
            .andExpect(jsonPath("$.[*].precioPinta").value(hasItem(DEFAULT_PRECIO_PINTA.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evento.class);
        Evento evento1 = new Evento();
        evento1.setId(1L);
        Evento evento2 = new Evento();
        evento2.setId(evento1.getId());
        assertThat(evento1).isEqualTo(evento2);
        evento2.setId(2L);
        assertThat(evento1).isNotEqualTo(evento2);
        evento1.setId(null);
        assertThat(evento1).isNotEqualTo(evento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoDTO.class);
        EventoDTO eventoDTO1 = new EventoDTO();
        eventoDTO1.setId(1L);
        EventoDTO eventoDTO2 = new EventoDTO();
        assertThat(eventoDTO1).isNotEqualTo(eventoDTO2);
        eventoDTO2.setId(eventoDTO1.getId());
        assertThat(eventoDTO1).isEqualTo(eventoDTO2);
        eventoDTO2.setId(2L);
        assertThat(eventoDTO1).isNotEqualTo(eventoDTO2);
        eventoDTO1.setId(null);
        assertThat(eventoDTO1).isNotEqualTo(eventoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventoMapper.fromId(null)).isNull();
    }
}
