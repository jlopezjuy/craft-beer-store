package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.MovimientoTanqueService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.MovimientoTanqueDTO;
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
 * REST controller for managing MovimientoTanque.
 */
@RestController
@RequestMapping("/api")
public class MovimientoTanqueResource {

    private final Logger log = LoggerFactory.getLogger(MovimientoTanqueResource.class);

    private static final String ENTITY_NAME = "movimientoTanque";

    private final MovimientoTanqueService movimientoTanqueService;

    public MovimientoTanqueResource(MovimientoTanqueService movimientoTanqueService) {
        this.movimientoTanqueService = movimientoTanqueService;
    }

    /**
     * POST  /movimiento-tanques : Create a new movimientoTanque.
     *
     * @param movimientoTanqueDTO the movimientoTanqueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movimientoTanqueDTO, or with status 400 (Bad Request) if the movimientoTanque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movimiento-tanques")
    public ResponseEntity<MovimientoTanqueDTO> createMovimientoTanque(@RequestBody MovimientoTanqueDTO movimientoTanqueDTO) throws URISyntaxException {
        log.debug("REST request to save MovimientoTanque : {}", movimientoTanqueDTO);
        if (movimientoTanqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimientoTanque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovimientoTanqueDTO result = movimientoTanqueService.save(movimientoTanqueDTO);
        return ResponseEntity.created(new URI("/api/movimiento-tanques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movimiento-tanques : Updates an existing movimientoTanque.
     *
     * @param movimientoTanqueDTO the movimientoTanqueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movimientoTanqueDTO,
     * or with status 400 (Bad Request) if the movimientoTanqueDTO is not valid,
     * or with status 500 (Internal Server Error) if the movimientoTanqueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movimiento-tanques")
    public ResponseEntity<MovimientoTanqueDTO> updateMovimientoTanque(@RequestBody MovimientoTanqueDTO movimientoTanqueDTO) throws URISyntaxException {
        log.debug("REST request to update MovimientoTanque : {}", movimientoTanqueDTO);
        if (movimientoTanqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovimientoTanqueDTO result = movimientoTanqueService.save(movimientoTanqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movimientoTanqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movimiento-tanques : get all the movimientoTanques.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movimientoTanques in body
     */
    @GetMapping("/movimiento-tanques")
    public ResponseEntity<List<MovimientoTanqueDTO>> getAllMovimientoTanques(Pageable pageable) {
        log.debug("REST request to get a page of MovimientoTanques");
        Page<MovimientoTanqueDTO> page = movimientoTanqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movimiento-tanques");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

  /**
   * GET  /movimiento-tanques : get all the movimientoTanques.
   *
   * @param pageable the pagination information
   * @param tanqueId the id of tanque
   * @return the ResponseEntity with status 200 (OK) and the list of movimientoTanques in body
   */
  @GetMapping("/movimiento-tanques/tanque/{tanqueId}")
  public ResponseEntity<List<MovimientoTanqueDTO>> getAllMovimientoTanquesOfTanque(Pageable pageable, @PathVariable Long tanqueId) {
    log.debug("REST request to get a page of MovimientoTanques");
    Page<MovimientoTanqueDTO> page = movimientoTanqueService.findAll(pageable, tanqueId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movimiento-tanques/tanque/{tanqueId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

    /**
     * GET  /movimiento-tanques/:id : get the "id" movimientoTanque.
     *
     * @param id the id of the movimientoTanqueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movimientoTanqueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movimiento-tanques/{id}")
    public ResponseEntity<MovimientoTanqueDTO> getMovimientoTanque(@PathVariable Long id) {
        log.debug("REST request to get MovimientoTanque : {}", id);
        Optional<MovimientoTanqueDTO> movimientoTanqueDTO = movimientoTanqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimientoTanqueDTO);
    }

    /**
     * DELETE  /movimiento-tanques/:id : delete the "id" movimientoTanque.
     *
     * @param id the id of the movimientoTanqueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movimiento-tanques/{id}")
    public ResponseEntity<Void> deleteMovimientoTanque(@PathVariable Long id) {
        log.debug("REST request to delete MovimientoTanque : {}", id);
        movimientoTanqueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
