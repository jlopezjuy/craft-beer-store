package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.RecetaService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.RecetaDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Receta}.
 */
@RestController
@RequestMapping("/api")
public class RecetaResource {

    private final Logger log = LoggerFactory.getLogger(RecetaResource.class);

    private static final String ENTITY_NAME = "receta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecetaService recetaService;

    public RecetaResource(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    /**
     * {@code POST  /recetas} : Create a new receta.
     *
     * @param recetaDTO the recetaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recetaDTO, or with status {@code 400 (Bad Request)} if the receta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recetas")
    public ResponseEntity<RecetaDTO> createReceta(@RequestBody RecetaDTO recetaDTO) throws URISyntaxException {
        log.debug("REST request to save Receta : {}", recetaDTO);
        if (recetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new receta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecetaDTO result = recetaService.save(recetaDTO);
        return ResponseEntity.created(new URI("/api/recetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recetas} : Updates an existing receta.
     *
     * @param recetaDTO the recetaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recetaDTO,
     * or with status {@code 400 (Bad Request)} if the recetaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recetaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recetas")
    public ResponseEntity<RecetaDTO> updateReceta(@RequestBody RecetaDTO recetaDTO) throws URISyntaxException {
        log.debug("REST request to update Receta : {}", recetaDTO);
        if (recetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecetaDTO result = recetaService.save(recetaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recetas} : get all the recetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recetas in body.
     */
    @GetMapping("/recetas")
    public ResponseEntity<List<RecetaDTO>> getAllRecetas(Pageable pageable) {
        log.debug("REST request to get a page of Recetas");
        Page<RecetaDTO> page = recetaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recetas/producto/{productoId}")
    public ResponseEntity<List<RecetaDTO>> getAllRecetasByProducto(Pageable pageable, @PathVariable Long productoId) {
        log.debug("REST request to get a page of Recetas");
        Page<RecetaDTO> page = recetaService.findAll(pageable, productoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recetas/producto/list/{productoId}")
    public ResponseEntity<List<RecetaDTO>> getAllRecetasByProductoList(Pageable pageable, @PathVariable Long productoId) {
        log.debug("REST request to get a page of Recetas");
        List<RecetaDTO> page = recetaService.findAllByProducto(productoId);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /recetas/:id} : get the "id" receta.
     *
     * @param id the id of the recetaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recetaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recetas/{id}")
    public ResponseEntity<RecetaDTO> getReceta(@PathVariable Long id) {
        log.debug("REST request to get Receta : {}", id);
        Optional<RecetaDTO> recetaDTO = recetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recetaDTO);
    }

    /**
     * {@code DELETE  /recetas/:id} : delete the "id" receta.
     *
     * @param id the id of the recetaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recetas/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        log.debug("REST request to delete Receta : {}", id);
        recetaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
