package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.service.InsumoService;
import com.craftbeerstore.application.service.dto.InsumoDTO;
import com.craftbeerstore.application.service.mapper.InsumoMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.Unidad;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
/**
 * Integration tests for the {@link InsumoResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class InsumoResourceIT {

    private static final String DEFAULT_NOMBRE_INSUMO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_INSUMO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_STOCK = new BigDecimal(2);

    private static final Unidad DEFAULT_UNIDAD = Unidad.KILOGRAMO;
    private static final Unidad UPDATED_UNIDAD = Unidad.GRAMO;

    private static final TipoInsumo DEFAULT_TIPO = TipoInsumo.MALTA;
    private static final TipoInsumo UPDATED_TIPO = TipoInsumo.LUPULO;

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final BigDecimal DEFAULT_PRECIO_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_UNITARIO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRECIO_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_TOTAL = new BigDecimal(2);

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private InsumoMapper insumoMapper;

    @Autowired
    private InsumoService insumoService;

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

    private MockMvc restInsumoMockMvc;

    private Insumo insumo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsumoResource insumoResource = new InsumoResource(insumoService);
        this.restInsumoMockMvc = MockMvcBuilders.standaloneSetup(insumoResource)
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
    public static Insumo createEntity(EntityManager em) {
        Insumo insumo = new Insumo()
            .nombreInsumo(DEFAULT_NOMBRE_INSUMO)
            .marca(DEFAULT_MARCA)
            .stock(DEFAULT_STOCK)
            .unidad(DEFAULT_UNIDAD)
            .tipo(DEFAULT_TIPO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .precioUnitario(DEFAULT_PRECIO_UNITARIO)
            .precioTotal(DEFAULT_PRECIO_TOTAL);
        return insumo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insumo createUpdatedEntity(EntityManager em) {
        Insumo insumo = new Insumo()
            .nombreInsumo(UPDATED_NOMBRE_INSUMO)
            .marca(UPDATED_MARCA)
            .stock(UPDATED_STOCK)
            .unidad(UPDATED_UNIDAD)
            .tipo(UPDATED_TIPO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .precioTotal(UPDATED_PRECIO_TOTAL);
        return insumo;
    }

    @BeforeEach
    public void initTest() {
        insumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsumo() throws Exception {
        int databaseSizeBeforeCreate = insumoRepository.findAll().size();

        // Create the Insumo
        InsumoDTO insumoDTO = insumoMapper.toDto(insumo);
        restInsumoMockMvc.perform(post("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isCreated());

        // Validate the Insumo in the database
        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeCreate + 1);
        Insumo testInsumo = insumoList.get(insumoList.size() - 1);
        assertThat(testInsumo.getNombreInsumo()).isEqualTo(DEFAULT_NOMBRE_INSUMO);
        assertThat(testInsumo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testInsumo.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testInsumo.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testInsumo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testInsumo.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testInsumo.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testInsumo.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
        assertThat(testInsumo.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void createInsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insumoRepository.findAll().size();

        // Create the Insumo with an existing ID
        insumo.setId(1L);
        InsumoDTO insumoDTO = insumoMapper.toDto(insumo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsumoMockMvc.perform(post("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insumo in the database
        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreInsumoIsRequired() throws Exception {
        int databaseSizeBeforeTest = insumoRepository.findAll().size();
        // set the field null
        insumo.setNombreInsumo(null);

        // Create the Insumo, which fails.
        InsumoDTO insumoDTO = insumoMapper.toDto(insumo);

        restInsumoMockMvc.perform(post("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isBadRequest());

        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = insumoRepository.findAll().size();
        // set the field null
        insumo.setMarca(null);

        // Create the Insumo, which fails.
        InsumoDTO insumoDTO = insumoMapper.toDto(insumo);

        restInsumoMockMvc.perform(post("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isBadRequest());

        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInsumos() throws Exception {
        // Initialize the database
        insumoRepository.saveAndFlush(insumo);

        // Get all the insumoList
        restInsumoMockMvc.perform(get("/api/insumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreInsumo").value(hasItem(DEFAULT_NOMBRE_INSUMO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO.intValue())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getInsumo() throws Exception {
        // Initialize the database
        insumoRepository.saveAndFlush(insumo);

        // Get the insumo
        restInsumoMockMvc.perform(get("/api/insumos/{id}", insumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insumo.getId().intValue()))
            .andExpect(jsonPath("$.nombreInsumo").value(DEFAULT_NOMBRE_INSUMO))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO.intValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInsumo() throws Exception {
        // Get the insumo
        restInsumoMockMvc.perform(get("/api/insumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsumo() throws Exception {
        // Initialize the database
        insumoRepository.saveAndFlush(insumo);

        int databaseSizeBeforeUpdate = insumoRepository.findAll().size();

        // Update the insumo
        Insumo updatedInsumo = insumoRepository.findById(insumo.getId()).get();
        // Disconnect from session so that the updates on updatedInsumo are not directly saved in db
        em.detach(updatedInsumo);
        updatedInsumo
            .nombreInsumo(UPDATED_NOMBRE_INSUMO)
            .marca(UPDATED_MARCA)
            .stock(UPDATED_STOCK)
            .unidad(UPDATED_UNIDAD)
            .tipo(UPDATED_TIPO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .precioTotal(UPDATED_PRECIO_TOTAL);
        InsumoDTO insumoDTO = insumoMapper.toDto(updatedInsumo);

        restInsumoMockMvc.perform(put("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isOk());

        // Validate the Insumo in the database
        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeUpdate);
        Insumo testInsumo = insumoList.get(insumoList.size() - 1);
        assertThat(testInsumo.getNombreInsumo()).isEqualTo(UPDATED_NOMBRE_INSUMO);
        assertThat(testInsumo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testInsumo.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testInsumo.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testInsumo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testInsumo.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testInsumo.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testInsumo.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
        assertThat(testInsumo.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingInsumo() throws Exception {
        int databaseSizeBeforeUpdate = insumoRepository.findAll().size();

        // Create the Insumo
        InsumoDTO insumoDTO = insumoMapper.toDto(insumo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsumoMockMvc.perform(put("/api/insumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insumoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insumo in the database
        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInsumo() throws Exception {
        // Initialize the database
        insumoRepository.saveAndFlush(insumo);

        int databaseSizeBeforeDelete = insumoRepository.findAll().size();

        // Delete the insumo
        restInsumoMockMvc.perform(delete("/api/insumos/{id}", insumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insumo> insumoList = insumoRepository.findAll();
        assertThat(insumoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
