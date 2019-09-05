package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.BarrilService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.BarrilDTO;
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
 * REST controller for managing Barril.
 */
@RestController
@RequestMapping("/api")
public class BarrilResource {

    private final Logger log = LoggerFactory.getLogger(BarrilResource.class);

    private static final String ENTITY_NAME = "barril";

    private final BarrilService barrilService;

    public BarrilResource(BarrilService barrilService) {
        this.barrilService = barrilService;
    }

    /**
     * POST  /barrils : Create a new barril.
     *
     * @param barrilDTO the barrilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new barrilDTO, or with status 400 (Bad Request) if the barril has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/barrils")
    public ResponseEntity<BarrilDTO> createBarril(@RequestBody BarrilDTO barrilDTO) throws URISyntaxException {
        log.debug("REST request to save Barril : {}", barrilDTO);
        if (barrilDTO.getId() != null) {
            throw new BadRequestAlertException("A new barril cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarrilDTO result = barrilService.save(barrilDTO);
        return ResponseEntity.created(new URI("/api/barrils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /barrils : Updates an existing barril.
     *
     * @param barrilDTO the barrilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated barrilDTO,
     * or with status 400 (Bad Request) if the barrilDTO is not valid,
     * or with status 500 (Internal Server Error) if the barrilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/barrils")
    public ResponseEntity<BarrilDTO> updateBarril(@RequestBody BarrilDTO barrilDTO) throws URISyntaxException {
        log.debug("REST request to update Barril : {}", barrilDTO);
        if (barrilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarrilDTO result = barrilService.save(barrilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, barrilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /barrils : get all the barrils.
     *
     * @param pageable the pagination information
     * @param empresaId the empresaId information
     * @return the ResponseEntity with status 200 (OK) and the list of barrils in body
     */
    @GetMapping("/barrils/empresa/{empresaId}")
    public ResponseEntity<List<BarrilDTO>> getAllBarrilesByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Barrils");
        Page<BarrilDTO> page = barrilService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/barrils/empresa");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

  /**
   * GET  /barrils : get all the barrils.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of barrils in body
   */
  @GetMapping("/barrils")
  public ResponseEntity<List<BarrilDTO>> getAllBarrils(Pageable pageable) {
    log.debug("REST request to get a page of Barrils");
    Page<BarrilDTO> page = barrilService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/barrils");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

    /**
     * GET  /barrils/:id : get the "id" barril.
     *
     * @param id the id of the barrilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the barrilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/barrils/{id}")
    public ResponseEntity<BarrilDTO> getBarril(@PathVariable Long id) {
        log.debug("REST request to get Barril : {}", id);
        Optional<BarrilDTO> barrilDTO = barrilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barrilDTO);
    }

    /**
     * DELETE  /barrils/:id : delete the "id" barril.
     *
     * @param id the id of the barrilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/barrils/{id}")
    public ResponseEntity<Void> deleteBarril(@PathVariable Long id) {
        log.debug("REST request to delete Barril : {}", id);
        barrilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}