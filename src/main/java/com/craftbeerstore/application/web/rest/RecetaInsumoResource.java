package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
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
 * REST controller for managing RecetaInsumo.
 */
@RestController
@RequestMapping("/api")
public class RecetaInsumoResource {

  private final Logger log = LoggerFactory.getLogger(RecetaInsumoResource.class);

  private static final String ENTITY_NAME = "recetaInsumo";

  private final RecetaInsumoService recetaInsumoService;

  public RecetaInsumoResource(RecetaInsumoService recetaInsumoService) {
    this.recetaInsumoService = recetaInsumoService;
  }

  /**
   * POST  /receta-insumos : Create a new recetaInsumo.
   *
   * @param recetaInsumoDTO the recetaInsumoDTO to create
   * @return the ResponseEntity with status 201 (Created) and with body the new recetaInsumoDTO, or with status 400 (Bad Request) if the recetaInsumo has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PostMapping("/receta-insumos")
  public ResponseEntity<RecetaInsumoDTO> createRecetaInsumo(@Valid @RequestBody RecetaInsumoDTO recetaInsumoDTO) throws URISyntaxException {
    log.debug("REST request to save RecetaInsumo : {}", recetaInsumoDTO);
    if (recetaInsumoDTO.getId() != null) {
      throw new BadRequestAlertException("A new recetaInsumo cannot already have an ID", ENTITY_NAME, "idexists");
    }
    RecetaInsumoDTO result = recetaInsumoService.save(recetaInsumoDTO);
    return ResponseEntity.created(new URI("/api/receta-insumos/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * PUT  /receta-insumos : Updates an existing recetaInsumo.
   *
   * @param recetaInsumoDTO the recetaInsumoDTO to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated recetaInsumoDTO,
   * or with status 400 (Bad Request) if the recetaInsumoDTO is not valid,
   * or with status 500 (Internal Server Error) if the recetaInsumoDTO couldn't be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping("/receta-insumos")
  public ResponseEntity<RecetaInsumoDTO> updateRecetaInsumo(@Valid @RequestBody RecetaInsumoDTO recetaInsumoDTO) throws URISyntaxException {
    log.debug("REST request to update RecetaInsumo : {}", recetaInsumoDTO);
    if (recetaInsumoDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    RecetaInsumoDTO result = recetaInsumoService.save(recetaInsumoDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recetaInsumoDTO.getId().toString()))
      .body(result);
  }

  /**
   * GET  /receta-insumos : get all the recetaInsumos.
   *
   * @param pageable the pagination information
   * @return the ResponseEntity with status 200 (OK) and the list of recetaInsumos in body
   */
  @GetMapping("/receta-insumos")
  public ResponseEntity<List<RecetaInsumoDTO>> getAllRecetaInsumos(Pageable pageable) {
    log.debug("REST request to get a page of RecetaInsumos");
    Page<RecetaInsumoDTO> page = recetaInsumoService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receta-insumos");
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * @param recetaId
   * @param insumoId
   * @param tipoInsumo
   * @return
   */
  @GetMapping("/receta-insumos/receta-insumo-tipo/{recetaId}/{tipoInsumo}")
  public ResponseEntity<List<RecetaInsumoDTO>> getAllRecetaInsumosTipo(@PathVariable Long recetaId,
                                                                       @PathVariable TipoInsumo tipoInsumo) {
    log.debug("REST request to get a page of RecetaInsumos");
    List<RecetaInsumoDTO> page = recetaInsumoService.findAllByRecetaImsumo(recetaId, tipoInsumo);
    return ResponseEntity.ok().body(page);
  }

  @GetMapping("/receta-insumos/receta-insumo-tipo/{recetaId}")
  public ResponseEntity<List<RecetaInsumoDTO>> getAllRecetaInsumosTipoNotIn(@PathVariable Long recetaId,
                                                                            @RequestParam List<TipoInsumo> tipoInsumos) {
    log.debug("REST request to get a page of RecetaInsumos");
    List<RecetaInsumoDTO> page = recetaInsumoService.findAllByRecetaImsumo(recetaId, tipoInsumos);
    return ResponseEntity.ok().body(page);
  }

  /**
   * GET  /receta-insumos/:id : get the "id" recetaInsumo.
   *
   * @param id the id of the recetaInsumoDTO to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body the recetaInsumoDTO, or with status 404 (Not Found)
   */
  @GetMapping("/receta-insumos/{id}")
  public ResponseEntity<RecetaInsumoDTO> getRecetaInsumo(@PathVariable Long id) {
    log.debug("REST request to get RecetaInsumo : {}", id);
    Optional<RecetaInsumoDTO> recetaInsumoDTO = recetaInsumoService.findOne(id);
    return ResponseUtil.wrapOrNotFound(recetaInsumoDTO);
  }

  /**
   * DELETE  /receta-insumos/:id : delete the "id" recetaInsumo.
   *
   * @param id the id of the recetaInsumoDTO to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/receta-insumos/{id}")
  public ResponseEntity<Void> deleteRecetaInsumo(@PathVariable Long id) {
    log.debug("REST request to delete RecetaInsumo : {}", id);
    recetaInsumoService.delete(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }

  @DeleteMapping("/receta-insumos/delete")
  public ResponseEntity<Void> deleteRecetaInsumo(@RequestParam("insumos") List<String> list) {
    log.debug("REST request to delete RecetaInsumo : {}", list);
    recetaInsumoService.delete(list);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, "")).build();
  }

}
