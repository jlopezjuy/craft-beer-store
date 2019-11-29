package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.TanqueService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.TanqueDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Tanque}.
 */
@RestController
@RequestMapping("/api")
public class TanqueResource {

    private final Logger log = LoggerFactory.getLogger(TanqueResource.class);

    private static final String ENTITY_NAME = "tanque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TanqueService tanqueService;

    public TanqueResource(TanqueService tanqueService) {
        this.tanqueService = tanqueService;
    }

    /**
     * {@code POST  /tanques} : Create a new tanque.
     *
     * @param tanqueDTO the tanqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tanqueDTO, or with status {@code 400 (Bad Request)} if the tanque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tanques")
    public ResponseEntity<TanqueDTO> createTanque(@RequestBody TanqueDTO tanqueDTO) throws URISyntaxException {
        log.debug("REST request to save Tanque : {}", tanqueDTO);
        if (tanqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new tanque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TanqueDTO result = tanqueService.save(tanqueDTO);
        return ResponseEntity.created(new URI("/api/tanques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tanques} : Updates an existing tanque.
     *
     * @param tanqueDTO the tanqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tanqueDTO,
     * or with status {@code 400 (Bad Request)} if the tanqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tanqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tanques")
    public ResponseEntity<TanqueDTO> updateTanque(@RequestBody TanqueDTO tanqueDTO) throws URISyntaxException {
        log.debug("REST request to update Tanque : {}", tanqueDTO);
        if (tanqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TanqueDTO result = tanqueService.save(tanqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tanqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tanques} : get all the tanques.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tanques in body.
     */
    @GetMapping("/tanques")
    public ResponseEntity<List<TanqueDTO>> getAllTanques(Pageable pageable) {
        log.debug("REST request to get a page of Tanques");
        Page<TanqueDTO> page = tanqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tanques/:id} : get the "id" tanque.
     *
     * @param id the id of the tanqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tanqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tanques/{id}")
    public ResponseEntity<TanqueDTO> getTanque(@PathVariable Long id) {
        log.debug("REST request to get Tanque : {}", id);
        Optional<TanqueDTO> tanqueDTO = tanqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tanqueDTO);
    }

    /**
     * {@code DELETE  /tanques/:id} : delete the "id" tanque.
     *
     * @param id the id of the tanqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tanques/{id}")
    public ResponseEntity<Void> deleteTanque(@PathVariable Long id) {
        log.debug("REST request to delete Tanque : {}", id);
        tanqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
