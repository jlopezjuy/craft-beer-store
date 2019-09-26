package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.EtapaLoteService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.EtapaLoteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EtapaLote.
 */
@RestController
@RequestMapping("/api")
public class EtapaLoteResource {

    private final Logger log = LoggerFactory.getLogger(EtapaLoteResource.class);

    private static final String ENTITY_NAME = "etapaLote";

    private final EtapaLoteService etapaLoteService;

    public EtapaLoteResource(EtapaLoteService etapaLoteService) {
        this.etapaLoteService = etapaLoteService;
    }

    /**
     * POST  /etapa-lotes : Create a new etapaLote.
     *
     * @param etapaLoteDTO the etapaLoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapaLoteDTO, or with status 400 (Bad Request) if the etapaLote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etapa-lotes")
    public ResponseEntity<EtapaLoteDTO> createEtapaLote(@RequestBody EtapaLoteDTO etapaLoteDTO) throws URISyntaxException {
        log.debug("REST request to save EtapaLote : {}", etapaLoteDTO);
        if (etapaLoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapaLote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapaLoteDTO result = etapaLoteService.save(etapaLoteDTO);
        return ResponseEntity.created(new URI("/api/etapa-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etapa-lotes : Updates an existing etapaLote.
     *
     * @param etapaLoteDTO the etapaLoteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapaLoteDTO,
     * or with status 400 (Bad Request) if the etapaLoteDTO is not valid,
     * or with status 500 (Internal Server Error) if the etapaLoteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etapa-lotes")
    public ResponseEntity<EtapaLoteDTO> updateEtapaLote(@RequestBody EtapaLoteDTO etapaLoteDTO) throws URISyntaxException {
        log.debug("REST request to update EtapaLote : {}", etapaLoteDTO);
        if (etapaLoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtapaLoteDTO result = etapaLoteService.save(etapaLoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapaLoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etapa-lotes : get all the etapaLotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etapaLotes in body
     */
    @GetMapping("/etapa-lotes")
    public ResponseEntity<List<EtapaLoteDTO>> getAllEtapaLotes(Pageable pageable) {
        log.debug("REST request to get a page of EtapaLotes");
        Page<EtapaLoteDTO> page = etapaLoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etapa-lotes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

  /**
   * GET  /etapa-lotes : get all the etapaLotes.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of etapaLotes in body
   */
  @GetMapping("/etapa-lotes/lote/{loteId}")
  public ResponseEntity<List<EtapaLoteDTO>> getAllEtapaByLotes(Pageable pageable, @PathVariable Long loteId) {
    log.debug("REST request to get a page of EtapaLotes");
    Page<EtapaLoteDTO> page = etapaLoteService.findAll(pageable, loteId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etapa-lotes/lote/{loteId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

    /**
     * GET  /etapa-lotes/:id : get the "id" etapaLote.
     *
     * @param id the id of the etapaLoteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapaLoteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etapa-lotes/{id}")
    public ResponseEntity<EtapaLoteDTO> getEtapaLote(@PathVariable Long id) {
        log.debug("REST request to get EtapaLote : {}", id);
        Optional<EtapaLoteDTO> etapaLoteDTO = etapaLoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapaLoteDTO);
    }

    /**
     * DELETE  /etapa-lotes/:id : delete the "id" etapaLote.
     *
     * @param id the id of the etapaLoteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etapa-lotes/{id}")
    public ResponseEntity<Void> deleteEtapaLote(@PathVariable Long id) {
        log.debug("REST request to delete EtapaLote : {}", id);
        etapaLoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
