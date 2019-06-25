package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.EquipamientoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;
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

/**
 * REST controller for managing Equipamiento.
 */
@RestController
@RequestMapping("/api")
public class EquipamientoResource {

    private final Logger log = LoggerFactory.getLogger(EquipamientoResource.class);

    private static final String ENTITY_NAME = "equipamiento";

    private final EquipamientoService equipamientoService;

    public EquipamientoResource(EquipamientoService equipamientoService) {
        this.equipamientoService = equipamientoService;
    }

    /**
     * POST  /equipamientos : Create a new equipamiento.
     *
     * @param equipamientoDTO the equipamientoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipamientoDTO, or with status 400 (Bad Request) if the equipamiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipamientos")
    public ResponseEntity<EquipamientoDTO> createEquipamiento(@Valid @RequestBody EquipamientoDTO equipamientoDTO) throws URISyntaxException {
        log.debug("REST request to save Equipamiento : {}", equipamientoDTO);
        if (equipamientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipamientoDTO result = equipamientoService.save(equipamientoDTO);
        return ResponseEntity.created(new URI("/api/equipamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipamientos : Updates an existing equipamiento.
     *
     * @param equipamientoDTO the equipamientoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipamientoDTO,
     * or with status 400 (Bad Request) if the equipamientoDTO is not valid,
     * or with status 500 (Internal Server Error) if the equipamientoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipamientos")
    public ResponseEntity<EquipamientoDTO> updateEquipamiento(@Valid @RequestBody EquipamientoDTO equipamientoDTO) throws URISyntaxException {
        log.debug("REST request to update Equipamiento : {}", equipamientoDTO);
        if (equipamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipamientoDTO result = equipamientoService.save(equipamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipamientos : get all the equipamientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of equipamientos in body
     */
    @GetMapping("/equipamientos")
    public ResponseEntity<List<EquipamientoDTO>> getAllEquipamientos(Pageable pageable) {
        log.debug("REST request to get a page of Equipamientos");
        Page<EquipamientoDTO> page = equipamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/equipamientos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /equipamientos : get all the equipamientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of equipamientos in body
     */
    @GetMapping("/equipamientos/empresa/{empresaId}")
    public ResponseEntity<List<EquipamientoDTO>> getAllEquipamientosByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Equipamientos");
        Page<EquipamientoDTO> page = equipamientoService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/equipamientos/empresa/{empresaId}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /equipamientos/:id : get the "id" equipamiento.
     *
     * @param id the id of the equipamientoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipamientoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/equipamientos/{id}")
    public ResponseEntity<EquipamientoDTO> getEquipamiento(@PathVariable Long id) {
        log.debug("REST request to get Equipamiento : {}", id);
        Optional<EquipamientoDTO> equipamientoDTO = equipamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipamientoDTO);
    }

    /**
     * DELETE  /equipamientos/:id : delete the "id" equipamiento.
     *
     * @param id the id of the equipamientoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipamientos/{id}")
    public ResponseEntity<Void> deleteEquipamiento(@PathVariable Long id) {
        log.debug("REST request to delete Equipamiento : {}", id);
        equipamientoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
