package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
import com.craftbeerstore.application.service.MedicionLoteService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.MedicionLoteDTO;
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
 * REST controller for managing MedicionLote.
 */
@RestController
@RequestMapping("/api")
public class MedicionLoteResource {

    private final Logger log = LoggerFactory.getLogger(MedicionLoteResource.class);

    private static final String ENTITY_NAME = "medicionLote";

    private final MedicionLoteService medicionLoteService;

    public MedicionLoteResource(MedicionLoteService medicionLoteService) {
        this.medicionLoteService = medicionLoteService;
    }

    /**
     * POST  /medicion-lotes : Create a new medicionLote.
     *
     * @param medicionLoteDTO the medicionLoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicionLoteDTO, or with status 400 (Bad Request) if the medicionLote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicion-lotes")
    public ResponseEntity<MedicionLoteDTO> createMedicionLote(@RequestBody MedicionLoteDTO medicionLoteDTO) throws URISyntaxException {
        log.debug("REST request to save MedicionLote : {}", medicionLoteDTO);
        if (medicionLoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicionLote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicionLoteDTO result = medicionLoteService.save(medicionLoteDTO);
        return ResponseEntity.created(new URI("/api/medicion-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicion-lotes : Updates an existing medicionLote.
     *
     * @param medicionLoteDTO the medicionLoteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicionLoteDTO,
     * or with status 400 (Bad Request) if the medicionLoteDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicionLoteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicion-lotes")
    public ResponseEntity<MedicionLoteDTO> updateMedicionLote(@RequestBody MedicionLoteDTO medicionLoteDTO) throws URISyntaxException {
        log.debug("REST request to update MedicionLote : {}", medicionLoteDTO);
        if (medicionLoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicionLoteDTO result = medicionLoteService.save(medicionLoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicionLoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicion-lotes : get all the medicionLotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicionLotes in body
     */
    @GetMapping("/medicion-lotes")
    public ResponseEntity<List<MedicionLoteDTO>> getAllMedicionLotes(Pageable pageable) {
        log.debug("REST request to get a page of MedicionLotes");
        Page<MedicionLoteDTO> page = medicionLoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medicion-lotes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

  /**
   * GET  /medicion-lotes : get all the medicionLotes.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of medicionLotes in body
   */
  @GetMapping("/medicion-lotes/lote/{loteId}")
  public ResponseEntity<List<MedicionLoteDTO>> getAllMedicionesLote(Pageable pageable, @PathVariable Long loteId) {
    log.debug("REST request to get a page of MedicionLotes");
    Page<MedicionLoteDTO> page = medicionLoteService.findAll(pageable, loteId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medicion-lotes");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET  /medicion-lotes : get all the medicionLotes.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of medicionLotes in body
   */
  @GetMapping("/medicion-lotes/lote/{loteId}/tipo/{tipoMedicion}")
  public ResponseEntity<List<MedicionLoteDTO>> getAllMedicionesLoteTipo(Pageable pageable, @PathVariable Long loteId, @PathVariable TipoMedicion tipoMedicion) {
    log.debug("REST request to get a page of MedicionLotes");
    Page<MedicionLoteDTO> page = medicionLoteService.findAll(pageable, loteId, tipoMedicion);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medicion-lotes");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

    /**
     * GET  /medicion-lotes/:id : get the "id" medicionLote.
     *
     * @param id the id of the medicionLoteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicionLoteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medicion-lotes/{id}")
    public ResponseEntity<MedicionLoteDTO> getMedicionLote(@PathVariable Long id) {
        log.debug("REST request to get MedicionLote : {}", id);
        Optional<MedicionLoteDTO> medicionLoteDTO = medicionLoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicionLoteDTO);
    }

    /**
     * DELETE  /medicion-lotes/:id : delete the "id" medicionLote.
     *
     * @param id the id of the medicionLoteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicion-lotes/{id}")
    public ResponseEntity<Void> deleteMedicionLote(@PathVariable Long id) {
        log.debug("REST request to delete MedicionLote : {}", id);
        medicionLoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
