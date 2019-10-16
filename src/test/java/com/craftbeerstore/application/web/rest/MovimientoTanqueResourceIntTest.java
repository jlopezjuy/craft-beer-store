package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.MovimientoTanque;
import com.craftbeerstore.application.repository.MovimientoTanqueRepository;
import com.craftbeerstore.application.service.MovimientoTanqueService;
import com.craftbeerstore.application.service.dto.MovimientoTanqueDTO;
import com.craftbeerstore.application.service.mapper.MovimientoTanqueMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.EstadoUsoTanque;
/**
 * Test class for the MovimientoTanqueResource REST controller.
 *
 * @see MovimientoTanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class MovimientoTanqueResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final EstadoUsoTanque DEFAULT_ESTADO = EstadoUsoTanque.EN_USO;
    private static final EstadoUsoTanque UPDATED_ESTADO = EstadoUsoTanque.VACIO;

    private static final Integer DEFAULT_DIAS = 1;
    private static final Integer UPDATED_DIAS = 2;

    @Autowired
    private MovimientoTanqueRepository movimientoTanqueRepository;

    @Autowired
    private MovimientoTanqueMapper movimientoTanqueMapper;

    @Autowired
    private MovimientoTanqueService movimientoTanqueService;

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

    private MockMvc restMovimientoTanqueMockMvc;

    private MovimientoTanque movimientoTanque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovimientoTanqueResource movimientoTanqueResource = new MovimientoTanqueResource(movimientoTanqueService);
        this.restMovimientoTanqueMockMvc = MockMvcBuilders.standaloneSetup(movimientoTanqueResource)
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
    public static MovimientoTanque createEntity(EntityManager em) {
        MovimientoTanque movimientoTanque = new MovimientoTanque()
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO)
            .dias(DEFAULT_DIAS);
        return movimientoTanque;
    }

    @Before
    public void initTest() {
        movimientoTanque = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovimientoTanque() throws Exception {
        int databaseSizeBeforeCreate = movimientoTanqueRepository.findAll().size();

        // Create the MovimientoTanque
        MovimientoTanqueDTO movimientoTanqueDTO = movimientoTanqueMapper.toDto(movimientoTanque);
        restMovimientoTanqueMockMvc.perform(post("/api/movimiento-tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoTanqueDTO)))
            .andExpect(status().isCreated());

        // Validate the MovimientoTanque in the database
        List<MovimientoTanque> movimientoTanqueList = movimientoTanqueRepository.findAll();
        assertThat(movimientoTanqueList).hasSize(databaseSizeBeforeCreate + 1);
        MovimientoTanque testMovimientoTanque = movimientoTanqueList.get(movimientoTanqueList.size() - 1);
        assertThat(testMovimientoTanque.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testMovimientoTanque.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMovimientoTanque.getDias()).isEqualTo(DEFAULT_DIAS);
    }

    @Test
    @Transactional
    public void createMovimientoTanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movimientoTanqueRepository.findAll().size();

        // Create the MovimientoTanque with an existing ID
        movimientoTanque.setId(1L);
        MovimientoTanqueDTO movimientoTanqueDTO = movimientoTanqueMapper.toDto(movimientoTanque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimientoTanqueMockMvc.perform(post("/api/movimiento-tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoTanqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimientoTanque in the database
        List<MovimientoTanque> movimientoTanqueList = movimientoTanqueRepository.findAll();
        assertThat(movimientoTanqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovimientoTanques() throws Exception {
        // Initialize the database
        movimientoTanqueRepository.saveAndFlush(movimientoTanque);

        // Get all the movimientoTanqueList
        restMovimientoTanqueMockMvc.perform(get("/api/movimiento-tanques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimientoTanque.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].dias").value(hasItem(DEFAULT_DIAS)));
    }
    
    @Test
    @Transactional
    public void getMovimientoTanque() throws Exception {
        // Initialize the database
        movimientoTanqueRepository.saveAndFlush(movimientoTanque);

        // Get the movimientoTanque
        restMovimientoTanqueMockMvc.perform(get("/api/movimiento-tanques/{id}", movimientoTanque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movimientoTanque.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.dias").value(DEFAULT_DIAS));
    }

    @Test
    @Transactional
    public void getNonExistingMovimientoTanque() throws Exception {
        // Get the movimientoTanque
        restMovimientoTanqueMockMvc.perform(get("/api/movimiento-tanques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovimientoTanque() throws Exception {
        // Initialize the database
        movimientoTanqueRepository.saveAndFlush(movimientoTanque);

        int databaseSizeBeforeUpdate = movimientoTanqueRepository.findAll().size();

        // Update the movimientoTanque
        MovimientoTanque updatedMovimientoTanque = movimientoTanqueRepository.findById(movimientoTanque.getId()).get();
        // Disconnect from session so that the updates on updatedMovimientoTanque are not directly saved in db
        em.detach(updatedMovimientoTanque);
        updatedMovimientoTanque
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO)
            .dias(UPDATED_DIAS);
        MovimientoTanqueDTO movimientoTanqueDTO = movimientoTanqueMapper.toDto(updatedMovimientoTanque);

        restMovimientoTanqueMockMvc.perform(put("/api/movimiento-tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoTanqueDTO)))
            .andExpect(status().isOk());

        // Validate the MovimientoTanque in the database
        List<MovimientoTanque> movimientoTanqueList = movimientoTanqueRepository.findAll();
        assertThat(movimientoTanqueList).hasSize(databaseSizeBeforeUpdate);
        MovimientoTanque testMovimientoTanque = movimientoTanqueList.get(movimientoTanqueList.size() - 1);
        assertThat(testMovimientoTanque.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMovimientoTanque.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMovimientoTanque.getDias()).isEqualTo(UPDATED_DIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingMovimientoTanque() throws Exception {
        int databaseSizeBeforeUpdate = movimientoTanqueRepository.findAll().size();

        // Create the MovimientoTanque
        MovimientoTanqueDTO movimientoTanqueDTO = movimientoTanqueMapper.toDto(movimientoTanque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimientoTanqueMockMvc.perform(put("/api/movimiento-tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoTanqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimientoTanque in the database
        List<MovimientoTanque> movimientoTanqueList = movimientoTanqueRepository.findAll();
        assertThat(movimientoTanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovimientoTanque() throws Exception {
        // Initialize the database
        movimientoTanqueRepository.saveAndFlush(movimientoTanque);

        int databaseSizeBeforeDelete = movimientoTanqueRepository.findAll().size();

        // Delete the movimientoTanque
        restMovimientoTanqueMockMvc.perform(delete("/api/movimiento-tanques/{id}", movimientoTanque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovimientoTanque> movimientoTanqueList = movimientoTanqueRepository.findAll();
        assertThat(movimientoTanqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoTanque.class);
        MovimientoTanque movimientoTanque1 = new MovimientoTanque();
        movimientoTanque1.setId(1L);
        MovimientoTanque movimientoTanque2 = new MovimientoTanque();
        movimientoTanque2.setId(movimientoTanque1.getId());
        assertThat(movimientoTanque1).isEqualTo(movimientoTanque2);
        movimientoTanque2.setId(2L);
        assertThat(movimientoTanque1).isNotEqualTo(movimientoTanque2);
        movimientoTanque1.setId(null);
        assertThat(movimientoTanque1).isNotEqualTo(movimientoTanque2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoTanqueDTO.class);
        MovimientoTanqueDTO movimientoTanqueDTO1 = new MovimientoTanqueDTO();
        movimientoTanqueDTO1.setId(1L);
        MovimientoTanqueDTO movimientoTanqueDTO2 = new MovimientoTanqueDTO();
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO2.setId(movimientoTanqueDTO1.getId());
        assertThat(movimientoTanqueDTO1).isEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO2.setId(2L);
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO1.setId(null);
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(movimientoTanqueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(movimientoTanqueMapper.fromId(null)).isNull();
    }
}
