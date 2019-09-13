package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.ProveedorService;
import com.craftbeerstore.application.service.dto.ProveedorDTO;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Proveedor.
 */
@RestController
@RequestMapping("/api")
public class ProveedorResource {

  private final Logger log = LoggerFactory.getLogger(ProveedorResource.class);

  private static final String ENTITY_NAME = "proveedor";

  private final ProveedorService proveedorService;

  public ProveedorResource(ProveedorService proveedorService) {
    this.proveedorService = proveedorService;
  }

  /**
   * POST  /proveedors : Create a new proveedor.
   *
   * @param proveedorDTO the proveedorDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new proveedorDTO, or with status 400 (Bad Request) if the proveedor has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/proveedors")
  public ResponseEntity<ProveedorDTO> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) throws URISyntaxException {
    log.debug("REST request to save Proveedor : {}", proveedorDTO);
    if (proveedorDTO.getId() != null) {
      throw new BadRequestAlertException("A new proveedor cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ProveedorDTO result = proveedorService.save(proveedorDTO);
    return ResponseEntity.created(new URI("/api/proveedors/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * PUT  /proveedors : Updates an existing proveedor.
   *
   * @param proveedorDTO the proveedorDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated proveedorDTO,
   * or with status 400 (Bad Request) if the proveedorDTO is not valid,
   * or with status 500 (Internal Server Error) if the proveedorDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/proveedors")
  public ResponseEntity<ProveedorDTO> updateProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) throws URISyntaxException {
    log.debug("REST request to update Proveedor : {}", proveedorDTO);
    if (proveedorDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    ProveedorDTO result = proveedorService.save(proveedorDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proveedorDTO.getId().toString()))
      .body(result);
  }

  /**
   * GET  /proveedors : get all the proveedors.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of proveedors in body
   */
  @GetMapping("/proveedors")
  public ResponseEntity<List<ProveedorDTO>> getAllProveedors(Pageable pageable) {
    log.debug("REST request to get a page of Proveedors");
    Page<ProveedorDTO> page = proveedorService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proveedors");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET /proveedors/empresa/{empresaId} : get all the proveedors by empresa
   *
   * @param pageable  the pagination information
   * @param empresaId the id of empresaDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and the list of productos by empresa in body
   */
  @GetMapping("/proveedors/empresa/{empresaId}")
  public ResponseEntity<List<ProveedorDTO>> getAllProveedorsByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
    log.debug("REST request to get a page of Proveedors");
    Page<ProveedorDTO> page = proveedorService.findAllByEmpresa(pageable, empresaId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proveedors/empresa/{empresaId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET  /proveedors/:id : get the "id" proveedor.
   *
   * @param id the id of the proveedorDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the proveedorDTO, or with status 404 (Not Found)
   */
  @GetMapping("/proveedors/{id}")
  public ResponseEntity<ProveedorDTO> getProveedor(@PathVariable Long id) {
    log.debug("REST request to get Proveedor : {}", id);
    Optional<ProveedorDTO> proveedorDTO = proveedorService.findOne(id);
    return ResponseUtil.wrapOrNotFound(proveedorDTO);
  }

  /**
   * DELETE  /proveedors/:id : delete the "id" proveedor.
   *
   * @param id the id of the proveedorDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/proveedors/{id}")
  public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
    log.debug("REST request to delete Proveedor : {}", id);
    proveedorService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }

}
