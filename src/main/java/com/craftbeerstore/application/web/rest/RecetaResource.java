package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.RecetaService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.RecetaDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Receta.
 */
@RestController
@RequestMapping("/api")
public class RecetaResource {

    private final Logger log = LoggerFactory.getLogger(RecetaResource.class);

    private static final String ENTITY_NAME = "receta";

    private final RecetaService recetaService;

    public RecetaResource(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    /**
     * POST  /recetas : Create a new receta.
     *
     * @param recetaDTO the recetaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recetaDTO, or with status 400 (Bad Request) if the receta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recetas")
    public ResponseEntity<RecetaDTO> createReceta(@RequestBody RecetaDTO recetaDTO) throws URISyntaxException {
        log.debug("REST request to save Receta : {}", recetaDTO);
        if (recetaDTO.getId() != null) {
            throw new BadRequestAlertException("A new receta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecetaDTO result = recetaService.save(recetaDTO);
        return ResponseEntity.created(new URI("/api/recetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recetas : Updates an existing receta.
     *
     * @param recetaDTO the recetaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recetaDTO,
     * or with status 400 (Bad Request) if the recetaDTO is not valid,
     * or with status 500 (Internal Server Error) if the recetaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recetas")
    public ResponseEntity<RecetaDTO> updateReceta(@RequestBody RecetaDTO recetaDTO) throws URISyntaxException {
        log.debug("REST request to update Receta : {}", recetaDTO);
        if (recetaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecetaDTO result = recetaService.save(recetaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recetaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recetas : get all the recetas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recetas in body
     */
    @GetMapping("/recetas")
    public ResponseEntity<List<RecetaDTO>> getAllRecetas(Pageable pageable) {
        log.debug("REST request to get a page of Recetas");
        Page<RecetaDTO> page = recetaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recetas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recetas/producto/{productoId}")
    public ResponseEntity<List<RecetaDTO>> getAllRecetasByProducto(Pageable pageable, @PathVariable Long productoId) {
        log.debug("REST request to get a page of Recetas");
        Page<RecetaDTO> page = recetaService.findAll(pageable, productoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recetas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recetas/producto/list/{productoId}")
    public ResponseEntity<List<RecetaDTO>> getAllRecetasByProductoList(Pageable pageable, @PathVariable Long productoId) {
        log.debug("REST request to get a page of Recetas");
        List<RecetaDTO> page = recetaService.findAllByProducto(productoId);
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /recetas/:id : get the "id" receta.
     *
     * @param id the id of the recetaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recetaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recetas/{id}")
    public ResponseEntity<RecetaDTO> getReceta(@PathVariable Long id) {
        log.debug("REST request to get Receta : {}", id);
        Optional<RecetaDTO> recetaDTO = recetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recetaDTO);
    }

    /**
     * DELETE  /recetas/:id : delete the "id" receta.
     *
     * @param id the id of the recetaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recetas/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        log.debug("REST request to delete Receta : {}", id);
        recetaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recetas?query=:query : search for the receta corresponding
     * to the query.
     *
     * @param query the query of the receta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recetas")
    public ResponseEntity<List<RecetaDTO>> searchRecetas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recetas for query {}", query);
        Page<RecetaDTO> page = recetaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recetas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
