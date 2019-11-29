package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.CajaService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.CajaDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Caja}.
 */
@RestController
@RequestMapping("/api")
public class CajaResource {

    private final Logger log = LoggerFactory.getLogger(CajaResource.class);

    private static final String ENTITY_NAME = "caja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CajaService cajaService;

    public CajaResource(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    /**
     * {@code POST  /cajas} : Create a new caja.
     *
     * @param cajaDTO the cajaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cajaDTO, or with status {@code 400 (Bad Request)} if the caja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cajas")
    public ResponseEntity<CajaDTO> createCaja(@Valid @RequestBody CajaDTO cajaDTO) throws URISyntaxException {
        log.debug("REST request to save Caja : {}", cajaDTO);
        if (cajaDTO.getId() != null) {
            throw new BadRequestAlertException("A new caja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CajaDTO result = cajaService.save(cajaDTO);
        return ResponseEntity.created(new URI("/api/cajas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cajas} : Updates an existing caja.
     *
     * @param cajaDTO the cajaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cajaDTO,
     * or with status {@code 400 (Bad Request)} if the cajaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cajaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cajas")
    public ResponseEntity<CajaDTO> updateCaja(@Valid @RequestBody CajaDTO cajaDTO) throws URISyntaxException {
        log.debug("REST request to update Caja : {}", cajaDTO);
        if (cajaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CajaDTO result = cajaService.save(cajaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cajaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cajas} : get all the cajas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cajas in body.
     */
    @GetMapping("/cajas")
    public ResponseEntity<List<CajaDTO>> getAllCajas(Pageable pageable) {
        log.debug("REST request to get a page of Cajas");
        Page<CajaDTO> page = cajaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cajas/:id} : get the "id" caja.
     *
     * @param id the id of the cajaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cajaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cajas/{id}")
    public ResponseEntity<CajaDTO> getCaja(@PathVariable Long id) {
        log.debug("REST request to get Caja : {}", id);
        Optional<CajaDTO> cajaDTO = cajaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cajaDTO);
    }

    /**
     * {@code DELETE  /cajas/:id} : delete the "id" caja.
     *
     * @param id the id of the cajaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cajas/{id}")
    public ResponseEntity<Void> deleteCaja(@PathVariable Long id) {
        log.debug("REST request to delete Caja : {}", id);
        cajaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
