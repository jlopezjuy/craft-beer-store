package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.EtapaLoteService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.EtapaLoteDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.EtapaLote}.
 */
@RestController
@RequestMapping("/api")
public class EtapaLoteResource {

    private final Logger log = LoggerFactory.getLogger(EtapaLoteResource.class);

    private static final String ENTITY_NAME = "etapaLote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapaLoteService etapaLoteService;

    public EtapaLoteResource(EtapaLoteService etapaLoteService) {
        this.etapaLoteService = etapaLoteService;
    }

    /**
     * {@code POST  /etapa-lotes} : Create a new etapaLote.
     *
     * @param etapaLoteDTO the etapaLoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapaLoteDTO, or with status {@code 400 (Bad Request)} if the etapaLote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etapa-lotes")
    public ResponseEntity<EtapaLoteDTO> createEtapaLote(@RequestBody EtapaLoteDTO etapaLoteDTO) throws URISyntaxException {
        log.debug("REST request to save EtapaLote : {}", etapaLoteDTO);
        if (etapaLoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new etapaLote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapaLoteDTO result = etapaLoteService.save(etapaLoteDTO);
        return ResponseEntity.created(new URI("/api/etapa-lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etapa-lotes} : Updates an existing etapaLote.
     *
     * @param etapaLoteDTO the etapaLoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapaLoteDTO,
     * or with status {@code 400 (Bad Request)} if the etapaLoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapaLoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etapa-lotes")
    public ResponseEntity<EtapaLoteDTO> updateEtapaLote(@RequestBody EtapaLoteDTO etapaLoteDTO) throws URISyntaxException {
        log.debug("REST request to update EtapaLote : {}", etapaLoteDTO);
        if (etapaLoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtapaLoteDTO result = etapaLoteService.save(etapaLoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapaLoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etapa-lotes} : get all the etapaLotes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapaLotes in body.
     */
    @GetMapping("/etapa-lotes")
    public ResponseEntity<List<EtapaLoteDTO>> getAllEtapaLotes(Pageable pageable) {
        log.debug("REST request to get a page of EtapaLotes");
        Page<EtapaLoteDTO> page = etapaLoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etapa-lotes/:id} : get the "id" etapaLote.
     *
     * @param id the id of the etapaLoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapaLoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etapa-lotes/{id}")
    public ResponseEntity<EtapaLoteDTO> getEtapaLote(@PathVariable Long id) {
        log.debug("REST request to get EtapaLote : {}", id);
        Optional<EtapaLoteDTO> etapaLoteDTO = etapaLoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapaLoteDTO);
    }

    /**
     * {@code DELETE  /etapa-lotes/:id} : delete the "id" etapaLote.
     *
     * @param id the id of the etapaLoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etapa-lotes/{id}")
    public ResponseEntity<Void> deleteEtapaLote(@PathVariable Long id) {
        log.debug("REST request to delete EtapaLote : {}", id);
        etapaLoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
