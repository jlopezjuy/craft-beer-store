package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.EquipamientoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Equipamiento}.
 */
@RestController
@RequestMapping("/api")
public class EquipamientoResource {

    private final Logger log = LoggerFactory.getLogger(EquipamientoResource.class);

    private static final String ENTITY_NAME = "equipamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipamientoService equipamientoService;

    public EquipamientoResource(EquipamientoService equipamientoService) {
        this.equipamientoService = equipamientoService;
    }

    /**
     * {@code POST  /equipamientos} : Create a new equipamiento.
     *
     * @param equipamientoDTO the equipamientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipamientoDTO, or with status {@code 400 (Bad Request)} if the equipamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipamientos")
    public ResponseEntity<EquipamientoDTO> createEquipamiento(@Valid @RequestBody EquipamientoDTO equipamientoDTO) throws URISyntaxException {
        log.debug("REST request to save Equipamiento : {}", equipamientoDTO);
        if (equipamientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipamientoDTO result = equipamientoService.save(equipamientoDTO);
        return ResponseEntity.created(new URI("/api/equipamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipamientos} : Updates an existing equipamiento.
     *
     * @param equipamientoDTO the equipamientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipamientoDTO,
     * or with status {@code 400 (Bad Request)} if the equipamientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipamientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipamientos")
    public ResponseEntity<EquipamientoDTO> updateEquipamiento(@Valid @RequestBody EquipamientoDTO equipamientoDTO) throws URISyntaxException {
        log.debug("REST request to update Equipamiento : {}", equipamientoDTO);
        if (equipamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipamientoDTO result = equipamientoService.save(equipamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipamientos} : get all the equipamientos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipamientos in body.
     */
    @GetMapping("/equipamientos")
    public ResponseEntity<List<EquipamientoDTO>> getAllEquipamientos(Pageable pageable) {
        log.debug("REST request to get a page of Equipamientos");
        Page<EquipamientoDTO> page = equipamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipamientos/:id} : get the "id" equipamiento.
     *
     * @param id the id of the equipamientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipamientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipamientos/{id}")
    public ResponseEntity<EquipamientoDTO> getEquipamiento(@PathVariable Long id) {
        log.debug("REST request to get Equipamiento : {}", id);
        Optional<EquipamientoDTO> equipamientoDTO = equipamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipamientoDTO);
    }

    /**
     * {@code DELETE  /equipamientos/:id} : delete the "id" equipamiento.
     *
     * @param id the id of the equipamientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipamientos/{id}")
    public ResponseEntity<Void> deleteEquipamiento(@PathVariable Long id) {
        log.debug("REST request to delete Equipamiento : {}", id);
        equipamientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
