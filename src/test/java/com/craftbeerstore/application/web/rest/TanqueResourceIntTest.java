package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Tanque;
import com.craftbeerstore.application.repository.TanqueRepository;
import com.craftbeerstore.application.service.TanqueService;
import com.craftbeerstore.application.service.dto.TanqueDTO;
import com.craftbeerstore.application.service.mapper.TanqueMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoTanque;
import com.craftbeerstore.application.domain.enumeration.EstadoTanque;
/**
 * Test class for the TanqueResource REST controller.
 *
 * @see TanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class TanqueResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LITROS = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS = new BigDecimal(2);

    private static final TipoTanque DEFAULT_TIPO = TipoTanque.UNITANK_INOX;
    private static final TipoTanque UPDATED_TIPO = TipoTanque.FERMENTADOR;

    private static final EstadoTanque DEFAULT_ESTADO = EstadoTanque.VACIO;
    private static final EstadoTanque UPDATED_ESTADO = EstadoTanque.EN_USO;

    private static final BigDecimal DEFAULT_LISTROS_DISPONIBLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LISTROS_DISPONIBLE = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TanqueRepository tanqueRepository;

    @Autowired
    private TanqueMapper tanqueMapper;

    @Autowired
    private TanqueService tanqueService;

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

    private MockMvc restTanqueMockMvc;

    private Tanque tanque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TanqueResource tanqueResource = new TanqueResource(tanqueService);
        this.restTanqueMockMvc = MockMvcBuilders.standaloneSetup(tanqueResource)
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
    public static Tanque createEntity(EntityManager em) {
        Tanque tanque = new Tanque()
            .nombre(DEFAULT_NOMBRE)
            .litros(DEFAULT_LITROS)
            .tipo(DEFAULT_TIPO)
            .estado(DEFAULT_ESTADO)
            .listrosDisponible(DEFAULT_LISTROS_DISPONIBLE)
            .fechaIngreso(DEFAULT_FECHA_INGRESO);
        return tanque;
    }

    @Before
    public void initTest() {
        tanque = createEntity(em);
    }

    @Test
    @Transactional
    public void createTanque() throws Exception {
        int databaseSizeBeforeCreate = tanqueRepository.findAll().size();

        // Create the Tanque
        TanqueDTO tanqueDTO = tanqueMapper.toDto(tanque);
        restTanqueMockMvc.perform(post("/api/tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tanqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Tanque in the database
        List<Tanque> tanqueList = tanqueRepository.findAll();
        assertThat(tanqueList).hasSize(databaseSizeBeforeCreate + 1);
        Tanque testTanque = tanqueList.get(tanqueList.size() - 1);
        assertThat(testTanque.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTanque.getLitros()).isEqualTo(DEFAULT_LITROS);
        assertThat(testTanque.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testTanque.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTanque.getListrosDisponible()).isEqualTo(DEFAULT_LISTROS_DISPONIBLE);
        assertThat(testTanque.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
    }

    @Test
    @Transactional
    public void createTanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tanqueRepository.findAll().size();

        // Create the Tanque with an existing ID
        tanque.setId(1L);
        TanqueDTO tanqueDTO = tanqueMapper.toDto(tanque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTanqueMockMvc.perform(post("/api/tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tanqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tanque in the database
        List<Tanque> tanqueList = tanqueRepository.findAll();
        assertThat(tanqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTanques() throws Exception {
        // Initialize the database
        tanqueRepository.saveAndFlush(tanque);

        // Get all the tanqueList
        restTanqueMockMvc.perform(get("/api/tanques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tanque.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS.intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].listrosDisponible").value(hasItem(DEFAULT_LISTROS_DISPONIBLE.intValue())))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())));
    }
    
    @Test
    @Transactional
    public void getTanque() throws Exception {
        // Initialize the database
        tanqueRepository.saveAndFlush(tanque);

        // Get the tanque
        restTanqueMockMvc.perform(get("/api/tanques/{id}", tanque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tanque.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.litros").value(DEFAULT_LITROS.intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.listrosDisponible").value(DEFAULT_LISTROS_DISPONIBLE.intValue()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTanque() throws Exception {
        // Get the tanque
        restTanqueMockMvc.perform(get("/api/tanques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTanque() throws Exception {
        // Initialize the database
        tanqueRepository.saveAndFlush(tanque);

        int databaseSizeBeforeUpdate = tanqueRepository.findAll().size();

        // Update the tanque
        Tanque updatedTanque = tanqueRepository.findById(tanque.getId()).get();
        // Disconnect from session so that the updates on updatedTanque are not directly saved in db
        em.detach(updatedTanque);
        updatedTanque
            .nombre(UPDATED_NOMBRE)
            .litros(UPDATED_LITROS)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .listrosDisponible(UPDATED_LISTROS_DISPONIBLE)
            .fechaIngreso(UPDATED_FECHA_INGRESO);
        TanqueDTO tanqueDTO = tanqueMapper.toDto(updatedTanque);

        restTanqueMockMvc.perform(put("/api/tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tanqueDTO)))
            .andExpect(status().isOk());

        // Validate the Tanque in the database
        List<Tanque> tanqueList = tanqueRepository.findAll();
        assertThat(tanqueList).hasSize(databaseSizeBeforeUpdate);
        Tanque testTanque = tanqueList.get(tanqueList.size() - 1);
        assertThat(testTanque.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTanque.getLitros()).isEqualTo(UPDATED_LITROS);
        assertThat(testTanque.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testTanque.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTanque.getListrosDisponible()).isEqualTo(UPDATED_LISTROS_DISPONIBLE);
        assertThat(testTanque.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
    }

    @Test
    @Transactional
    public void updateNonExistingTanque() throws Exception {
        int databaseSizeBeforeUpdate = tanqueRepository.findAll().size();

        // Create the Tanque
        TanqueDTO tanqueDTO = tanqueMapper.toDto(tanque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTanqueMockMvc.perform(put("/api/tanques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tanqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tanque in the database
        List<Tanque> tanqueList = tanqueRepository.findAll();
        assertThat(tanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTanque() throws Exception {
        // Initialize the database
        tanqueRepository.saveAndFlush(tanque);

        int databaseSizeBeforeDelete = tanqueRepository.findAll().size();

        // Delete the tanque
        restTanqueMockMvc.perform(delete("/api/tanques/{id}", tanque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tanque> tanqueList = tanqueRepository.findAll();
        assertThat(tanqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tanque.class);
        Tanque tanque1 = new Tanque();
        tanque1.setId(1L);
        Tanque tanque2 = new Tanque();
        tanque2.setId(tanque1.getId());
        assertThat(tanque1).isEqualTo(tanque2);
        tanque2.setId(2L);
        assertThat(tanque1).isNotEqualTo(tanque2);
        tanque1.setId(null);
        assertThat(tanque1).isNotEqualTo(tanque2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TanqueDTO.class);
        TanqueDTO tanqueDTO1 = new TanqueDTO();
        tanqueDTO1.setId(1L);
        TanqueDTO tanqueDTO2 = new TanqueDTO();
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
        tanqueDTO2.setId(tanqueDTO1.getId());
        assertThat(tanqueDTO1).isEqualTo(tanqueDTO2);
        tanqueDTO2.setId(2L);
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
        tanqueDTO1.setId(null);
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tanqueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tanqueMapper.fromId(null)).isNull();
    }
}
