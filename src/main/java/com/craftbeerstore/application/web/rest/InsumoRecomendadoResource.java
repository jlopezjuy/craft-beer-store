package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.InsumoRecomendadoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.InsumoRecomendado}.
 */
@RestController
@RequestMapping("/api")
public class InsumoRecomendadoResource {

    private final Logger log = LoggerFactory.getLogger(InsumoRecomendadoResource.class);

    private static final String ENTITY_NAME = "insumoRecomendado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsumoRecomendadoService insumoRecomendadoService;

    public InsumoRecomendadoResource(InsumoRecomendadoService insumoRecomendadoService) {
        this.insumoRecomendadoService = insumoRecomendadoService;
    }

    /**
     * {@code POST  /insumo-recomendados} : Create a new insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the insumoRecomendadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insumoRecomendadoDTO, or with status {@code 400 (Bad Request)} if the insumoRecomendado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insumo-recomendados")
    public ResponseEntity<InsumoRecomendadoDTO> createInsumoRecomendado(@RequestBody InsumoRecomendadoDTO insumoRecomendadoDTO) throws URISyntaxException {
        log.debug("REST request to save InsumoRecomendado : {}", insumoRecomendadoDTO);
        if (insumoRecomendadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new insumoRecomendado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsumoRecomendadoDTO result = insumoRecomendadoService.save(insumoRecomendadoDTO);
        return ResponseEntity.created(new URI("/api/insumo-recomendados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insumo-recomendados} : Updates an existing insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the insumoRecomendadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insumoRecomendadoDTO,
     * or with status {@code 400 (Bad Request)} if the insumoRecomendadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insumoRecomendadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insumo-recomendados")
    public ResponseEntity<InsumoRecomendadoDTO> updateInsumoRecomendado(@RequestBody InsumoRecomendadoDTO insumoRecomendadoDTO) throws URISyntaxException {
        log.debug("REST request to update InsumoRecomendado : {}", insumoRecomendadoDTO);
        if (insumoRecomendadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsumoRecomendadoDTO result = insumoRecomendadoService.save(insumoRecomendadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insumoRecomendadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insumo-recomendados} : get all the insumoRecomendados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insumoRecomendados in body.
     */
    @GetMapping("/insumo-recomendados")
    public ResponseEntity<List<InsumoRecomendadoDTO>> getAllInsumoRecomendados(Pageable pageable) {
        log.debug("REST request to get a page of InsumoRecomendados");
        Page<InsumoRecomendadoDTO> page = insumoRecomendadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insumo-recomendados/:id} : get the "id" insumoRecomendado.
     *
     * @param id the id of the insumoRecomendadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insumoRecomendadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insumo-recomendados/{id}")
    public ResponseEntity<InsumoRecomendadoDTO> getInsumoRecomendado(@PathVariable Long id) {
        log.debug("REST request to get InsumoRecomendado : {}", id);
        Optional<InsumoRecomendadoDTO> insumoRecomendadoDTO = insumoRecomendadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insumoRecomendadoDTO);
    }

    /**
     * {@code DELETE  /insumo-recomendados/:id} : delete the "id" insumoRecomendado.
     *
     * @param id the id of the insumoRecomendadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insumo-recomendados/{id}")
    public ResponseEntity<Void> deleteInsumoRecomendado(@PathVariable Long id) {
        log.debug("REST request to delete InsumoRecomendado : {}", id);
        insumoRecomendadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
