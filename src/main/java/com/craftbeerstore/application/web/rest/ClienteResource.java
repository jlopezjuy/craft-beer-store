package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.ClienteService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.ClienteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cliente.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * POST  /clientes : Create a new cliente.
     *
     * @param clienteDTO the clienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clienteDTO, or with status 400 (Bad Request) if the cliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clientes")
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", clienteDTO);
        if (clienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteDTO result = clienteService.save(clienteDTO);
        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clientes : Updates an existing cliente.
     *
     * @param clienteDTO the clienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clienteDTO,
     * or with status 400 (Bad Request) if the clienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the clienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clientes")
    public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        log.debug("REST request to update Cliente : {}", clienteDTO);
        if (clienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteDTO result = clienteService.save(clienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clientes : get all the clientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientes in body
     */
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> getAllClientes(Pageable pageable) {
        log.debug("REST request to get a page of Clientes");
        Page<ClienteDTO> page = clienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     *
     * @param pageable
     * @param empresaId
     * @return
     */
    @GetMapping("/clientes/empresa/{empresaId}")
    public ResponseEntity<List<ClienteDTO>> getAllClientesByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        Page<ClienteDTO> page = clienteService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,"/api/clientes/empresa/{empresaId}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /clientes/:id : get the "id" cliente.
     *
     * @param id the id of the clienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        Optional<ClienteDTO> clienteDTO = clienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteDTO);
    }

    /**
     * DELETE  /clientes/:id : delete the "id" cliente.
     *
     * @param id the id of the clienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clientes?query=:query : search for the cliente corresponding
     * to the query.
     *
     * @param query the query of the cliente search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clientes")
    public ResponseEntity<List<ClienteDTO>> searchClientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clientes for query {}", query);
        Page<ClienteDTO> page = clienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clientes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
