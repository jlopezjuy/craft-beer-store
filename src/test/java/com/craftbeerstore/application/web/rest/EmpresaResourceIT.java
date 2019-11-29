package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;
import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.EmpresaService;
import com.craftbeerstore.application.service.dto.EmpresaDTO;
import com.craftbeerstore.application.service.mapper.EmpresaMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.Provincia;
/**
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EmpresaResourceIT {

    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final Long DEFAULT_CODIGO_POSTAL = 1L;
    private static final Long UPDATED_CODIGO_POSTAL = 2L;

    private static final Provincia DEFAULT_PROVINCIA = Provincia.MISIONES;
    private static final Provincia UPDATED_PROVINCIA = Provincia.SAN_LUIS;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "qh@fk.ynnl";
    private static final String UPDATED_CORREO = "n@hu.tehs";

    private static final byte[] DEFAULT_LOGO_PRINCIPAL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_PRINCIPAL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_PRINCIPAL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_PRINCIPAL_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_FECHA_INICIO_ACTIVIDAD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO_ACTIVIDAD = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaService empresaService;

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

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaResource empresaResource = new EmpresaResource(empresaService);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
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
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA)
            .direccion(DEFAULT_DIRECCION)
            .localidad(DEFAULT_LOCALIDAD)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .provincia(DEFAULT_PROVINCIA)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .logoPrincipal(DEFAULT_LOGO_PRINCIPAL)
            .logoPrincipalContentType(DEFAULT_LOGO_PRINCIPAL_CONTENT_TYPE)
            .fechaInicioActividad(DEFAULT_FECHA_INICIO_ACTIVIDAD);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .direccion(UPDATED_DIRECCION)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .logoPrincipal(UPDATED_LOGO_PRINCIPAL)
            .logoPrincipalContentType(UPDATED_LOGO_PRINCIPAL_CONTENT_TYPE)
            .fechaInicioActividad(UPDATED_FECHA_INICIO_ACTIVIDAD);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
        assertThat(testEmpresa.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEmpresa.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testEmpresa.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpresa.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testEmpresa.getLogoPrincipal()).isEqualTo(DEFAULT_LOGO_PRINCIPAL);
        assertThat(testEmpresa.getLogoPrincipalContentType()).isEqualTo(DEFAULT_LOGO_PRINCIPAL_CONTENT_TYPE);
        assertThat(testEmpresa.getFechaInicioActividad()).isEqualTo(DEFAULT_FECHA_INICIO_ACTIVIDAD);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNombreEmpresa(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setDireccion(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].logoPrincipalContentType").value(hasItem(DEFAULT_LOGO_PRINCIPAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoPrincipal").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO_PRINCIPAL))))
            .andExpect(jsonPath("$.[*].fechaInicioActividad").value(hasItem(DEFAULT_FECHA_INICIO_ACTIVIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.intValue()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.logoPrincipalContentType").value(DEFAULT_LOGO_PRINCIPAL_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoPrincipal").value(Base64Utils.encodeToString(DEFAULT_LOGO_PRINCIPAL)))
            .andExpect(jsonPath("$.fechaInicioActividad").value(DEFAULT_FECHA_INICIO_ACTIVIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .direccion(UPDATED_DIRECCION)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .logoPrincipal(UPDATED_LOGO_PRINCIPAL)
            .logoPrincipalContentType(UPDATED_LOGO_PRINCIPAL_CONTENT_TYPE)
            .fechaInicioActividad(UPDATED_FECHA_INICIO_ACTIVIDAD);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
        assertThat(testEmpresa.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEmpresa.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpresa.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testEmpresa.getLogoPrincipal()).isEqualTo(UPDATED_LOGO_PRINCIPAL);
        assertThat(testEmpresa.getLogoPrincipalContentType()).isEqualTo(UPDATED_LOGO_PRINCIPAL_CONTENT_TYPE);
        assertThat(testEmpresa.getFechaInicioActividad()).isEqualTo(UPDATED_FECHA_INICIO_ACTIVIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
