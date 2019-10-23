package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.MedicionLote;
import com.craftbeerstore.application.repository.MedicionLoteRepository;
import com.craftbeerstore.application.service.MedicionLoteService;
import com.craftbeerstore.application.service.dto.MedicionLoteDTO;
import com.craftbeerstore.application.service.mapper.MedicionLoteMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
/**
 * Test class for the MedicionLoteResource REST controller.
 *
 * @see MedicionLoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class MedicionLoteResourceIntTest {

    private static final Integer DEFAULT_DIA = 1;
    private static final Integer UPDATED_DIA = 2;

    private static final TipoMedicion DEFAULT_TIPO_MEDICION = TipoMedicion.DENSIDAD;
    private static final TipoMedicion UPDATED_TIPO_MEDICION = TipoMedicion.TEMPERATURA;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_REALIZADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REALIZADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    @Autowired
    private MedicionLoteRepository medicionLoteRepository;

    @Autowired
    private MedicionLoteMapper medicionLoteMapper;

    @Autowired
    private MedicionLoteService medicionLoteService;

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

    private MockMvc restMedicionLoteMockMvc;

    private MedicionLote medicionLote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicionLoteResource medicionLoteResource = new MedicionLoteResource(medicionLoteService);
        this.restMedicionLoteMockMvc = MockMvcBuilders.standaloneSetup(medicionLoteResource)
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
    public static MedicionLote createEntity(EntityManager em) {
        MedicionLote medicionLote = new MedicionLote()
            .dia(DEFAULT_DIA)
            .tipoMedicion(DEFAULT_TIPO_MEDICION)
            .estado(DEFAULT_ESTADO)
            .fechaRealizado(DEFAULT_FECHA_REALIZADO)
            .valor(DEFAULT_VALOR)
            .observacion(DEFAULT_OBSERVACION);
        return medicionLote;
    }

    @Before
    public void initTest() {
        medicionLote = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicionLote() throws Exception {
        int databaseSizeBeforeCreate = medicionLoteRepository.findAll().size();

        // Create the MedicionLote
        MedicionLoteDTO medicionLoteDTO = medicionLoteMapper.toDto(medicionLote);
        restMedicionLoteMockMvc.perform(post("/api/medicion-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicionLoteDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicionLote in the database
        List<MedicionLote> medicionLoteList = medicionLoteRepository.findAll();
        assertThat(medicionLoteList).hasSize(databaseSizeBeforeCreate + 1);
        MedicionLote testMedicionLote = medicionLoteList.get(medicionLoteList.size() - 1);
        assertThat(testMedicionLote.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testMedicionLote.getTipoMedicion()).isEqualTo(DEFAULT_TIPO_MEDICION);
        assertThat(testMedicionLote.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMedicionLote.getFechaRealizado()).isEqualTo(DEFAULT_FECHA_REALIZADO);
        assertThat(testMedicionLote.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testMedicionLote.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
    }

    @Test
    @Transactional
    public void createMedicionLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicionLoteRepository.findAll().size();

        // Create the MedicionLote with an existing ID
        medicionLote.setId(1L);
        MedicionLoteDTO medicionLoteDTO = medicionLoteMapper.toDto(medicionLote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicionLoteMockMvc.perform(post("/api/medicion-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicionLoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicionLote in the database
        List<MedicionLote> medicionLoteList = medicionLoteRepository.findAll();
        assertThat(medicionLoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicionLotes() throws Exception {
        // Initialize the database
        medicionLoteRepository.saveAndFlush(medicionLote);

        // Get all the medicionLoteList
        restMedicionLoteMockMvc.perform(get("/api/medicion-lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicionLote.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].tipoMedicion").value(hasItem(DEFAULT_TIPO_MEDICION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaRealizado").value(hasItem(DEFAULT_FECHA_REALIZADO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicionLote() throws Exception {
        // Initialize the database
        medicionLoteRepository.saveAndFlush(medicionLote);

        // Get the medicionLote
        restMedicionLoteMockMvc.perform(get("/api/medicion-lotes/{id}", medicionLote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicionLote.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA))
            .andExpect(jsonPath("$.tipoMedicion").value(DEFAULT_TIPO_MEDICION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fechaRealizado").value(DEFAULT_FECHA_REALIZADO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicionLote() throws Exception {
        // Get the medicionLote
        restMedicionLoteMockMvc.perform(get("/api/medicion-lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicionLote() throws Exception {
        // Initialize the database
        medicionLoteRepository.saveAndFlush(medicionLote);

        int databaseSizeBeforeUpdate = medicionLoteRepository.findAll().size();

        // Update the medicionLote
        MedicionLote updatedMedicionLote = medicionLoteRepository.findById(medicionLote.getId()).get();
        // Disconnect from session so that the updates on updatedMedicionLote are not directly saved in db
        em.detach(updatedMedicionLote);
        updatedMedicionLote
            .dia(UPDATED_DIA)
            .tipoMedicion(UPDATED_TIPO_MEDICION)
            .estado(UPDATED_ESTADO)
            .fechaRealizado(UPDATED_FECHA_REALIZADO)
            .valor(UPDATED_VALOR)
            .observacion(UPDATED_OBSERVACION);
        MedicionLoteDTO medicionLoteDTO = medicionLoteMapper.toDto(updatedMedicionLote);

        restMedicionLoteMockMvc.perform(put("/api/medicion-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicionLoteDTO)))
            .andExpect(status().isOk());

        // Validate the MedicionLote in the database
        List<MedicionLote> medicionLoteList = medicionLoteRepository.findAll();
        assertThat(medicionLoteList).hasSize(databaseSizeBeforeUpdate);
        MedicionLote testMedicionLote = medicionLoteList.get(medicionLoteList.size() - 1);
        assertThat(testMedicionLote.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testMedicionLote.getTipoMedicion()).isEqualTo(UPDATED_TIPO_MEDICION);
        assertThat(testMedicionLote.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMedicionLote.getFechaRealizado()).isEqualTo(UPDATED_FECHA_REALIZADO);
        assertThat(testMedicionLote.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testMedicionLote.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicionLote() throws Exception {
        int databaseSizeBeforeUpdate = medicionLoteRepository.findAll().size();

        // Create the MedicionLote
        MedicionLoteDTO medicionLoteDTO = medicionLoteMapper.toDto(medicionLote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicionLoteMockMvc.perform(put("/api/medicion-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicionLoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicionLote in the database
        List<MedicionLote> medicionLoteList = medicionLoteRepository.findAll();
        assertThat(medicionLoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicionLote() throws Exception {
        // Initialize the database
        medicionLoteRepository.saveAndFlush(medicionLote);

        int databaseSizeBeforeDelete = medicionLoteRepository.findAll().size();

        // Delete the medicionLote
        restMedicionLoteMockMvc.perform(delete("/api/medicion-lotes/{id}", medicionLote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicionLote> medicionLoteList = medicionLoteRepository.findAll();
        assertThat(medicionLoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicionLote.class);
        MedicionLote medicionLote1 = new MedicionLote();
        medicionLote1.setId(1L);
        MedicionLote medicionLote2 = new MedicionLote();
        medicionLote2.setId(medicionLote1.getId());
        assertThat(medicionLote1).isEqualTo(medicionLote2);
        medicionLote2.setId(2L);
        assertThat(medicionLote1).isNotEqualTo(medicionLote2);
        medicionLote1.setId(null);
        assertThat(medicionLote1).isNotEqualTo(medicionLote2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicionLoteDTO.class);
        MedicionLoteDTO medicionLoteDTO1 = new MedicionLoteDTO();
        medicionLoteDTO1.setId(1L);
        MedicionLoteDTO medicionLoteDTO2 = new MedicionLoteDTO();
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
        medicionLoteDTO2.setId(medicionLoteDTO1.getId());
        assertThat(medicionLoteDTO1).isEqualTo(medicionLoteDTO2);
        medicionLoteDTO2.setId(2L);
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
        medicionLoteDTO1.setId(null);
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicionLoteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicionLoteMapper.fromId(null)).isNull();
    }
}
