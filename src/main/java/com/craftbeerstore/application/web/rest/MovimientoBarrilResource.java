package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.MovimientoBarrilService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.MovimientoBarrilDTO;
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
 * REST controller for managing MovimientoBarril.
 */
@RestController
@RequestMapping("/api")
public class MovimientoBarrilResource {

    private final Logger log = LoggerFactory.getLogger(MovimientoBarrilResource.class);

    private static final String ENTITY_NAME = "movimientoBarril";

    private final MovimientoBarrilService movimientoBarrilService;

    public MovimientoBarrilResource(MovimientoBarrilService movimientoBarrilService) {
        this.movimientoBarrilService = movimientoBarrilService;
    }

    /**
     * POST  /movimiento-barrils : Create a new movimientoBarril.
     *
     * @param movimientoBarrilDTO the movimientoBarrilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movimientoBarrilDTO, or with status 400 (Bad Request) if the movimientoBarril has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movimiento-barrils")
    public ResponseEntity<MovimientoBarrilDTO> createMovimientoBarril(@RequestBody MovimientoBarrilDTO movimientoBarrilDTO) throws URISyntaxException {
        log.debug("REST request to save MovimientoBarril : {}", movimientoBarrilDTO);
        if (movimientoBarrilDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimientoBarril cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovimientoBarrilDTO result = movimientoBarrilService.save(movimientoBarrilDTO);
        return ResponseEntity.created(new URI("/api/movimiento-barrils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movimiento-barrils : Updates an existing movimientoBarril.
     *
     * @param movimientoBarrilDTO the movimientoBarrilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movimientoBarrilDTO,
     * or with status 400 (Bad Request) if the movimientoBarrilDTO is not valid,
     * or with status 500 (Internal Server Error) if the movimientoBarrilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movimiento-barrils")
    public ResponseEntity<MovimientoBarrilDTO> updateMovimientoBarril(@RequestBody MovimientoBarrilDTO movimientoBarrilDTO) throws URISyntaxException {
        log.debug("REST request to update MovimientoBarril : {}", movimientoBarrilDTO);
        if (movimientoBarrilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovimientoBarrilDTO result = movimientoBarrilService.save(movimientoBarrilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movimientoBarrilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movimiento-barrils : get all the movimientoBarrils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movimientoBarrils in body
     */
    @GetMapping("/movimiento-barrils")
    public ResponseEntity<List<MovimientoBarrilDTO>> getAllMovimientoBarrils(Pageable pageable) {
        log.debug("REST request to get a page of MovimientoBarrils");
        Page<MovimientoBarrilDTO> page = movimientoBarrilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movimiento-barrils");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

  /**
   * GET  /movimiento-barrils : get all the movimientoBarrils.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of movimientoBarrils in body
   */
  @GetMapping("/movimiento-barrils/barril/{barrilId}")
  public ResponseEntity<List<MovimientoBarrilDTO>> getAllMovimientoBarrils(Pageable pageable, @PathVariable Long barrilId) {
    log.debug("REST request to get a page of MovimientoBarrils");
    Page<MovimientoBarrilDTO> page = movimientoBarrilService.findAll(pageable, barrilId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movimiento-barrils/barril/{barrilId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

    /**
     * GET  /movimiento-barrils/:id : get the "id" movimientoBarril.
     *
     * @param id the id of the movimientoBarrilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movimientoBarrilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movimiento-barrils/{id}")
    public ResponseEntity<MovimientoBarrilDTO> getMovimientoBarril(@PathVariable Long id) {
        log.debug("REST request to get MovimientoBarril : {}", id);
        Optional<MovimientoBarrilDTO> movimientoBarrilDTO = movimientoBarrilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimientoBarrilDTO);
    }

    /**
     * DELETE  /movimiento-barrils/:id : delete the "id" movimientoBarril.
     *
     * @param id the id of the movimientoBarrilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movimiento-barrils/{id}")
    public ResponseEntity<Void> deleteMovimientoBarril(@PathVariable Long id) {
        log.debug("REST request to delete MovimientoBarril : {}", id);
        movimientoBarrilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
