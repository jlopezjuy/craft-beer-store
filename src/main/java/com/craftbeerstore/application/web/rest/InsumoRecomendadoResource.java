package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.InsumoRecomendadoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;
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

/**
 * REST controller for managing InsumoRecomendado.
 */
@RestController
@RequestMapping("/api")
public class InsumoRecomendadoResource {

    private final Logger log = LoggerFactory.getLogger(InsumoRecomendadoResource.class);

    private static final String ENTITY_NAME = "insumoRecomendado";

    private final InsumoRecomendadoService insumoRecomendadoService;

    public InsumoRecomendadoResource(InsumoRecomendadoService insumoRecomendadoService) {
        this.insumoRecomendadoService = insumoRecomendadoService;
    }

    /**
     * POST  /insumo-recomendados : Create a new insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the insumoRecomendadoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insumoRecomendadoDTO, or with status 400 (Bad Request) if the insumoRecomendado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insumo-recomendados")
    public ResponseEntity<InsumoRecomendadoDTO> createInsumoRecomendado(@RequestBody InsumoRecomendadoDTO insumoRecomendadoDTO) throws URISyntaxException {
        log.debug("REST request to save InsumoRecomendado : {}", insumoRecomendadoDTO);
        if (insumoRecomendadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new insumoRecomendado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsumoRecomendadoDTO result = insumoRecomendadoService.save(insumoRecomendadoDTO);
        return ResponseEntity.created(new URI("/api/insumo-recomendados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insumo-recomendados : Updates an existing insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the insumoRecomendadoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insumoRecomendadoDTO,
     * or with status 400 (Bad Request) if the insumoRecomendadoDTO is not valid,
     * or with status 500 (Internal Server Error) if the insumoRecomendadoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insumo-recomendados")
    public ResponseEntity<InsumoRecomendadoDTO> updateInsumoRecomendado(@RequestBody InsumoRecomendadoDTO insumoRecomendadoDTO) throws URISyntaxException {
        log.debug("REST request to update InsumoRecomendado : {}", insumoRecomendadoDTO);
        if (insumoRecomendadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsumoRecomendadoDTO result = insumoRecomendadoService.save(insumoRecomendadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insumoRecomendadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insumo-recomendados : get all the insumoRecomendados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insumoRecomendados in body
     */
    @GetMapping("/insumo-recomendados")
    public ResponseEntity<List<InsumoRecomendadoDTO>> getAllInsumoRecomendados(Pageable pageable) {
        log.debug("REST request to get a page of InsumoRecomendados");
        Page<InsumoRecomendadoDTO> page = insumoRecomendadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insumo-recomendados");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/insumo-recomendados/all")
    public ResponseEntity<List<InsumoRecomendadoDTO>> getAllInsumoRecomendadosList() {
        log.debug("REST request to get all InsumoRecomendados");
        List<InsumoRecomendadoDTO> page = insumoRecomendadoService.findAll();
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /insumo-recomendados/:id : get the "id" insumoRecomendado.
     *
     * @param id the id of the insumoRecomendadoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insumoRecomendadoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insumo-recomendados/{id}")
    public ResponseEntity<InsumoRecomendadoDTO> getInsumoRecomendado(@PathVariable Long id) {
        log.debug("REST request to get InsumoRecomendado : {}", id);
        Optional<InsumoRecomendadoDTO> insumoRecomendadoDTO = insumoRecomendadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insumoRecomendadoDTO);
    }

    /**
     * DELETE  /insumo-recomendados/:id : delete the "id" insumoRecomendado.
     *
     * @param id the id of the insumoRecomendadoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insumo-recomendados/{id}")
    public ResponseEntity<Void> deleteInsumoRecomendado(@PathVariable Long id) {
        log.debug("REST request to delete InsumoRecomendado : {}", id);
        insumoRecomendadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
