package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.CraftBeerStoreApp;

import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.repository.ClienteRepository;
import com.craftbeerstore.application.service.ClienteService;
import com.craftbeerstore.application.service.dto.ClienteDTO;
import com.craftbeerstore.application.service.mapper.ClienteMapper;
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
import java.util.Collections;
import java.util.List;


import static com.craftbeerstore.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.craftbeerstore.application.domain.enumeration.Provincia;
import com.craftbeerstore.application.domain.enumeration.TipoCliente;
/**
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftBeerStoreApp.class)
public class ClienteResourceIntTest {

    private static final String DEFAULT_NOMBRE_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final Long DEFAULT_CODIGO_POSTAL = 1L;
    private static final Long UPDATED_CODIGO_POSTAL = 2L;

    private static final Provincia DEFAULT_PROVINCIA = Provincia.MISIONES;
    private static final Provincia UPDATED_PROVINCIA = Provincia.SAN_LUIS;

    private static final TipoCliente DEFAULT_TIPO_CLIENTE = TipoCliente.CONSUMIDOR_FINAL;
    private static final TipoCliente UPDATED_TIPO_CLIENTE = TipoCliente.RESPONSABLE_INSCRIPTO;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "mj@y.nor";
    private static final String UPDATED_CORREO = "s@a.okr";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clienteService;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombreApellido(DEFAULT_NOMBRE_APELLIDO)
            .domicilio(DEFAULT_DOMICILIO)
            .localidad(DEFAULT_LOCALIDAD)
            .codigoPostal(DEFAULT_CODIGO_POSTAL)
            .provincia(DEFAULT_PROVINCIA)
            .tipoCliente(DEFAULT_TIPO_CLIENTE)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO);
        return cliente;
    }

    @Before
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreApellido()).isEqualTo(DEFAULT_NOMBRE_APELLIDO);
        assertThat(testCliente.getDomicilio()).isEqualTo(DEFAULT_DOMICILIO);
        assertThat(testCliente.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testCliente.getCodigoPostal()).isEqualTo(DEFAULT_CODIGO_POSTAL);
        assertThat(testCliente.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testCliente.getTipoCliente()).isEqualTo(DEFAULT_TIPO_CLIENTE);
        assertThat(testCliente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCliente.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombreApellido(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setDomicilio(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setTipoCliente(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreApellido").value(hasItem(DEFAULT_NOMBRE_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].domicilio").value(hasItem(DEFAULT_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD.toString())))
            .andExpect(jsonPath("$.[*].codigoPostal").value(hasItem(DEFAULT_CODIGO_POSTAL.intValue())))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA.toString())))
            .andExpect(jsonPath("$.[*].tipoCliente").value(hasItem(DEFAULT_TIPO_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombreApellido").value(DEFAULT_NOMBRE_APELLIDO.toString()))
            .andExpect(jsonPath("$.domicilio").value(DEFAULT_DOMICILIO.toString()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD.toString()))
            .andExpect(jsonPath("$.codigoPostal").value(DEFAULT_CODIGO_POSTAL.intValue()))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA.toString()))
            .andExpect(jsonPath("$.tipoCliente").value(DEFAULT_TIPO_CLIENTE.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nombreApellido(UPDATED_NOMBRE_APELLIDO)
            .domicilio(UPDATED_DOMICILIO)
            .localidad(UPDATED_LOCALIDAD)
            .codigoPostal(UPDATED_CODIGO_POSTAL)
            .provincia(UPDATED_PROVINCIA)
            .tipoCliente(UPDATED_TIPO_CLIENTE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombreApellido()).isEqualTo(UPDATED_NOMBRE_APELLIDO);
        assertThat(testCliente.getDomicilio()).isEqualTo(UPDATED_DOMICILIO);
        assertThat(testCliente.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testCliente.getCodigoPostal()).isEqualTo(UPDATED_CODIGO_POSTAL);
        assertThat(testCliente.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testCliente.getTipoCliente()).isEqualTo(UPDATED_TIPO_CLIENTE);
        assertThat(testCliente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCliente.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteDTO.class);
        ClienteDTO clienteDTO1 = new ClienteDTO();
        clienteDTO1.setId(1L);
        ClienteDTO clienteDTO2 = new ClienteDTO();
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO2.setId(clienteDTO1.getId());
        assertThat(clienteDTO1).isEqualTo(clienteDTO2);
        clienteDTO2.setId(2L);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO1.setId(null);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteMapper.fromId(null)).isNull();
    }
}
