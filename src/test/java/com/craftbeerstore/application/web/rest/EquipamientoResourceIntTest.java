package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Equipamiento;
import com.craftbeerstore.application.repository.EquipamientoRepository;
import com.craftbeerstore.application.service.EquipamientoService;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;
import com.craftbeerstore.application.service.mapper.EquipamientoMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoEquipamiento;
/**
 * Test class for the EquipamientoResource REST controller.
 *
 * @see EquipamientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EquipamientoResourceIntTest {

    private static final String DEFAULT_NOMBRE_EQUIPAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EQUIPAMIENTO = "BBBBBBBBBB";

    private static final TipoEquipamiento DEFAULT_TIPO_EQUIPAMIENTO = TipoEquipamiento.MEDICION;
    private static final TipoEquipamiento UPDATED_TIPO_EQUIPAMIENTO = TipoEquipamiento.MOLIENDA;

    private static final BigDecimal DEFAULT_PRECIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRECIO = new BigDecimal(1);

    private static final BigDecimal DEFAULT_COSTO_ENVIO = new BigDecimal(0);
    private static final BigDecimal UPDATED_COSTO_ENVIO = new BigDecimal(1);

    private static final LocalDate DEFAULT_FECHA_COMPRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMPRA = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private EquipamientoRepository equipamientoRepository;

    @Autowired
    private EquipamientoMapper equipamientoMapper;

    @Autowired
    private EquipamientoService equipamientoService;

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

    private MockMvc restEquipamientoMockMvc;

    private Equipamiento equipamiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipamientoResource equipamientoResource = new EquipamientoResource(equipamientoService);
        this.restEquipamientoMockMvc = MockMvcBuilders.standaloneSetup(equipamientoResource)
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
    public static Equipamiento createEntity(EntityManager em) {
        Equipamiento equipamiento = new Equipamiento()
            .nombreEquipamiento(DEFAULT_NOMBRE_EQUIPAMIENTO)
            .tipoEquipamiento(DEFAULT_TIPO_EQUIPAMIENTO)
            .precio(DEFAULT_PRECIO)
            .costoEnvio(DEFAULT_COSTO_ENVIO)
            .fechaCompra(DEFAULT_FECHA_COMPRA)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return equipamiento;
    }

    @Before
    public void initTest() {
        equipamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipamiento() throws Exception {
        int databaseSizeBeforeCreate = equipamientoRepository.findAll().size();

        // Create the Equipamiento
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);
        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipamiento in the database
        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Equipamiento testEquipamiento = equipamientoList.get(equipamientoList.size() - 1);
        assertThat(testEquipamiento.getNombreEquipamiento()).isEqualTo(DEFAULT_NOMBRE_EQUIPAMIENTO);
        assertThat(testEquipamiento.getTipoEquipamiento()).isEqualTo(DEFAULT_TIPO_EQUIPAMIENTO);
        assertThat(testEquipamiento.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testEquipamiento.getCostoEnvio()).isEqualTo(DEFAULT_COSTO_ENVIO);
        assertThat(testEquipamiento.getFechaCompra()).isEqualTo(DEFAULT_FECHA_COMPRA);
        assertThat(testEquipamiento.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testEquipamiento.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEquipamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipamientoRepository.findAll().size();

        // Create the Equipamiento with an existing ID
        equipamiento.setId(1L);
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipamiento in the database
        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreEquipamientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipamientoRepository.findAll().size();
        // set the field null
        equipamiento.setNombreEquipamiento(null);

        // Create the Equipamiento, which fails.
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoEquipamientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipamientoRepository.findAll().size();
        // set the field null
        equipamiento.setTipoEquipamiento(null);

        // Create the Equipamiento, which fails.
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipamientoRepository.findAll().size();
        // set the field null
        equipamiento.setPrecio(null);

        // Create the Equipamiento, which fails.
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostoEnvioIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipamientoRepository.findAll().size();
        // set the field null
        equipamiento.setCostoEnvio(null);

        // Create the Equipamiento, which fails.
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaCompraIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipamientoRepository.findAll().size();
        // set the field null
        equipamiento.setFechaCompra(null);

        // Create the Equipamiento, which fails.
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        restEquipamientoMockMvc.perform(post("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipamientos() throws Exception {
        // Initialize the database
        equipamientoRepository.saveAndFlush(equipamiento);

        // Get all the equipamientoList
        restEquipamientoMockMvc.perform(get("/api/equipamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEquipamiento").value(hasItem(DEFAULT_NOMBRE_EQUIPAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEquipamiento").value(hasItem(DEFAULT_TIPO_EQUIPAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].costoEnvio").value(hasItem(DEFAULT_COSTO_ENVIO.intValue())))
            .andExpect(jsonPath("$.[*].fechaCompra").value(hasItem(DEFAULT_FECHA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getEquipamiento() throws Exception {
        // Initialize the database
        equipamientoRepository.saveAndFlush(equipamiento);

        // Get the equipamiento
        restEquipamientoMockMvc.perform(get("/api/equipamientos/{id}", equipamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipamiento.getId().intValue()))
            .andExpect(jsonPath("$.nombreEquipamiento").value(DEFAULT_NOMBRE_EQUIPAMIENTO.toString()))
            .andExpect(jsonPath("$.tipoEquipamiento").value(DEFAULT_TIPO_EQUIPAMIENTO.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()))
            .andExpect(jsonPath("$.costoEnvio").value(DEFAULT_COSTO_ENVIO.intValue()))
            .andExpect(jsonPath("$.fechaCompra").value(DEFAULT_FECHA_COMPRA.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingEquipamiento() throws Exception {
        // Get the equipamiento
        restEquipamientoMockMvc.perform(get("/api/equipamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipamiento() throws Exception {
        // Initialize the database
        equipamientoRepository.saveAndFlush(equipamiento);

        int databaseSizeBeforeUpdate = equipamientoRepository.findAll().size();

        // Update the equipamiento
        Equipamiento updatedEquipamiento = equipamientoRepository.findById(equipamiento.getId()).get();
        // Disconnect from session so that the updates on updatedEquipamiento are not directly saved in db
        em.detach(updatedEquipamiento);
        updatedEquipamiento
            .nombreEquipamiento(UPDATED_NOMBRE_EQUIPAMIENTO)
            .tipoEquipamiento(UPDATED_TIPO_EQUIPAMIENTO)
            .precio(UPDATED_PRECIO)
            .costoEnvio(UPDATED_COSTO_ENVIO)
            .fechaCompra(UPDATED_FECHA_COMPRA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(updatedEquipamiento);

        restEquipamientoMockMvc.perform(put("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isOk());

        // Validate the Equipamiento in the database
        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeUpdate);
        Equipamiento testEquipamiento = equipamientoList.get(equipamientoList.size() - 1);
        assertThat(testEquipamiento.getNombreEquipamiento()).isEqualTo(UPDATED_NOMBRE_EQUIPAMIENTO);
        assertThat(testEquipamiento.getTipoEquipamiento()).isEqualTo(UPDATED_TIPO_EQUIPAMIENTO);
        assertThat(testEquipamiento.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testEquipamiento.getCostoEnvio()).isEqualTo(UPDATED_COSTO_ENVIO);
        assertThat(testEquipamiento.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
        assertThat(testEquipamiento.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testEquipamiento.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipamiento() throws Exception {
        int databaseSizeBeforeUpdate = equipamientoRepository.findAll().size();

        // Create the Equipamiento
        EquipamientoDTO equipamientoDTO = equipamientoMapper.toDto(equipamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipamientoMockMvc.perform(put("/api/equipamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipamiento in the database
        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipamiento() throws Exception {
        // Initialize the database
        equipamientoRepository.saveAndFlush(equipamiento);

        int databaseSizeBeforeDelete = equipamientoRepository.findAll().size();

        // Delete the equipamiento
        restEquipamientoMockMvc.perform(delete("/api/equipamientos/{id}", equipamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Equipamiento> equipamientoList = equipamientoRepository.findAll();
        assertThat(equipamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipamiento.class);
        Equipamiento equipamiento1 = new Equipamiento();
        equipamiento1.setId(1L);
        Equipamiento equipamiento2 = new Equipamiento();
        equipamiento2.setId(equipamiento1.getId());
        assertThat(equipamiento1).isEqualTo(equipamiento2);
        equipamiento2.setId(2L);
        assertThat(equipamiento1).isNotEqualTo(equipamiento2);
        equipamiento1.setId(null);
        assertThat(equipamiento1).isNotEqualTo(equipamiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipamientoDTO.class);
        EquipamientoDTO equipamientoDTO1 = new EquipamientoDTO();
        equipamientoDTO1.setId(1L);
        EquipamientoDTO equipamientoDTO2 = new EquipamientoDTO();
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
        equipamientoDTO2.setId(equipamientoDTO1.getId());
        assertThat(equipamientoDTO1).isEqualTo(equipamientoDTO2);
        equipamientoDTO2.setId(2L);
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
        equipamientoDTO1.setId(null);
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(equipamientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(equipamientoMapper.fromId(null)).isNull();
    }
}
