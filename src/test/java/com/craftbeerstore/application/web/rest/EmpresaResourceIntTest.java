package com.craftbeerstore.application.web.rest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Test class for the EmpresaResource REST controller.
 *
 * @see EmpresaResource
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CraftBeerStoreApp.class)
public class EmpresaResourceIntTest {

//    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
//    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
//    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
//    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_CODIGO_POSTAL = 1L;
//    private static final Long UPDATED_CODIGO_POSTAL = 2L;
//
//    private static final Provincia DEFAULT_PROVINCIA = Provincia.MISIONES;
//    private static final Provincia UPDATED_PROVINCIA = Provincia.SAN_LUIS;
//
//    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
//    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CORREO = "QV@4D\\DLSND";
//    private static final String UPDATED_CORREO = "4@E\\[LFZCZ";
//
//    @Autowired
//    private EmpresaRepository empresaRepository;
//
//    @Autowired
//    private EmpresaMapper empresaMapper;
//
//    @Autowired
//    private EmpresaService empresaService;
//
//    /**
//     * This repository is mocked in the com.craftbeerstore.application.repository.search test package.
//     *
//     * @see com.craftbeerstore.application.repository.search.EmpresaSearchRepositoryMockConfiguration
//     */
//    @Autowired
//    private EmpresaSearchRepository mockEmpresaSearchRepository;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private Validator validator;
//
//    private MockMvc restEmpresaMockMvc;
//
//    private Empresa empresa;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final EmpresaResource empresaResource = new EmpresaResource(empresaService);
//        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter)
//            .setValidator(validator).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Empresa createEntity(EntityManager em) {
//        Empresa empresa = new Empresa()
//            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA)
//            .direccion(DEFAULT_DIRECCION)
//            .localidad(DEFAULT_LOCALIDAD)
//            .codigoPostal(DEFAULT_CODIGO_POSTAL)
//            .provincia(DEFAULT_PROVINCIA)
//            .telefono(DEFAULT_TELEFONO)
//            .correo(DEFAULT_CORREO);
//        return empresa;
//    }
//
//    @Before
//    public void initTest() {
//        empresa = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createEmpresa() throws Exception {
//        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
//
//        // Create the Empresa
//        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
//        restEmpresaMockMvc.perform(post("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Empresa in the database
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
//        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
//        assertThat(testEmpresa.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
//        assertThat(testEmpresa.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
//        assertThat(testEmpresa.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
//        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
//        assertThat(testEmpresa.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
//        assertThat(testEmpresa.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
//        assertThat(testEmpresa.getCorreo()).isEqualTo(DEFAULT_CORREO);
//
//        // Validate the Empresa in Elasticsearch
//        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
//    }
//
//    @Test
//    @Transactional
//    public void createEmpresaWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
//
//        // Create the Empresa with an existing ID
//        empresa.setId(1L);
//        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restEmpresaMockMvc.perform(post("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Empresa in the database
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
//
//        // Validate the Empresa in Elasticsearch
//        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
//    }
//
//    @Test
//    @Transactional
//    public void checkNombreEmpresaIsRequired() throws Exception {
//        int databaseSizeBeforeTest = empresaRepository.findAll().size();
//        // set the field null
//        empresa.setNombreEmpresa(null);
//
//        // Create the Empresa, which fails.
//        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
//
//        restEmpresaMockMvc.perform(post("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkDireccionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = empresaRepository.findAll().size();
//        // set the field null
//        empresa.setDireccion(null);
//
//        // Create the Empresa, which fails.
//        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
//
//        restEmpresaMockMvc.perform(post("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllEmpresas() throws Exception {
//        // Initialize the database
//        empresaRepository.saveAndFlush(empresa);
//
//        // Get all the empresaList
//        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
//            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA.toString())))
//            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
//            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
//            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
//            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
//            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
//            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getEmpresa() throws Exception {
//        // Initialize the database
//        empresaRepository.saveAndFlush(empresa);
//
//        // Get the empresa
//        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
//            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA.toString()))
//            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
//            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD.toString()))
//            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.intValue()))
//            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
//            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
//            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingEmpresa() throws Exception {
//        // Get the empresa
//        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateEmpresa() throws Exception {
//        // Initialize the database
//        empresaRepository.saveAndFlush(empresa);
//
//        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
//
//        // Update the empresa
//        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
//        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
//        em.detach(updatedEmpresa);
//        updatedEmpresa
//            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
//            .direccion(UPDATED_DIRECCION)
//            .localidad(UPDATED_LOCALIDAD)
//            .codigoPostal(UPDATED_CODIGO_POSTAL)
//            .provincia(UPDATED_PROVINCIA)
//            .telefono(UPDATED_TELEFONO)
//            .correo(UPDATED_CORREO);
//        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);
//
//        restEmpresaMockMvc.perform(put("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Empresa in the database
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
//        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
//        assertThat(testEmpresa.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
//        assertThat(testEmpresa.getDireccion()).isEqualTo(UPDATED_DIRECCION);
//        assertThat(testEmpresa.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
//        assertThat(testEmpresa.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
//        assertThat(testEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
//        assertThat(testEmpresa.getTelefono()).isEqualTo(UPDATED_TELEFONO);
//        assertThat(testEmpresa.getCorreo()).isEqualTo(UPDATED_CORREO);
//
//        // Validate the Empresa in Elasticsearch
//        verify(mockEmpresaSearchRepository, times(1)).save(testEmpresa);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingEmpresa() throws Exception {
//        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
//
//        // Create the Empresa
//        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restEmpresaMockMvc.perform(put("/api/empresas")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Empresa in the database
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
//
//        // Validate the Empresa in Elasticsearch
//        verify(mockEmpresaSearchRepository, times(0)).save(empresa);
//    }
//
//    @Test
//    @Transactional
//    public void deleteEmpresa() throws Exception {
//        // Initialize the database
//        empresaRepository.saveAndFlush(empresa);
//
//        int databaseSizeBeforeDelete = empresaRepository.findAll().size();
//
//        // Delete the empresa
//        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Empresa> empresaList = empresaRepository.findAll();
//        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
//
//        // Validate the Empresa in Elasticsearch
//        verify(mockEmpresaSearchRepository, times(1)).deleteById(empresa.getId());
//    }
//
//    @Test
//    @Transactional
//    public void searchEmpresa() throws Exception {
//        // Initialize the database
//        empresaRepository.saveAndFlush(empresa);
//        when(mockEmpresaSearchRepository.search(queryStringQuery("id:" + empresa.getId()), PageRequest.of(0, 20)))
//            .thenReturn(new PageImpl<>(Collections.singletonList(empresa), PageRequest.of(0, 1), 1));
//        // Search the empresa
//        restEmpresaMockMvc.perform(get("/api/_search/empresas?query=id:" + empresa.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
//            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)))
//            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
//            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
//            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
//            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
//            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
//            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)));
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Empresa.class);
//        Empresa empresa1 = new Empresa();
//        empresa1.setId(1L);
//        Empresa empresa2 = new Empresa();
//        empresa2.setId(empresa1.getId());
//        assertThat(empresa1).isEqualTo(empresa2);
//        empresa2.setId(2L);
//        assertThat(empresa1).isNotEqualTo(empresa2);
//        empresa1.setId(null);
//        assertThat(empresa1).isNotEqualTo(empresa2);
//    }
//
//    @Test
//    @Transactional
//    public void dtoEqualsVerifier() throws Exception {
//        TestUtil.equalsVerifier(EmpresaDTO.class);
//        EmpresaDTO empresaDTO1 = new EmpresaDTO();
//        empresaDTO1.setId(1L);
//        EmpresaDTO empresaDTO2 = new EmpresaDTO();
//        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
//        empresaDTO2.setId(empresaDTO1.getId());
//        assertThat(empresaDTO1).isEqualTo(empresaDTO2);
//        empresaDTO2.setId(2L);
//        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
//        empresaDTO1.setId(null);
//        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
//    }
//
//    @Test
//    @Transactional
//    public void testEntityFromId() {
//        assertThat(empresaMapper.fromId(42L).getId()).isEqualTo(42);
//        assertThat(empresaMapper.fromId(null)).isNull();
//    }
}
