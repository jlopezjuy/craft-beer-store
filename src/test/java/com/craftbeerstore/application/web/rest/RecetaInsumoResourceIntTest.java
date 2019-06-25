package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.RecetaInsumo;
import com.craftbeerstore.application.repository.RecetaInsumoRepository;
import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
import com.craftbeerstore.application.service.mapper.RecetaInsumoMapper;
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
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.domain.enumeration.UsoMalta;
import com.craftbeerstore.application.domain.enumeration.ModoLupulo;
import com.craftbeerstore.application.domain.enumeration.UsoLupulo;
import com.craftbeerstore.application.domain.enumeration.TipoOtro;
import com.craftbeerstore.application.domain.enumeration.UsoOtro;
/**
 * Test class for the RecetaInsumoResource REST controller.
 *
 * @see RecetaInsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class RecetaInsumoResourceIntTest {

    private static final TipoInsumo DEFAULT_TIPO_INSUMO = TipoInsumo.MALTA;
    private static final TipoInsumo UPDATED_TIPO_INSUMO = TipoInsumo.LUPULO;

    private static final BigDecimal DEFAULT_CANTIDAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANTIDAD = new BigDecimal(2);

    private static final Long DEFAULT_COLOR = 2L;
    private static final Long UPDATED_COLOR = 3L;

    private static final BigDecimal DEFAULT_PORCENTAJE = new BigDecimal(100);
    private static final BigDecimal UPDATED_PORCENTAJE = new BigDecimal(99);

    private static final UsoMalta DEFAULT_USO_MALTA = UsoMalta.MASH;
    private static final UsoMalta UPDATED_USO_MALTA = UsoMalta.RECIRCULATING;

    private static final Long DEFAULT_ALPHA = 100L;
    private static final Long UPDATED_ALPHA = 99L;

    private static final ModoLupulo DEFAULT_MODO_LUPULO = ModoLupulo.PELLET;
    private static final ModoLupulo UPDATED_MODO_LUPULO = ModoLupulo.PLUG;

    private static final BigDecimal DEFAULT_GRAMOS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAMOS = new BigDecimal(2);

    private static final UsoLupulo DEFAULT_USO_LUPULO = UsoLupulo.BOIL;
    private static final UsoLupulo UPDATED_USO_LUPULO = UsoLupulo.FIRST_WORT;

    private static final Long DEFAULT_TIEMPO = 0L;
    private static final Long UPDATED_TIEMPO = 1L;

    private static final BigDecimal DEFAULT_IBU = new BigDecimal(1);
    private static final BigDecimal UPDATED_IBU = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DENSIDAD_LEVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_DENSIDAD_LEVA = new BigDecimal(2);

    private static final Long DEFAULT_TAM_SOBRE = 1L;
    private static final Long UPDATED_TAM_SOBRE = 2L;

    private static final Long DEFAULT_ATENUACION = 1L;
    private static final Long UPDATED_ATENUACION = 2L;

    private static final TipoOtro DEFAULT_TIPO_OTRO = TipoOtro.FINING;
    private static final TipoOtro UPDATED_TIPO_OTRO = TipoOtro.WATER_AGENT;

    private static final UsoOtro DEFAULT_USO_OTRO = UsoOtro.BOIL;
    private static final UsoOtro UPDATED_USO_OTRO = UsoOtro.MASH;

    private static final Long DEFAULT_TIEMPO_OTRO = 1L;
    private static final Long UPDATED_TIEMPO_OTRO = 2L;

    @Autowired
    private RecetaInsumoRepository recetaInsumoRepository;

    @Autowired
    private RecetaInsumoMapper recetaInsumoMapper;

    @Autowired
    private RecetaInsumoService recetaInsumoService;

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

    private MockMvc restRecetaInsumoMockMvc;

    private RecetaInsumo recetaInsumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecetaInsumoResource recetaInsumoResource = new RecetaInsumoResource(recetaInsumoService);
        this.restRecetaInsumoMockMvc = MockMvcBuilders.standaloneSetup(recetaInsumoResource)
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
    public static RecetaInsumo createEntity(EntityManager em) {
        RecetaInsumo recetaInsumo = new RecetaInsumo()
            .tipoInsumo(DEFAULT_TIPO_INSUMO)
            .cantidad(DEFAULT_CANTIDAD)
            .color(DEFAULT_COLOR)
            .porcentaje(DEFAULT_PORCENTAJE)
            .usoMalta(DEFAULT_USO_MALTA)
            .alpha(DEFAULT_ALPHA)
            .modoLupulo(DEFAULT_MODO_LUPULO)
            .gramos(DEFAULT_GRAMOS)
            .usoLupulo(DEFAULT_USO_LUPULO)
            .tiempo(DEFAULT_TIEMPO)
            .ibu(DEFAULT_IBU)
            .densidadLeva(DEFAULT_DENSIDAD_LEVA)
            .tamSobre(DEFAULT_TAM_SOBRE)
            .atenuacion(DEFAULT_ATENUACION)
            .tipoOtro(DEFAULT_TIPO_OTRO)
            .usoOtro(DEFAULT_USO_OTRO)
            .tiempoOtro(DEFAULT_TIEMPO_OTRO);
        return recetaInsumo;
    }

    @Before
    public void initTest() {
        recetaInsumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecetaInsumo() throws Exception {
        int databaseSizeBeforeCreate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);
        restRecetaInsumoMockMvc.perform(post("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isCreated());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeCreate + 1);
        RecetaInsumo testRecetaInsumo = recetaInsumoList.get(recetaInsumoList.size() - 1);
        assertThat(testRecetaInsumo.getTipoInsumo()).isEqualTo(DEFAULT_TIPO_INSUMO);
        assertThat(testRecetaInsumo.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testRecetaInsumo.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testRecetaInsumo.getPorcentaje()).isEqualTo(DEFAULT_PORCENTAJE);
        assertThat(testRecetaInsumo.getUsoMalta()).isEqualTo(DEFAULT_USO_MALTA);
        assertThat(testRecetaInsumo.getAlpha()).isEqualTo(DEFAULT_ALPHA);
        assertThat(testRecetaInsumo.getModoLupulo()).isEqualTo(DEFAULT_MODO_LUPULO);
        assertThat(testRecetaInsumo.getGramos()).isEqualTo(DEFAULT_GRAMOS);
        assertThat(testRecetaInsumo.getUsoLupulo()).isEqualTo(DEFAULT_USO_LUPULO);
        assertThat(testRecetaInsumo.getTiempo()).isEqualTo(DEFAULT_TIEMPO);
        assertThat(testRecetaInsumo.getIbu()).isEqualTo(DEFAULT_IBU);
        assertThat(testRecetaInsumo.getDensidadLeva()).isEqualTo(DEFAULT_DENSIDAD_LEVA);
        assertThat(testRecetaInsumo.getTamSobre()).isEqualTo(DEFAULT_TAM_SOBRE);
        assertThat(testRecetaInsumo.getAtenuacion()).isEqualTo(DEFAULT_ATENUACION);
        assertThat(testRecetaInsumo.getTipoOtro()).isEqualTo(DEFAULT_TIPO_OTRO);
        assertThat(testRecetaInsumo.getUsoOtro()).isEqualTo(DEFAULT_USO_OTRO);
        assertThat(testRecetaInsumo.getTiempoOtro()).isEqualTo(DEFAULT_TIEMPO_OTRO);
    }

    @Test
    @Transactional
    public void createRecetaInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo with an existing ID
        recetaInsumo.setId(1L);
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecetaInsumoMockMvc.perform(post("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecetaInsumos() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        // Get all the recetaInsumoList
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recetaInsumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoInsumo").value(hasItem(DEFAULT_TIPO_INSUMO.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.intValue())))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.intValue())))
            .andExpect(jsonPath("$.[*].usoMalta").value(hasItem(DEFAULT_USO_MALTA.toString())))
            .andExpect(jsonPath("$.[*].alpha").value(hasItem(DEFAULT_ALPHA.intValue())))
            .andExpect(jsonPath("$.[*].modoLupulo").value(hasItem(DEFAULT_MODO_LUPULO.toString())))
            .andExpect(jsonPath("$.[*].gramos").value(hasItem(DEFAULT_GRAMOS.intValue())))
            .andExpect(jsonPath("$.[*].usoLupulo").value(hasItem(DEFAULT_USO_LUPULO.toString())))
            .andExpect(jsonPath("$.[*].tiempo").value(hasItem(DEFAULT_TIEMPO.intValue())))
            .andExpect(jsonPath("$.[*].ibu").value(hasItem(DEFAULT_IBU.intValue())))
            .andExpect(jsonPath("$.[*].densidadLeva").value(hasItem(DEFAULT_DENSIDAD_LEVA.intValue())))
            .andExpect(jsonPath("$.[*].tamSobre").value(hasItem(DEFAULT_TAM_SOBRE.intValue())))
            .andExpect(jsonPath("$.[*].atenuacion").value(hasItem(DEFAULT_ATENUACION.intValue())))
            .andExpect(jsonPath("$.[*].tipoOtro").value(hasItem(DEFAULT_TIPO_OTRO.toString())))
            .andExpect(jsonPath("$.[*].usoOtro").value(hasItem(DEFAULT_USO_OTRO.toString())))
            .andExpect(jsonPath("$.[*].tiempoOtro").value(hasItem(DEFAULT_TIEMPO_OTRO.intValue())));
    }

    @Test
    @Transactional
    public void getRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        // Get the recetaInsumo
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos/{id}", recetaInsumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recetaInsumo.getId().intValue()))
            .andExpect(jsonPath("$.tipoInsumo").value(DEFAULT_TIPO_INSUMO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.intValue()))
            .andExpect(jsonPath("$.porcentaje").value(DEFAULT_PORCENTAJE.intValue()))
            .andExpect(jsonPath("$.usoMalta").value(DEFAULT_USO_MALTA.toString()))
            .andExpect(jsonPath("$.alpha").value(DEFAULT_ALPHA.intValue()))
            .andExpect(jsonPath("$.modoLupulo").value(DEFAULT_MODO_LUPULO.toString()))
            .andExpect(jsonPath("$.gramos").value(DEFAULT_GRAMOS.intValue()))
            .andExpect(jsonPath("$.usoLupulo").value(DEFAULT_USO_LUPULO.toString()))
            .andExpect(jsonPath("$.tiempo").value(DEFAULT_TIEMPO.intValue()))
            .andExpect(jsonPath("$.ibu").value(DEFAULT_IBU.intValue()))
            .andExpect(jsonPath("$.densidadLeva").value(DEFAULT_DENSIDAD_LEVA.intValue()))
            .andExpect(jsonPath("$.tamSobre").value(DEFAULT_TAM_SOBRE.intValue()))
            .andExpect(jsonPath("$.atenuacion").value(DEFAULT_ATENUACION.intValue()))
            .andExpect(jsonPath("$.tipoOtro").value(DEFAULT_TIPO_OTRO.toString()))
            .andExpect(jsonPath("$.usoOtro").value(DEFAULT_USO_OTRO.toString()))
            .andExpect(jsonPath("$.tiempoOtro").value(DEFAULT_TIEMPO_OTRO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecetaInsumo() throws Exception {
        // Get the recetaInsumo
        restRecetaInsumoMockMvc.perform(get("/api/receta-insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        int databaseSizeBeforeUpdate = recetaInsumoRepository.findAll().size();

        // Update the recetaInsumo
        RecetaInsumo updatedRecetaInsumo = recetaInsumoRepository.findById(recetaInsumo.getId()).get();
        // Disconnect from session so that the updates on updatedRecetaInsumo are not directly saved in db
        em.detach(updatedRecetaInsumo);
        updatedRecetaInsumo
            .tipoInsumo(UPDATED_TIPO_INSUMO)
            .cantidad(UPDATED_CANTIDAD)
            .color(UPDATED_COLOR)
            .porcentaje(UPDATED_PORCENTAJE)
            .usoMalta(UPDATED_USO_MALTA)
            .alpha(UPDATED_ALPHA)
            .modoLupulo(UPDATED_MODO_LUPULO)
            .gramos(UPDATED_GRAMOS)
            .usoLupulo(UPDATED_USO_LUPULO)
            .tiempo(UPDATED_TIEMPO)
            .ibu(UPDATED_IBU)
            .densidadLeva(UPDATED_DENSIDAD_LEVA)
            .tamSobre(UPDATED_TAM_SOBRE)
            .atenuacion(UPDATED_ATENUACION)
            .tipoOtro(UPDATED_TIPO_OTRO)
            .usoOtro(UPDATED_USO_OTRO)
            .tiempoOtro(UPDATED_TIEMPO_OTRO);
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(updatedRecetaInsumo);

        restRecetaInsumoMockMvc.perform(put("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isOk());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeUpdate);
        RecetaInsumo testRecetaInsumo = recetaInsumoList.get(recetaInsumoList.size() - 1);
        assertThat(testRecetaInsumo.getTipoInsumo()).isEqualTo(UPDATED_TIPO_INSUMO);
        assertThat(testRecetaInsumo.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRecetaInsumo.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testRecetaInsumo.getPorcentaje()).isEqualTo(UPDATED_PORCENTAJE);
        assertThat(testRecetaInsumo.getUsoMalta()).isEqualTo(UPDATED_USO_MALTA);
        assertThat(testRecetaInsumo.getAlpha()).isEqualTo(UPDATED_ALPHA);
        assertThat(testRecetaInsumo.getModoLupulo()).isEqualTo(UPDATED_MODO_LUPULO);
        assertThat(testRecetaInsumo.getGramos()).isEqualTo(UPDATED_GRAMOS);
        assertThat(testRecetaInsumo.getUsoLupulo()).isEqualTo(UPDATED_USO_LUPULO);
        assertThat(testRecetaInsumo.getTiempo()).isEqualTo(UPDATED_TIEMPO);
        assertThat(testRecetaInsumo.getIbu()).isEqualTo(UPDATED_IBU);
        assertThat(testRecetaInsumo.getDensidadLeva()).isEqualTo(UPDATED_DENSIDAD_LEVA);
        assertThat(testRecetaInsumo.getTamSobre()).isEqualTo(UPDATED_TAM_SOBRE);
        assertThat(testRecetaInsumo.getAtenuacion()).isEqualTo(UPDATED_ATENUACION);
        assertThat(testRecetaInsumo.getTipoOtro()).isEqualTo(UPDATED_TIPO_OTRO);
        assertThat(testRecetaInsumo.getUsoOtro()).isEqualTo(UPDATED_USO_OTRO);
        assertThat(testRecetaInsumo.getTiempoOtro()).isEqualTo(UPDATED_TIEMPO_OTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingRecetaInsumo() throws Exception {
        int databaseSizeBeforeUpdate = recetaInsumoRepository.findAll().size();

        // Create the RecetaInsumo
        RecetaInsumoDTO recetaInsumoDTO = recetaInsumoMapper.toDto(recetaInsumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetaInsumoMockMvc.perform(put("/api/receta-insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recetaInsumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecetaInsumo in the database
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecetaInsumo() throws Exception {
        // Initialize the database
        recetaInsumoRepository.saveAndFlush(recetaInsumo);

        int databaseSizeBeforeDelete = recetaInsumoRepository.findAll().size();

        // Delete the recetaInsumo
        restRecetaInsumoMockMvc.perform(delete("/api/receta-insumos/{id}", recetaInsumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecetaInsumo> recetaInsumoList = recetaInsumoRepository.findAll();
        assertThat(recetaInsumoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumo.class);
        RecetaInsumo recetaInsumo1 = new RecetaInsumo();
        recetaInsumo1.setId(1L);
        RecetaInsumo recetaInsumo2 = new RecetaInsumo();
        recetaInsumo2.setId(recetaInsumo1.getId());
        assertThat(recetaInsumo1).isEqualTo(recetaInsumo2);
        recetaInsumo2.setId(2L);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
        recetaInsumo1.setId(null);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumoDTO.class);
        RecetaInsumoDTO recetaInsumoDTO1 = new RecetaInsumoDTO();
        recetaInsumoDTO1.setId(1L);
        RecetaInsumoDTO recetaInsumoDTO2 = new RecetaInsumoDTO();
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(recetaInsumoDTO1.getId());
        assertThat(recetaInsumoDTO1).isEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(2L);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO1.setId(null);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recetaInsumoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recetaInsumoMapper.fromId(null)).isNull();
    }
}
