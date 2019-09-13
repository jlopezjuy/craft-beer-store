package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.LoteService;
import com.craftbeerstore.application.service.dto.LoteDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lote.
 */
@RestController
@RequestMapping("/api")
public class LoteResource {

  private final Logger log = LoggerFactory.getLogger(LoteResource.class);

  private static final String ENTITY_NAME = "lote";

  private final LoteService loteService;

  public LoteResource(LoteService loteService) {
    this.loteService = loteService;
  }

  /**
   * POST  /lotes : Create a new lote.
   *
   * @param loteDTO the loteDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new loteDTO, or with status 400 (Bad Request) if the lote has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/lotes")
  public ResponseEntity<LoteDTO> createLote(@RequestBody LoteDTO loteDTO) throws URISyntaxException {
    log.debug("REST request to save Lote : {}", loteDTO);
    if (loteDTO.getId() != null) {
      throw new BadRequestAlertException("A new lote cannot already have an ID", ENTITY_NAME, "idexists");
    }
    LoteDTO result = loteService.save(loteDTO);
    return ResponseEntity.created(new URI("/api/lotes/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * PUT  /lotes : Updates an existing lote.
   *
   * @param loteDTO the loteDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated loteDTO,
   * or with status 400 (Bad Request) if the loteDTO is not valid,
   * or with status 500 (Internal Server Error) if the loteDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/lotes")
  public ResponseEntity<LoteDTO> updateLote(@RequestBody LoteDTO loteDTO) throws URISyntaxException {
    log.debug("REST request to update Lote : {}", loteDTO);
    if (loteDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    LoteDTO result = loteService.save(loteDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loteDTO.getId().toString()))
      .body(result);
  }

  /**
   * GET  /lotes : get all the lotes.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
   */
  @GetMapping("/lotes")
  public ResponseEntity<List<LoteDTO>> getAllLotes(Pageable pageable) {
    log.debug("REST request to get a page of Lotes");
    Page<LoteDTO> page = loteService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lotes");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET  /lotes/empresa/:empresaId : get all the lotes.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
   */
  @GetMapping("/lotes/empresa/{empresaId}")
  public ResponseEntity<List<LoteDTO>> getAllLotesEmpresa(Pageable pageable, @PathVariable Long empresaId) {
    log.debug("REST request to get a page of Lotes");
    Page<LoteDTO> page = loteService.findAll(pageable, empresaId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lotes/empresa/{empresaId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET  /lotes/:id : get the "id" lote.
   *
   * @param id the id of the loteDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the loteDTO, or with status 404 (Not Found)
   */
  @GetMapping("/lotes/{id}")
  public ResponseEntity<LoteDTO> getLote(@PathVariable Long id) {
    log.debug("REST request to get Lote : {}", id);
    Optional<LoteDTO> loteDTO = loteService.findOne(id);
    return ResponseUtil.wrapOrNotFound(loteDTO);
  }

  /**
   * DELETE  /lotes/:id : delete the "id" lote.
   *
   * @param id the id of the loteDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/lotes/{id}")
  public ResponseEntity<Void> deleteLote(@PathVariable Long id) {
    log.debug("REST request to delete Lote : {}", id);
    loteService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
}
