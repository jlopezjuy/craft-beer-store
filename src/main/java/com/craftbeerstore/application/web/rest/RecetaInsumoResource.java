package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.RecetaInsumo}.
 */
@RestController
@RequestMapping("/api")
public class RecetaInsumoResource {

    private final Logger log = LoggerFactory.getLogger(RecetaInsumoResource.class);

    private static final String ENTITY_NAME = "recetaInsumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecetaInsumoService recetaInsumoService;

    public RecetaInsumoResource(RecetaInsumoService recetaInsumoService) {
        this.recetaInsumoService = recetaInsumoService;
    }

    /**
     * {@code POST  /receta-insumos} : Create a new recetaInsumo.
     *
     * @param recetaInsumoDTO the recetaInsumoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recetaInsumoDTO, or with status {@code 400 (Bad Request)} if the recetaInsumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receta-insumos")
    public ResponseEntity<RecetaInsumoDTO> createRecetaInsumo(@Valid @RequestBody RecetaInsumoDTO recetaInsumoDTO) throws URISyntaxException {
        log.debug("REST request to save RecetaInsumo : {}", recetaInsumoDTO);
        if (recetaInsumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recetaInsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecetaInsumoDTO result = recetaInsumoService.save(recetaInsumoDTO);
        return ResponseEntity.created(new URI("/api/receta-insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receta-insumos} : Updates an existing recetaInsumo.
     *
     * @param recetaInsumoDTO the recetaInsumoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recetaInsumoDTO,
     * or with status {@code 400 (Bad Request)} if the recetaInsumoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recetaInsumoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receta-insumos")
    public ResponseEntity<RecetaInsumoDTO> updateRecetaInsumo(@Valid @RequestBody RecetaInsumoDTO recetaInsumoDTO) throws URISyntaxException {
        log.debug("REST request to update RecetaInsumo : {}", recetaInsumoDTO);
        if (recetaInsumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecetaInsumoDTO result = recetaInsumoService.save(recetaInsumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recetaInsumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receta-insumos} : get all the recetaInsumos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recetaInsumos in body.
     */
    @GetMapping("/receta-insumos")
    public ResponseEntity<List<RecetaInsumoDTO>> getAllRecetaInsumos(Pageable pageable) {
        log.debug("REST request to get a page of RecetaInsumos");
        Page<RecetaInsumoDTO> page = recetaInsumoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /receta-insumos/:id} : get the "id" recetaInsumo.
     *
     * @param id the id of the recetaInsumoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recetaInsumoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receta-insumos/{id}")
    public ResponseEntity<RecetaInsumoDTO> getRecetaInsumo(@PathVariable Long id) {
        log.debug("REST request to get RecetaInsumo : {}", id);
        Optional<RecetaInsumoDTO> recetaInsumoDTO = recetaInsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recetaInsumoDTO);
    }

    /**
     * {@code DELETE  /receta-insumos/:id} : delete the "id" recetaInsumo.
     *
     * @param id the id of the recetaInsumoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receta-insumos/{id}")
    public ResponseEntity<Void> deleteRecetaInsumo(@PathVariable Long id) {
        log.debug("REST request to delete RecetaInsumo : {}", id);
        recetaInsumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
