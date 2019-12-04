package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.MovimientosService;
import com.craftbeerstore.application.service.dto.MovimientoLitroDTO;
import com.craftbeerstore.application.service.dto.MovimientosProductoSemanaDTO;
import com.craftbeerstore.application.service.dto.MovimientosSemanaDTO;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.MovimientosDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.craftbeerstore.application.domain.Movimientos}.
 */
@RestController
@RequestMapping("/api")
public class MovimientosResource {

    private final Logger log = LoggerFactory.getLogger(MovimientosResource.class);

    private static final String ENTITY_NAME = "movimientos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimientosService movimientosService;

    public MovimientosResource(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    /**
     * {@code POST  /movimientos} : Create a new movimientos.
     *
     * @param movimientosDTO the movimientosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimientosDTO, or with status {@code 400 (Bad Request)} if the movimientos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movimientos")
    public ResponseEntity<MovimientosDTO> createMovimientos(@Valid @RequestBody MovimientosDTO movimientosDTO) throws URISyntaxException {
        log.debug("REST request to save Movimientos : {}", movimientosDTO);
        if (movimientosDTO.getId() != null) {
            throw new BadRequestAlertException("A new movimientos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovimientosDTO result = movimientosService.save(movimientosDTO);
        return ResponseEntity.created(new URI("/api/movimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movimientos} : Updates an existing movimientos.
     *
     * @param movimientosDTO the movimientosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimientosDTO,
     * or with status {@code 400 (Bad Request)} if the movimientosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimientosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movimientos")
    public ResponseEntity<MovimientosDTO> updateMovimientos(@Valid @RequestBody MovimientosDTO movimientosDTO) throws URISyntaxException {
        log.debug("REST request to update Movimientos : {}", movimientosDTO);
        if (movimientosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovimientosDTO result = movimientosService.save(movimientosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimientosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movimientos} : get all the movimientos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimientos in body.
     */
    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientosDTO>> getAllMovimientos(Pageable pageable) {
        log.debug("REST request to get a page of Movimientos");
        Page<MovimientosDTO> page = movimientosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /movimientos : get all the movimientos by Empresa
     *
     * @param pageable  the pagination information.
     * @param empresaId the id of empresa entity
     * @return the ResponseEntity eith status 200 (OK) and the list of movimientos by emprsa in body
     */
    @GetMapping("/movimientos/empresa/{empresaId}")
    public ResponseEntity<List<MovimientosDTO>> getAllMovimientosByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Movimientos by Empresa");
        Page<MovimientosDTO> page = movimientosService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movimientos/:id} : get the "id" movimientos.
     *
     * @param id the id of the movimientosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimientosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientosDTO> getMovimientos(@PathVariable Long id) {
        log.debug("REST request to get Movimientos : {}", id);
        Optional<MovimientosDTO> movimientosDTO = movimientosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movimientosDTO);
    }

    /**
     * {@code DELETE  /movimientos/:id} : delete the "id" movimientos.
     *
     * @param id the id of the movimientosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<Void> deleteMovimientos(@PathVariable Long id) {
        log.debug("REST request to delete Movimientos : {}", id);
        movimientosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * @param empresaId
     * @return
     */
    @GetMapping("/movimientos/semana/{empresaId}/{dias}")
    public ResponseEntity<List<MovimientosSemanaDTO>> getAllMovimientosSemanal(@PathVariable Long empresaId, @PathVariable String dias) {
        log.debug("REST request to get a page of Movimientos by Empresa");
        List<MovimientosSemanaDTO> list = movimientosService
            .findMovimientosSemana(empresaId, dias);
        return ResponseEntity.ok().body(list);
    }

    /**
     * @param empresaId
     * @return
     */
    @GetMapping("/movimientos/semana/venta/{empresaId}/{dias}")
    public ResponseEntity<List<MovimientosProductoSemanaDTO>> getAllMovimientosProductoSemanal(@PathVariable Long empresaId, @PathVariable String dias) {
        List<MovimientosProductoSemanaDTO> list = movimientosService.findMovimientoProductoSemana(empresaId, dias);
        return ResponseEntity.ok().body(list);
    }

    /**
     * @param empresaId
     * @param dias
     * @return
     */
    @GetMapping("/movimientos/semana/litros/{empresaId}/{dias}")
    public ResponseEntity<MovimientosProductoSemanaDTO> getAllMovimientosLitroSemanal(@PathVariable Long empresaId, @PathVariable String dias) {
        MovimientosProductoSemanaDTO movimiento = movimientosService.findLitrosSemana(empresaId, dias);
        return ResponseEntity.ok().body(movimiento);
    }

    /**
     * @param empresaId
     * @return
     */
    @GetMapping("/movimientos/semana/litros/{empresaId}")
    public ResponseEntity<List<MovimientoLitroDTO>> getMovimientosLitroSemanal(@PathVariable Long empresaId) {
        List<MovimientoLitroDTO> movimiento = movimientosService.findPeriodoLitrosSemana(empresaId);
        return ResponseEntity.ok().body(movimiento);
    }

    /**
     * @param empresaId
     * @return
     */
    @GetMapping("/movimientos/mes/litros/{empresaId}")
    public ResponseEntity<List<MovimientoLitroDTO>> getMovimientosLitroMes(@PathVariable Long empresaId) {
        List<MovimientoLitroDTO> movimiento = movimientosService.findPeriodoLitrosMes(empresaId);
        return ResponseEntity.ok().body(movimiento);
    }
}
