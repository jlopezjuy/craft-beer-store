package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.EmpresaService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.EmpresaDTO;
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
 * REST controller for managing Empresa.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";

    private final EmpresaService empresaService;

    public EmpresaResource(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * POST  /empresas : Create a new empresa.
     *
     * @param empresaDTO the empresaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empresaDTO, or with status 400 (Bad Request) if the empresa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empresas")
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresaDTO);
        if (empresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empresas : Updates an existing empresa.
     *
     * @param empresaDTO the empresaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empresaDTO,
     * or with status 400 (Bad Request) if the empresaDTO is not valid,
     * or with status 500 (Internal Server Error) if the empresaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empresas")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}", empresaDTO);
        if (empresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empresas : get all the empresas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of empresas in body
     */
    @GetMapping("/empresas")
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas(Pageable pageable) {
        log.debug("REST request to get a page of Empresas");
        Page<EmpresaDTO> page = empresaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/empresas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /empresas/:id : get the "id" empresa.
     *
     * @param id the id of the empresaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empresaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/empresas/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<EmpresaDTO> empresaDTO = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     *
     * @param email
     * @return
     */
    @GetMapping("/empresas/email/{email}")
    public ResponseEntity<EmpresaDTO> getEmpresaByMail(@PathVariable String email) {
        log.debug("REST request to get Empresa : {}", email);
        Optional<EmpresaDTO> empresaDTO = empresaService.findOneByEmail(email);
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     *
     * @return
     */
    @GetMapping("/empresas/usuario")
    public ResponseEntity<EmpresaDTO> getEmpresa() {
        log.debug("REST request to get Empresa by Loged User");
        Optional<EmpresaDTO> empresaDTO = empresaService.findOne();
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     * DELETE  /empresas/:id : delete the "id" empresa.
     *
     * @param id the id of the empresaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/empresas?query=:query : search for the empresa corresponding
     * to the query.
     *
     * @param query the query of the empresa search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/empresas")
    public ResponseEntity<List<EmpresaDTO>> searchEmpresas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Empresas for query {}", query);
        Page<EmpresaDTO> page = empresaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/empresas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
