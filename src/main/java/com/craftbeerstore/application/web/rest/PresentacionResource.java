package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.PresentacionService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.PresentacionDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Presentacion}.
 */
@RestController
@RequestMapping("/api")
public class PresentacionResource {

    private final Logger log = LoggerFactory.getLogger(PresentacionResource.class);

    private static final String ENTITY_NAME = "presentacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresentacionService presentacionService;

    public PresentacionResource(PresentacionService presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * {@code POST  /presentacions} : Create a new presentacion.
     *
     * @param presentacionDTO the presentacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presentacionDTO, or with status {@code 400 (Bad Request)} if the presentacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presentacions")
    public ResponseEntity<PresentacionDTO> createPresentacion(@Valid @RequestBody PresentacionDTO presentacionDTO) throws URISyntaxException {
        log.debug("REST request to save Presentacion : {}", presentacionDTO);
        if (presentacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new presentacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresentacionDTO result = presentacionService.save(presentacionDTO);
        return ResponseEntity.created(new URI("/api/presentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presentacions} : Updates an existing presentacion.
     *
     * @param presentacionDTO the presentacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentacionDTO,
     * or with status {@code 400 (Bad Request)} if the presentacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presentacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presentacions")
    public ResponseEntity<PresentacionDTO> updatePresentacion(@Valid @RequestBody PresentacionDTO presentacionDTO) throws URISyntaxException {
        log.debug("REST request to update Presentacion : {}", presentacionDTO);
        if (presentacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PresentacionDTO result = presentacionService.save(presentacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presentacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /presentacions} : get all the presentacions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presentacions in body.
     */
    @GetMapping("/presentacions")
    public ResponseEntity<List<PresentacionDTO>> getAllPresentacions(Pageable pageable) {
        log.debug("REST request to get a page of Presentacions");
        Page<PresentacionDTO> page = presentacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presentacions/:id} : get the "id" presentacion.
     *
     * @param id the id of the presentacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presentacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presentacions/{id}")
    public ResponseEntity<PresentacionDTO> getPresentacion(@PathVariable Long id) {
        log.debug("REST request to get Presentacion : {}", id);
        Optional<PresentacionDTO> presentacionDTO = presentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presentacionDTO);
    }

    /**
     * {@code DELETE  /presentacions/:id} : delete the "id" presentacion.
     *
     * @param id the id of the presentacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presentacions/{id}")
    public ResponseEntity<Void> deletePresentacion(@PathVariable Long id) {
        log.debug("REST request to delete Presentacion : {}", id);
        presentacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
