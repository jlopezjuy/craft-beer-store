package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.PuntoDeVentaService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.PuntoDeVentaDTO;
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
 * REST controller for managing PuntoDeVenta.
 */
@RestController
@RequestMapping("/api")
public class PuntoDeVentaResource {

    private final Logger log = LoggerFactory.getLogger(PuntoDeVentaResource.class);

    private static final String ENTITY_NAME = "puntoDeVenta";

    private final PuntoDeVentaService puntoDeVentaService;

    public PuntoDeVentaResource(PuntoDeVentaService puntoDeVentaService) {
        this.puntoDeVentaService = puntoDeVentaService;
    }

    /**
     * POST  /punto-de-ventas : Create a new puntoDeVenta.
     *
     * @param puntoDeVentaDTO the puntoDeVentaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puntoDeVentaDTO, or with status 400 (Bad Request) if the puntoDeVenta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/punto-de-ventas")
    public ResponseEntity<PuntoDeVentaDTO> createPuntoDeVenta(@Valid @RequestBody PuntoDeVentaDTO puntoDeVentaDTO) throws URISyntaxException {
        log.debug("REST request to save PuntoDeVenta : {}", puntoDeVentaDTO);
        if (puntoDeVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new puntoDeVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuntoDeVentaDTO result = puntoDeVentaService.save(puntoDeVentaDTO);
        return ResponseEntity.created(new URI("/api/punto-de-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /punto-de-ventas : Updates an existing puntoDeVenta.
     *
     * @param puntoDeVentaDTO the puntoDeVentaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puntoDeVentaDTO,
     * or with status 400 (Bad Request) if the puntoDeVentaDTO is not valid,
     * or with status 500 (Internal Server Error) if the puntoDeVentaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/punto-de-ventas")
    public ResponseEntity<PuntoDeVentaDTO> updatePuntoDeVenta(@Valid @RequestBody PuntoDeVentaDTO puntoDeVentaDTO) throws URISyntaxException {
        log.debug("REST request to update PuntoDeVenta : {}", puntoDeVentaDTO);
        if (puntoDeVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuntoDeVentaDTO result = puntoDeVentaService.save(puntoDeVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puntoDeVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /punto-de-ventas : get all the puntoDeVentas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of puntoDeVentas in body
     */
    @GetMapping("/punto-de-ventas")
    public ResponseEntity<List<PuntoDeVentaDTO>> getAllPuntoDeVentas(Pageable pageable) {
        log.debug("REST request to get a page of PuntoDeVentas");
        Page<PuntoDeVentaDTO> page = puntoDeVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/punto-de-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /punto-de-ventas/cliente/{clienteId} : get all the puntoDeVentas by Cliente
     * @param pageable
     * @param clienteId the clienteId of the PuntoDeVenta
     * @return the ResponseEntity with status 200(OK) and the list of Puntos de Ventas in body
     */
    @GetMapping("/punto-de-ventas/cliente/{clienteId}")
    public ResponseEntity<List<PuntoDeVentaDTO>> getAllPuntoDeVentasByCliente(Pageable pageable, @PathVariable Long clienteId) {
        log.debug("REST request to get a page of PuntoDeVentas");
        Page<PuntoDeVentaDTO> page = puntoDeVentaService.findAllByCliente(pageable, clienteId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/punto-de-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /punto-de-ventas/:id : get the "id" puntoDeVenta.
     *
     * @param id the id of the puntoDeVentaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puntoDeVentaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/punto-de-ventas/{id}")
    public ResponseEntity<PuntoDeVentaDTO> getPuntoDeVenta(@PathVariable Long id) {
        log.debug("REST request to get PuntoDeVenta : {}", id);
        Optional<PuntoDeVentaDTO> puntoDeVentaDTO = puntoDeVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(puntoDeVentaDTO);
    }

    /**
     * DELETE  /punto-de-ventas/:id : delete the "id" puntoDeVenta.
     *
     * @param id the id of the puntoDeVentaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/punto-de-ventas/{id}")
    public ResponseEntity<Void> deletePuntoDeVenta(@PathVariable Long id) {
        log.debug("REST request to delete PuntoDeVenta : {}", id);
        puntoDeVentaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/punto-de-ventas?query=:query : search for the puntoDeVenta corresponding
     * to the query.
     *
     * @param query the query of the puntoDeVenta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/punto-de-ventas")
    public ResponseEntity<List<PuntoDeVentaDTO>> searchPuntoDeVentas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PuntoDeVentas for query {}", query);
        Page<PuntoDeVentaDTO> page = puntoDeVentaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/punto-de-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
