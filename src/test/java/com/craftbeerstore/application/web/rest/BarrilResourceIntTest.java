package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Barril;
import com.craftbeerstore.application.repository.BarrilRepository;
import com.craftbeerstore.application.service.BarrilService;
import com.craftbeerstore.application.service.dto.BarrilDTO;
import com.craftbeerstore.application.service.mapper.BarrilMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.LitrosBarril;
import com.craftbeerstore.application.domain.enumeration.ConectorBarril;
import com.craftbeerstore.application.domain.enumeration.EstadoBarril;
/**
 * Test class for the BarrilResource REST controller.
 *
 * @see BarrilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class BarrilResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final LitrosBarril DEFAULT_LITROS = LitrosBarril.CINCO;
    private static final LitrosBarril UPDATED_LITROS = LitrosBarril.DIEZ;

    private static final ConectorBarril DEFAULT_CONECTOR = ConectorBarril.G;
    private static final ConectorBarril UPDATED_CONECTOR = ConectorBarril.A;

    private static final EstadoBarril DEFAULT_ESTADO = EstadoBarril.VACIO;
    private static final EstadoBarril UPDATED_ESTADO = EstadoBarril.LLENO;

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private BarrilRepository barrilRepository;

    @Autowired
    private BarrilMapper barrilMapper;

    @Autowired
    private BarrilService barrilService;

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

    private MockMvc restBarrilMockMvc;

    private Barril barril;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BarrilResource barrilResource = new BarrilResource(barrilService);
        this.restBarrilMockMvc = MockMvcBuilders.standaloneSetup(barrilResource)
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
    public static Barril createEntity(EntityManager em) {
        Barril barril = new Barril()
            .codigo(DEFAULT_CODIGO)
            .litros(DEFAULT_LITROS)
            .conector(DEFAULT_CONECTOR)
            .estado(DEFAULT_ESTADO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return barril;
    }

    @Before
    public void initTest() {
        barril = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarril() throws Exception {
        int databaseSizeBeforeCreate = barrilRepository.findAll().size();

        // Create the Barril
        BarrilDTO barrilDTO = barrilMapper.toDto(barril);
        restBarrilMockMvc.perform(post("/api/barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barrilDTO)))
            .andExpect(status().isCreated());

        // Validate the Barril in the database
        List<Barril> barrilList = barrilRepository.findAll();
        assertThat(barrilList).hasSize(databaseSizeBeforeCreate + 1);
        Barril testBarril = barrilList.get(barrilList.size() - 1);
        assertThat(testBarril.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testBarril.getLitros()).isEqualTo(DEFAULT_LITROS);
        assertThat(testBarril.getConector()).isEqualTo(DEFAULT_CONECTOR);
        assertThat(testBarril.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testBarril.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testBarril.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBarrilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barrilRepository.findAll().size();

        // Create the Barril with an existing ID
        barril.setId(1L);
        BarrilDTO barrilDTO = barrilMapper.toDto(barril);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarrilMockMvc.perform(post("/api/barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barrilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barril in the database
        List<Barril> barrilList = barrilRepository.findAll();
        assertThat(barrilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBarrils() throws Exception {
        // Initialize the database
        barrilRepository.saveAndFlush(barril);

        // Get all the barrilList
        restBarrilMockMvc.perform(get("/api/barrils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barril.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].litros").value(hasItem(DEFAULT_LITROS.toString())))
            .andExpect(jsonPath("$.[*].conector").value(hasItem(DEFAULT_CONECTOR.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getBarril() throws Exception {
        // Initialize the database
        barrilRepository.saveAndFlush(barril);

        // Get the barril
        restBarrilMockMvc.perform(get("/api/barrils/{id}", barril.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(barril.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.litros").value(DEFAULT_LITROS.toString()))
            .andExpect(jsonPath("$.conector").value(DEFAULT_CONECTOR.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingBarril() throws Exception {
        // Get the barril
        restBarrilMockMvc.perform(get("/api/barrils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarril() throws Exception {
        // Initialize the database
        barrilRepository.saveAndFlush(barril);

        int databaseSizeBeforeUpdate = barrilRepository.findAll().size();

        // Update the barril
        Barril updatedBarril = barrilRepository.findById(barril.getId()).get();
        // Disconnect from session so that the updates on updatedBarril are not directly saved in db
        em.detach(updatedBarril);
        updatedBarril
            .codigo(UPDATED_CODIGO)
            .litros(UPDATED_LITROS)
            .conector(UPDATED_CONECTOR)
            .estado(UPDATED_ESTADO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        BarrilDTO barrilDTO = barrilMapper.toDto(updatedBarril);

        restBarrilMockMvc.perform(put("/api/barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barrilDTO)))
            .andExpect(status().isOk());

        // Validate the Barril in the database
        List<Barril> barrilList = barrilRepository.findAll();
        assertThat(barrilList).hasSize(databaseSizeBeforeUpdate);
        Barril testBarril = barrilList.get(barrilList.size() - 1);
        assertThat(testBarril.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testBarril.getLitros()).isEqualTo(UPDATED_LITROS);
        assertThat(testBarril.getConector()).isEqualTo(UPDATED_CONECTOR);
        assertThat(testBarril.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testBarril.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testBarril.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBarril() throws Exception {
        int databaseSizeBeforeUpdate = barrilRepository.findAll().size();

        // Create the Barril
        BarrilDTO barrilDTO = barrilMapper.toDto(barril);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarrilMockMvc.perform(put("/api/barrils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barrilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barril in the database
        List<Barril> barrilList = barrilRepository.findAll();
        assertThat(barrilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBarril() throws Exception {
        // Initialize the database
        barrilRepository.saveAndFlush(barril);

        int databaseSizeBeforeDelete = barrilRepository.findAll().size();

        // Delete the barril
        restBarrilMockMvc.perform(delete("/api/barrils/{id}", barril.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Barril> barrilList = barrilRepository.findAll();
        assertThat(barrilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barril.class);
        Barril barril1 = new Barril();
        barril1.setId(1L);
        Barril barril2 = new Barril();
        barril2.setId(barril1.getId());
        assertThat(barril1).isEqualTo(barril2);
        barril2.setId(2L);
        assertThat(barril1).isNotEqualTo(barril2);
        barril1.setId(null);
        assertThat(barril1).isNotEqualTo(barril2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarrilDTO.class);
        BarrilDTO barrilDTO1 = new BarrilDTO();
        barrilDTO1.setId(1L);
        BarrilDTO barrilDTO2 = new BarrilDTO();
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
        barrilDTO2.setId(barrilDTO1.getId());
        assertThat(barrilDTO1).isEqualTo(barrilDTO2);
        barrilDTO2.setId(2L);
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
        barrilDTO1.setId(null);
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(barrilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(barrilMapper.fromId(null)).isNull();
    }
}
