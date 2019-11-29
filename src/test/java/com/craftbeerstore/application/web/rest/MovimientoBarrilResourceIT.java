package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.MovimientoBarril;
import com.craftbeerstore.application.repository.MovimientoBarrilRepository;
import com.craftbeerstore.application.service.MovimientoBarrilService;
import com.craftbeerstore.application.service.dto.MovimientoBarrilDTO;
import com.craftbeerstore.application.service.mapper.MovimientoBarrilMapper;
import com.craftbeerstore.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

import com.craftbeerstore.application.domain.enumeration.EstadoMovimientoBarril;
/**
 * Integration tests for the {@link MovimientoBarrilResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class MovimientoBarrilResourceIT {

    private static final LocalDate DEFAULT_FECHA_MOVIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MOVIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final EstadoMovimientoBarril DEFAULT_ESTADO = EstadoMovimientoBarril.VACIO;
    private static final EstadoMovimientoBarril UPDATED_ESTADO = EstadoMovimientoBarril.LLENO;

    private static final Long DEFAULT_DIAS = 1L;
    private static final Long UPDATED_DIAS = 2L;

    @Autowired
    private MovimientoBarrilRepository movimientoBarrilRepository;

    @Autowired
    private MovimientoBarrilMapper movimientoBarrilMapper;

    @Autowired
    private MovimientoBarrilService movimientoBarrilService;

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

    private MockMvc restMovimientoBarrilMockMvc;

    private MovimientoBarril movimientoBarril;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovimientoBarrilResource movimientoBarrilResource = new MovimientoBarrilResource(movimientoBarrilService);
        this.restMovimientoBarrilMockMvc = MockMvcBuilders.standaloneSetup(movimientoBarrilResource)
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
    public static MovimientoBarril createEntity(EntityManager em) {
        MovimientoBarril movimientoBarril = new MovimientoBarril()
            .fechaMovimiento(DEFAULT_FECHA_MOVIMIENTO)
            .estado(DEFAULT_ESTADO)
            .dias(DEFAULT_DIAS);
        return movimientoBarril;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MovimientoBarril createUpdatedEntity(EntityManager em) {
        MovimientoBarril movimientoBarril = new MovimientoBarril()
            .fechaMovimiento(UPDATED_FECHA_MOVIMIENTO)
            .estado(UPDATED_ESTADO)
            .dias(UPDATED_DIAS);
        return movimientoBarril;
    }

    @BeforeEach
    public void initTest() {
        movimientoBarril = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovimientoBarril() throws Exception {
        int databaseSizeBeforeCreate = movimientoBarrilRepository.findAll().size();

        // Create the MovimientoBarril
        MovimientoBarrilDTO movimientoBarrilDTO = movimientoBarrilMapper.toDto(movimientoBarril);
        restMovimientoBarrilMockMvc.perform(post("/api/movimiento-barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoBarrilDTO)))
            .andExpect(status().isCreated());

        // Validate the MovimientoBarril in the database
        List<MovimientoBarril> movimientoBarrilList = movimientoBarrilRepository.findAll();
        assertThat(movimientoBarrilList).hasSize(databaseSizeBeforeCreate + 1);
        MovimientoBarril testMovimientoBarril = movimientoBarrilList.get(movimientoBarrilList.size() - 1);
        assertThat(testMovimientoBarril.getFechaMovimiento()).isEqualTo(DEFAULT_FECHA_MOVIMIENTO);
        assertThat(testMovimientoBarril.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMovimientoBarril.getDias()).isEqualTo(DEFAULT_DIAS);
    }

    @Test
    @Transactional
    public void createMovimientoBarrilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movimientoBarrilRepository.findAll().size();

        // Create the MovimientoBarril with an existing ID
        movimientoBarril.setId(1L);
        MovimientoBarrilDTO movimientoBarrilDTO = movimientoBarrilMapper.toDto(movimientoBarril);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimientoBarrilMockMvc.perform(post("/api/movimiento-barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoBarrilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimientoBarril in the database
        List<MovimientoBarril> movimientoBarrilList = movimientoBarrilRepository.findAll();
        assertThat(movimientoBarrilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMovimientoBarrils() throws Exception {
        // Initialize the database
        movimientoBarrilRepository.saveAndFlush(movimientoBarril);

        // Get all the movimientoBarrilList
        restMovimientoBarrilMockMvc.perform(get("/api/movimiento-barrils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimientoBarril.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaMovimiento").value(hasItem(DEFAULT_FECHA_MOVIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].dias").value(hasItem(DEFAULT_DIAS.intValue())));
    }
    
    @Test
    @Transactional
    public void getMovimientoBarril() throws Exception {
        // Initialize the database
        movimientoBarrilRepository.saveAndFlush(movimientoBarril);

        // Get the movimientoBarril
        restMovimientoBarrilMockMvc.perform(get("/api/movimiento-barrils/{id}", movimientoBarril.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movimientoBarril.getId().intValue()))
            .andExpect(jsonPath("$.fechaMovimiento").value(DEFAULT_FECHA_MOVIMIENTO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.dias").value(DEFAULT_DIAS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMovimientoBarril() throws Exception {
        // Get the movimientoBarril
        restMovimientoBarrilMockMvc.perform(get("/api/movimiento-barrils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovimientoBarril() throws Exception {
        // Initialize the database
        movimientoBarrilRepository.saveAndFlush(movimientoBarril);

        int databaseSizeBeforeUpdate = movimientoBarrilRepository.findAll().size();

        // Update the movimientoBarril
        MovimientoBarril updatedMovimientoBarril = movimientoBarrilRepository.findById(movimientoBarril.getId()).get();
        // Disconnect from session so that the updates on updatedMovimientoBarril are not directly saved in db
        em.detach(updatedMovimientoBarril);
        updatedMovimientoBarril
            .fechaMovimiento(UPDATED_FECHA_MOVIMIENTO)
            .estado(UPDATED_ESTADO)
            .dias(UPDATED_DIAS);
        MovimientoBarrilDTO movimientoBarrilDTO = movimientoBarrilMapper.toDto(updatedMovimientoBarril);

        restMovimientoBarrilMockMvc.perform(put("/api/movimiento-barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoBarrilDTO)))
            .andExpect(status().isOk());

        // Validate the MovimientoBarril in the database
        List<MovimientoBarril> movimientoBarrilList = movimientoBarrilRepository.findAll();
        assertThat(movimientoBarrilList).hasSize(databaseSizeBeforeUpdate);
        MovimientoBarril testMovimientoBarril = movimientoBarrilList.get(movimientoBarrilList.size() - 1);
        assertThat(testMovimientoBarril.getFechaMovimiento()).isEqualTo(UPDATED_FECHA_MOVIMIENTO);
        assertThat(testMovimientoBarril.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMovimientoBarril.getDias()).isEqualTo(UPDATED_DIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingMovimientoBarril() throws Exception {
        int databaseSizeBeforeUpdate = movimientoBarrilRepository.findAll().size();

        // Create the MovimientoBarril
        MovimientoBarrilDTO movimientoBarrilDTO = movimientoBarrilMapper.toDto(movimientoBarril);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimientoBarrilMockMvc.perform(put("/api/movimiento-barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movimientoBarrilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MovimientoBarril in the database
        List<MovimientoBarril> movimientoBarrilList = movimientoBarrilRepository.findAll();
        assertThat(movimientoBarrilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovimientoBarril() throws Exception {
        // Initialize the database
        movimientoBarrilRepository.saveAndFlush(movimientoBarril);

        int databaseSizeBeforeDelete = movimientoBarrilRepository.findAll().size();

        // Delete the movimientoBarril
        restMovimientoBarrilMockMvc.perform(delete("/api/movimiento-barrils/{id}", movimientoBarril.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MovimientoBarril> movimientoBarrilList = movimientoBarrilRepository.findAll();
        assertThat(movimientoBarrilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
