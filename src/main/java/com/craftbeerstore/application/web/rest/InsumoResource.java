package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.service.InsumoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.InsumoDTO;
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
 * REST controller for managing {@link com.craftbeerstore.application.domain.Insumo}.
 */
@RestController
@RequestMapping("/api")
public class InsumoResource {

    private final Logger log = LoggerFactory.getLogger(InsumoResource.class);

    private static final String ENTITY_NAME = "insumo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsumoService insumoService;

    public InsumoResource(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    /**
     * {@code POST  /insumos} : Create a new insumo.
     *
     * @param insumoDTO the insumoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insumoDTO, or with status {@code 400 (Bad Request)} if the insumo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insumos")
    public ResponseEntity<InsumoDTO> createInsumo(@Valid @RequestBody InsumoDTO insumoDTO) throws URISyntaxException {
        log.debug("REST request to save Insumo : {}", insumoDTO);
        if (insumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new insumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsumoDTO result = insumoService.save(insumoDTO);
        return ResponseEntity.created(new URI("/api/insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insumos} : Updates an existing insumo.
     *
     * @param insumoDTO the insumoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insumoDTO,
     * or with status {@code 400 (Bad Request)} if the insumoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insumoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insumos")
    public ResponseEntity<InsumoDTO> updateInsumo(@Valid @RequestBody InsumoDTO insumoDTO) throws URISyntaxException {
        log.debug("REST request to update Insumo : {}", insumoDTO);
        if (insumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsumoDTO result = insumoService.save(insumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /insumos} : get all the insumos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insumos in body.
     */
    @GetMapping("/insumos")
    public ResponseEntity<List<InsumoDTO>> getAllInsumos(Pageable pageable) {
        log.debug("REST request to get a page of Insumos");
        Page<InsumoDTO> page = insumoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * @param empresaId
     * @param tipoInsumo
     * @return
     */
    @GetMapping("/insumos/tipo/{empresaId}/{tipoInsumo}")
    public ResponseEntity<List<InsumoDTO>> getAllInsumostIPO(@PathVariable Long empresaId, @PathVariable TipoInsumo tipoInsumo) {
        log.debug("REST request to get a page of Insumos {}", empresaId);
        log.debug("REST request to get a page of Insumos {}", tipoInsumo);
        List<InsumoDTO> page = insumoService.findAllByEmpresaAndTipo(empresaId, tipoInsumo);
        return ResponseEntity.ok().body(page);
    }

    /**
     * @param empresaId
     * @param tipoInsumos
     * @return
     */
    @GetMapping("/insumos/tipo/{empresaId}")
    public ResponseEntity<List<InsumoDTO>> getAllInsumostIPO(@PathVariable Long empresaId, @RequestParam List<TipoInsumo> tipoInsumos) {
        log.debug("REST request to get a page of Insumos {}", empresaId);
        log.debug("REST request to get a page of Insumos {}", tipoInsumos);
        List<InsumoDTO> page = insumoService.findAllByEmpresaAndTipo(empresaId, tipoInsumos);
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET /insumos/:empresaId : get all the insumos by empresa
     *
     * @param pageable
     * @param empresaId
     * @return the ResponseEntity with status 200 (OK) and the list of insumos by empresa in body
     */
    @GetMapping("/insumos/empresa/{empresaId}")
    public ResponseEntity<List<InsumoDTO>> getAllInsumosByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Insumos by Empresa");
        Page<InsumoDTO> page = insumoService.findAllByEmpresa(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insumos/:id} : get the "id" insumo.
     *
     * @param id the id of the insumoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insumoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insumos/{id}")
    public ResponseEntity<InsumoDTO> getInsumo(@PathVariable Long id) {
        log.debug("REST request to get Insumo : {}", id);
        Optional<InsumoDTO> insumoDTO = insumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insumoDTO);
    }

    /**
     * {@code DELETE  /insumos/:id} : delete the "id" insumo.
     *
     * @param id the id of the insumoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insumos/{id}")
    public ResponseEntity<Void> deleteInsumo(@PathVariable Long id) {
        log.debug("REST request to delete Insumo : {}", id);
        insumoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
