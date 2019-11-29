package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.EtapaLote;
import com.craftbeerstore.application.repository.EtapaLoteRepository;
import com.craftbeerstore.application.service.EtapaLoteService;
import com.craftbeerstore.application.service.dto.EtapaLoteDTO;
import com.craftbeerstore.application.service.mapper.EtapaLoteMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.EtapaLoteEnum;
/**
 * Integration tests for the {@link EtapaLoteResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EtapaLoteResourceIT {

    private static final EtapaLoteEnum DEFAULT_ETAPA = EtapaLoteEnum.COCCION;
    private static final EtapaLoteEnum UPDATED_ETAPA = EtapaLoteEnum.FERMENTACION;

    private static final BigDecimal DEFAULT_LITROS = new BigDecimal(1);
    private static final BigDecimal UPDATED_LITROS = new BigDecimal(2);

    private static final LocalDate DEFAULT_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DIAS = 1;
    private static final Integer UPDATED_DIAS = 2;

    @Autowired
    private EtapaLoteRepository etapaLoteRepository;

    @Autowired
    private EtapaLoteMapper etapaLoteMapper;

    @Autowired
    private EtapaLoteService etapaLoteService;

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

    private MockMvc restEtapaLoteMockMvc;

    private EtapaLote etapaLote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapaLoteResource etapaLoteResource = new EtapaLoteResource(etapaLoteService);
        this.restEtapaLoteMockMvc = MockMvcBuilders.standaloneSetup(etapaLoteResource)
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
    public static EtapaLote createEntity(EntityManager em) {
        EtapaLote etapaLote = new EtapaLote()
            .etapa(DEFAULT_ETAPA)
            .litros(DEFAULT_LITROS)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .dias(DEFAULT_DIAS);
        return etapaLote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaLote createUpdatedEntity(EntityManager em) {
        EtapaLote etapaLote = new EtapaLote()
            .etapa(UPDATED_ETAPA)
            .litros(UPDATED_LITROS)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .dias(UPDATED_DIAS);
        return etapaLote;
    }

    @BeforeEach
    public void initTest() {
        etapaLote = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapaLote() throws Exception {
        int databaseSizeBeforeCreate = etapaLoteRepository.findAll().size();

        // Create the EtapaLote
        EtapaLoteDTO etapaLoteDTO = etapaLoteMapper.toDto(etapaLote);
        restEtapaLoteMockMvc.perform(post("/api/etapa-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaLoteDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapaLote in the database
        List<EtapaLote> etapaLoteList = etapaLoteRepository.findAll();
        assertThat(etapaLoteList).hasSize(databaseSizeBeforeCreate + 1);
        EtapaLote testEtapaLote = etapaLoteList.get(etapaLoteList.size() - 1);
        assertThat(testEtapaLote.getEtapa()).isEqualTo(DEFAULT_ETAPA);
        assertThat(testEtapaLote.getLitros()).isEqualTo(DEFAULT_LITROS);
        assertThat(testEtapaLote.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testEtapaLote.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testEtapaLote.getDias()).isEqualTo(DEFAULT_DIAS);
    }

    @Test
    @Transactional
    public void createEtapaLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapaLoteRepository.findAll().size();

        // Create the EtapaLote with an existing ID
        etapaLote.setId(1L);
        EtapaLoteDTO etapaLoteDTO = etapaLoteMapper.toDto(etapaLote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaLoteMockMvc.perform(post("/api/etapa-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaLoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaLote in the database
        List<EtapaLote> etapaLoteList = etapaLoteRepository.findAll();
        assertThat(etapaLoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtapaLotes() throws Exception {
        // Initialize the database
        etapaLoteRepository.saveAndFlush(etapaLote);

        // Get all the etapaLoteList
        restEtapaLoteMockMvc.perform(get("/api/etapa-lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapaLote.getId().intValue())))
            .andExpect(jsonPath("$.[*].etapa").value(hasItem(DEFAULT_ETAPA.toString())))
            .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS.intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].dias").value(hasItem(DEFAULT_DIAS)));
    }
    
    @Test
    @Transactional
    public void getEtapaLote() throws Exception {
        // Initialize the database
        etapaLoteRepository.saveAndFlush(etapaLote);

        // Get the etapaLote
        restEtapaLoteMockMvc.perform(get("/api/etapa-lotes/{id}", etapaLote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapaLote.getId().intValue()))
            .andExpect(jsonPath("$.etapa").value(DEFAULT_ETAPA.toString()))
            .andExpect(jsonPath("$.litros").value(DEFAULT_LITROS.intValue()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.dias").value(DEFAULT_DIAS));
    }

    @Test
    @Transactional
    public void getNonExistingEtapaLote() throws Exception {
        // Get the etapaLote
        restEtapaLoteMockMvc.perform(get("/api/etapa-lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapaLote() throws Exception {
        // Initialize the database
        etapaLoteRepository.saveAndFlush(etapaLote);

        int databaseSizeBeforeUpdate = etapaLoteRepository.findAll().size();

        // Update the etapaLote
        EtapaLote updatedEtapaLote = etapaLoteRepository.findById(etapaLote.getId()).get();
        // Disconnect from session so that the updates on updatedEtapaLote are not directly saved in db
        em.detach(updatedEtapaLote);
        updatedEtapaLote
            .etapa(UPDATED_ETAPA)
            .litros(UPDATED_LITROS)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .dias(UPDATED_DIAS);
        EtapaLoteDTO etapaLoteDTO = etapaLoteMapper.toDto(updatedEtapaLote);

        restEtapaLoteMockMvc.perform(put("/api/etapa-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaLoteDTO)))
            .andExpect(status().isOk());

        // Validate the EtapaLote in the database
        List<EtapaLote> etapaLoteList = etapaLoteRepository.findAll();
        assertThat(etapaLoteList).hasSize(databaseSizeBeforeUpdate);
        EtapaLote testEtapaLote = etapaLoteList.get(etapaLoteList.size() - 1);
        assertThat(testEtapaLote.getEtapa()).isEqualTo(UPDATED_ETAPA);
        assertThat(testEtapaLote.getLitros()).isEqualTo(UPDATED_LITROS);
        assertThat(testEtapaLote.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testEtapaLote.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testEtapaLote.getDias()).isEqualTo(UPDATED_DIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapaLote() throws Exception {
        int databaseSizeBeforeUpdate = etapaLoteRepository.findAll().size();

        // Create the EtapaLote
        EtapaLoteDTO etapaLoteDTO = etapaLoteMapper.toDto(etapaLote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapaLoteMockMvc.perform(put("/api/etapa-lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapaLoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaLote in the database
        List<EtapaLote> etapaLoteList = etapaLoteRepository.findAll();
        assertThat(etapaLoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtapaLote() throws Exception {
        // Initialize the database
        etapaLoteRepository.saveAndFlush(etapaLote);

        int databaseSizeBeforeDelete = etapaLoteRepository.findAll().size();

        // Delete the etapaLote
        restEtapaLoteMockMvc.perform(delete("/api/etapa-lotes/{id}", etapaLote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtapaLote> etapaLoteList = etapaLoteRepository.findAll();
        assertThat(etapaLoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
