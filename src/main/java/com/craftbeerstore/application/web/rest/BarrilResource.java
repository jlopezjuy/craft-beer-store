package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.BarrilService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.BarrilDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Barril}.
 */
@RestController
@RequestMapping("/api")
public class BarrilResource {

    private final Logger log = LoggerFactory.getLogger(BarrilResource.class);

    private static final String ENTITY_NAME = "barril";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarrilService barrilService;

    public BarrilResource(BarrilService barrilService) {
        this.barrilService = barrilService;
    }

    /**
     * {@code POST  /barrils} : Create a new barril.
     *
     * @param barrilDTO the barrilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barrilDTO, or with status {@code 400 (Bad Request)} if the barril has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/barrils")
    public ResponseEntity<BarrilDTO> createBarril(@RequestBody BarrilDTO barrilDTO) throws URISyntaxException {
        log.debug("REST request to save Barril : {}", barrilDTO);
        if (barrilDTO.getId() != null) {
            throw new BadRequestAlertException("A new barril cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BarrilDTO result = barrilService.save(barrilDTO);
        return ResponseEntity.created(new URI("/api/barrils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /barrils} : Updates an existing barril.
     *
     * @param barrilDTO the barrilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barrilDTO,
     * or with status {@code 400 (Bad Request)} if the barrilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barrilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/barrils")
    public ResponseEntity<BarrilDTO> updateBarril(@RequestBody BarrilDTO barrilDTO) throws URISyntaxException {
        log.debug("REST request to update Barril : {}", barrilDTO);
        if (barrilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarrilDTO result = barrilService.save(barrilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, barrilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /barrils : get all the barrils.
     *
     * @param pageable  the pagination information
     * @param empresaId the empresaId information
     * @return the ResponseEntity with status 200 (OK) and the list of barrils in body
     */
    @GetMapping("/barrils/empresa/{empresaId}")
    public ResponseEntity<List<BarrilDTO>> getAllBarrilesByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Barrils");
        Page<BarrilDTO> page = barrilService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /barrils} : get all the barrils.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barrils in body.
     */
    @GetMapping("/barrils")
    public ResponseEntity<List<BarrilDTO>> getAllBarrils(Pageable pageable) {
        log.debug("REST request to get a page of Barrils");
        Page<BarrilDTO> page = barrilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /barrils/:id} : get the "id" barril.
     *
     * @param id the id of the barrilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barrilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/barrils/{id}")
    public ResponseEntity<BarrilDTO> getBarril(@PathVariable Long id) {
        log.debug("REST request to get Barril : {}", id);
        Optional<BarrilDTO> barrilDTO = barrilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barrilDTO);
    }

    /**
     * {@code DELETE  /barrils/:id} : delete the "id" barril.
     *
     * @param id the id of the barrilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/barrils/{id}")
    public ResponseEntity<Void> deleteBarril(@PathVariable Long id) {
        log.debug("REST request to delete Barril : {}", id);
        barrilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
