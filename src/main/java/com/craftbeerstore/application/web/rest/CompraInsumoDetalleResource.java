package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.CompraInsumoDetalleService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.craftbeerstore.application.domain.CompraInsumoDetalle}.
 */
@RestController
@RequestMapping("/api")
public class CompraInsumoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoDetalleResource.class);

    private static final String ENTITY_NAME = "compraInsumoDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompraInsumoDetalleService compraInsumoDetalleService;

    public CompraInsumoDetalleResource(CompraInsumoDetalleService compraInsumoDetalleService) {
        this.compraInsumoDetalleService = compraInsumoDetalleService;
    }

    /**
     * {@code POST  /compra-insumo-detalles} : Create a new compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the compraInsumoDetalleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compraInsumoDetalleDTO, or with status {@code 400 (Bad Request)} if the compraInsumoDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compra-insumo-detalles")
    public ResponseEntity<CompraInsumoDetalleDTO> createCompraInsumoDetalle(@RequestBody CompraInsumoDetalleDTO compraInsumoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save CompraInsumoDetalle : {}", compraInsumoDetalleDTO);
        if (compraInsumoDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new compraInsumoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraInsumoDetalleDTO result = compraInsumoDetalleService.save(compraInsumoDetalleDTO);
        return ResponseEntity.created(new URI("/api/compra-insumo-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compra-insumo-detalles} : Updates an existing compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the compraInsumoDetalleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compraInsumoDetalleDTO,
     * or with status {@code 400 (Bad Request)} if the compraInsumoDetalleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compraInsumoDetalleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compra-insumo-detalles")
    public ResponseEntity<CompraInsumoDetalleDTO> updateCompraInsumoDetalle(@RequestBody CompraInsumoDetalleDTO compraInsumoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to update CompraInsumoDetalle : {}", compraInsumoDetalleDTO);
        if (compraInsumoDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompraInsumoDetalleDTO result = compraInsumoDetalleService.save(compraInsumoDetalleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compraInsumoDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compra-insumo-detalles} : get all the compraInsumoDetalles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compraInsumoDetalles in body.
     */
    @GetMapping("/compra-insumo-detalles")
    public ResponseEntity<List<CompraInsumoDetalleDTO>> getAllCompraInsumoDetalles(Pageable pageable) {
        log.debug("REST request to get a page of CompraInsumoDetalles");
        Page<CompraInsumoDetalleDTO> page = compraInsumoDetalleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compra-insumo-detalles/:id} : get the "id" compraInsumoDetalle.
     *
     * @param id the id of the compraInsumoDetalleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compraInsumoDetalleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compra-insumo-detalles/{id}")
    public ResponseEntity<CompraInsumoDetalleDTO> getCompraInsumoDetalle(@PathVariable Long id) {
        log.debug("REST request to get CompraInsumoDetalle : {}", id);
        Optional<CompraInsumoDetalleDTO> compraInsumoDetalleDTO = compraInsumoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compraInsumoDetalleDTO);
    }

    /**
     * {@code DELETE  /compra-insumo-detalles/:id} : delete the "id" compraInsumoDetalle.
     *
     * @param id the id of the compraInsumoDetalleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compra-insumo-detalles/{id}")
    public ResponseEntity<Void> deleteCompraInsumoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete CompraInsumoDetalle : {}", id);
        compraInsumoDetalleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
