package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.CompraInsumoDetalleService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;
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
 * REST controller for managing CompraInsumoDetalle.
 */
@RestController
@RequestMapping("/api")
public class CompraInsumoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoDetalleResource.class);

    private static final String ENTITY_NAME = "compraInsumoDetalle";

    private final CompraInsumoDetalleService compraInsumoDetalleService;

    public CompraInsumoDetalleResource(CompraInsumoDetalleService compraInsumoDetalleService) {
        this.compraInsumoDetalleService = compraInsumoDetalleService;
    }

    /**
     * POST  /compra-insumo-detalles : Create a new compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the compraInsumoDetalleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compraInsumoDetalleDTO, or with status 400 (Bad Request) if the compraInsumoDetalle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compra-insumo-detalles")
    public ResponseEntity<CompraInsumoDetalleDTO> createCompraInsumoDetalle(@RequestBody CompraInsumoDetalleDTO compraInsumoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save CompraInsumoDetalle : {}", compraInsumoDetalleDTO);
        if (compraInsumoDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new compraInsumoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraInsumoDetalleDTO result = compraInsumoDetalleService.save(compraInsumoDetalleDTO);
        return ResponseEntity.created(new URI("/api/compra-insumo-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /compra-insumo-detalles/list : Create a new compraInsumoDetalle.
     * @param comprasInsumoDetalleDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/compra-insumo-detalles/list")
    public ResponseEntity<List<CompraInsumoDetalleDTO>> createComprasInsumoDetalle(@RequestBody List<CompraInsumoDetalleDTO> comprasInsumoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save CompraInsumoDetalle : {}", comprasInsumoDetalleDTO);
//        if (compraInsumoDetalleDTO.getId() != null) {
//            throw new BadRequestAlertException("A new compraInsumoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        List<CompraInsumoDetalleDTO> list = compraInsumoDetalleService.save(comprasInsumoDetalleDTO);
        return ResponseEntity.created(new URI("/api/compra-insumo-detalles/list"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "Ok"))
            .body(list);
    }

    /**
     * PUT  /compra-insumo-detalles : Updates an existing compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the compraInsumoDetalleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compraInsumoDetalleDTO,
     * or with status 400 (Bad Request) if the compraInsumoDetalleDTO is not valid,
     * or with status 500 (Internal Server Error) if the compraInsumoDetalleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compra-insumo-detalles")
    public ResponseEntity<CompraInsumoDetalleDTO> updateCompraInsumoDetalle(@RequestBody CompraInsumoDetalleDTO compraInsumoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to update CompraInsumoDetalle : {}", compraInsumoDetalleDTO);
        if (compraInsumoDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompraInsumoDetalleDTO result = compraInsumoDetalleService.save(compraInsumoDetalleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compraInsumoDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compra-insumo-detalles : get all the compraInsumoDetalles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compraInsumoDetalles in body
     */
    @GetMapping("/compra-insumo-detalles")
    public ResponseEntity<List<CompraInsumoDetalleDTO>> getAllCompraInsumoDetalles(Pageable pageable) {
        log.debug("REST request to get a page of CompraInsumoDetalles");
        Page<CompraInsumoDetalleDTO> page = compraInsumoDetalleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compra-insumo-detalles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /compra-insumo-detalles/:id : get the "id" compraInsumoDetalle.
     *
     * @param id the id of the compraInsumoDetalleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compraInsumoDetalleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compra-insumo-detalles/{id}")
    public ResponseEntity<CompraInsumoDetalleDTO> getCompraInsumoDetalle(@PathVariable Long id) {
        log.debug("REST request to get CompraInsumoDetalle : {}", id);
        Optional<CompraInsumoDetalleDTO> compraInsumoDetalleDTO = compraInsumoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compraInsumoDetalleDTO);
    }

    /**
     * DELETE  /compra-insumo-detalles/:id : delete the "id" compraInsumoDetalle.
     *
     * @param id the id of the compraInsumoDetalleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compra-insumo-detalles/{id}")
    public ResponseEntity<Void> deleteCompraInsumoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete CompraInsumoDetalle : {}", id);
        compraInsumoDetalleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /compra-insumo-detalles/compra-insumo/{compraInsumoId} : get all the compraInsumoDetalles.
     *
     * @param compraInsumoId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compraInsumoDetalles in body
     */
    @GetMapping("/compra-insumo-detalles/compra-insumo/{compraInsumoId}")
    public ResponseEntity<List<CompraInsumoDetalleDTO>> getAllCompraInsumoDetalles(@PathVariable Long compraInsumoId) {
        log.debug("REST request to get a page of CompraInsumoDetalles");
        List<CompraInsumoDetalleDTO> page = compraInsumoDetalleService.findAll(compraInsumoId);
        return ResponseEntity.ok().body(page);
    }
}
