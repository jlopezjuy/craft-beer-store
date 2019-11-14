package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.DetalleMovimientoService;
import com.craftbeerstore.application.service.dto.DetalleMovimientoDTO;
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
 * REST controller for managing DetalleMovimiento.
 */
@RestController
@RequestMapping("/api")
public class DetalleMovimientoResource {

  private final Logger log = LoggerFactory.getLogger(DetalleMovimientoResource.class);

  private static final String ENTITY_NAME = "detalleMovimiento";

  private final DetalleMovimientoService detalleMovimientoService;

  public DetalleMovimientoResource(DetalleMovimientoService detalleMovimientoService) {
    this.detalleMovimientoService = detalleMovimientoService;
  }

  /**
   * POST  /detalle-movimientos : Create a new detalleMovimiento.
   *
   * @param detalleMovimientoDTO the detalleMovimientoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new detalleMovimientoDTO, or with status 400 (Bad Request) if the detalleMovimiento has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/detalle-movimientos")
  public ResponseEntity<DetalleMovimientoDTO> createDetalleMovimiento(@Valid @RequestBody DetalleMovimientoDTO detalleMovimientoDTO) throws URISyntaxException {
    log.debug("REST request to save DetalleMovimiento : {}", detalleMovimientoDTO);
    if (detalleMovimientoDTO.getId() != null) {
      throw new BadRequestAlertException("A new detalleMovimiento cannot already have an ID", ENTITY_NAME, "idexists");
    }
    DetalleMovimientoDTO result = detalleMovimientoService.save(detalleMovimientoDTO);
    return ResponseEntity.created(new URI("/api/detalle-movimientos/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * POST  /detalle-movimientos : Create a new detalleMovimiento.
   *
   * @param detalleMovimientoDTO the detalleMovimientoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new detalleMovimientoDTO, or with status 400 (Bad Request) if the detalleMovimiento has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/detalle-movimientos/list")
  public ResponseEntity<List<DetalleMovimientoDTO>> createDetalleMovimientoList(@Valid @RequestBody List<DetalleMovimientoDTO> detalleMovimientoDTO) throws URISyntaxException {
    log.debug("REST request to save DetalleMovimiento : {}", detalleMovimientoDTO);
    if (detalleMovimientoDTO.size() < 1) {
      throw new BadRequestAlertException("A new detalleMovimiento list cannot save", ENTITY_NAME, "listempty");
    }
    List<DetalleMovimientoDTO> result = detalleMovimientoService.save(detalleMovimientoDTO);
    return ResponseEntity.created(new URI("/api/detalle-movimientos/list"))
      .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,"Ok"))
      .body(result);
  }

  /**
   * PUT  /detalle-movimientos : Updates an existing detalleMovimiento.
   *
   * @param detalleMovimientoDTO the detalleMovimientoDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated detalleMovimientoDTO,
   * or with status 400 (Bad Request) if the detalleMovimientoDTO is not valid,
   * or with status 500 (Internal Server Error) if the detalleMovimientoDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/detalle-movimientos")
  public ResponseEntity<DetalleMovimientoDTO> updateDetalleMovimiento(@Valid @RequestBody DetalleMovimientoDTO detalleMovimientoDTO) throws URISyntaxException {
    log.debug("REST request to update DetalleMovimiento : {}", detalleMovimientoDTO);
    if (detalleMovimientoDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    DetalleMovimientoDTO result = detalleMovimientoService.save(detalleMovimientoDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleMovimientoDTO.getId().toString()))
      .body(result);
  }

  /**
   * GET  /detalle-movimientos : get all the detalleMovimientos.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of detalleMovimientos in body
   */
  @GetMapping("/detalle-movimientos")
  public ResponseEntity<List<DetalleMovimientoDTO>> getAllDetalleMovimientos(Pageable pageable) {
    log.debug("REST request to get a page of DetalleMovimientos");
    Page<DetalleMovimientoDTO> page = detalleMovimientoService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-movimientos");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/detalle-movimientos/movimiento/{movimientoId}")
  public ResponseEntity<List<DetalleMovimientoDTO>> getAllDetalleMovimientosByMovimiento(Pageable pageable, @PathVariable Long movimientoId) {
    log.debug("REST request to get a page of DetalleMovimientos by Movimiento");
    Page<DetalleMovimientoDTO> page = detalleMovimientoService.findAllByMovimiento(pageable, movimientoId);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-movimientos/movimiento/{movimientoId}");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * GET  /detalle-movimientos/:id : get the "id" detalleMovimiento.
   *
   * @param id the id of the detalleMovimientoDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the detalleMovimientoDTO, or with status 404 (Not Found)
   */
  @GetMapping("/detalle-movimientos/{id}")
  public ResponseEntity<DetalleMovimientoDTO> getDetalleMovimiento(@PathVariable Long id) {
    log.debug("REST request to get DetalleMovimiento : {}", id);
    Optional<DetalleMovimientoDTO> detalleMovimientoDTO = detalleMovimientoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(detalleMovimientoDTO);
  }

  /**
   * DELETE  /detalle-movimientos/:id : delete the "id" detalleMovimiento.
   *
   * @param id the id of the detalleMovimientoDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/detalle-movimientos/{id}")
  public ResponseEntity<Void> deleteDetalleMovimiento(@PathVariable Long id) {
    log.debug("REST request to delete DetalleMovimiento : {}", id);
    detalleMovimientoService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }

}
