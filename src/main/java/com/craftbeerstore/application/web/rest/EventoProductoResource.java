package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.EventoProductoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.EventoProductoDTO;

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
 * REST controller for managing {@link com.craftbeerstore.application.domain.EventoProducto}.
 */
@RestController
@RequestMapping("/api")
public class EventoProductoResource {

    private final Logger log = LoggerFactory.getLogger(EventoProductoResource.class);

    private static final String ENTITY_NAME = "eventoProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoProductoService eventoProductoService;

    public EventoProductoResource(EventoProductoService eventoProductoService) {
        this.eventoProductoService = eventoProductoService;
    }

    /**
     * {@code POST  /evento-productos} : Create a new eventoProducto.
     *
     * @param eventoProductoDTO the eventoProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventoProductoDTO, or with status {@code 400 (Bad Request)} if the eventoProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evento-productos")
    public ResponseEntity<EventoProductoDTO> createEventoProducto(@RequestBody EventoProductoDTO eventoProductoDTO) throws URISyntaxException {
        log.debug("REST request to save EventoProducto : {}", eventoProductoDTO);
        if (eventoProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventoProductoDTO result = eventoProductoService.save(eventoProductoDTO);
        return ResponseEntity.created(new URI("/api/evento-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evento-productos} : Updates an existing eventoProducto.
     *
     * @param eventoProductoDTO the eventoProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventoProductoDTO,
     * or with status {@code 400 (Bad Request)} if the eventoProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventoProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evento-productos")
    public ResponseEntity<EventoProductoDTO> updateEventoProducto(@RequestBody EventoProductoDTO eventoProductoDTO) throws URISyntaxException {
        log.debug("REST request to update EventoProducto : {}", eventoProductoDTO);
        if (eventoProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventoProductoDTO result = eventoProductoService.save(eventoProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventoProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evento-productos} : get all the eventoProductos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventoProductos in body.
     */
    @GetMapping("/evento-productos")
    public ResponseEntity<List<EventoProductoDTO>> getAllEventoProductos(Pageable pageable) {
        log.debug("REST request to get a page of EventoProductos");
        Page<EventoProductoDTO> page = eventoProductoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/evento-productos/evento/{eventoId}")
    public ResponseEntity<List<EventoProductoDTO>> getAllEventoProductosByEvento(@PathVariable Long eventoId) {
        log.debug("REST request to get a page of EventoProductos");
        List<EventoProductoDTO> page = eventoProductoService.findAllByEvento(eventoId);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /evento-productos/:id} : get the "id" eventoProducto.
     *
     * @param id the id of the eventoProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventoProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evento-productos/{id}")
    public ResponseEntity<EventoProductoDTO> getEventoProducto(@PathVariable Long id) {
        log.debug("REST request to get EventoProducto : {}", id);
        Optional<EventoProductoDTO> eventoProductoDTO = eventoProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoProductoDTO);
    }

    /**
     * {@code DELETE  /evento-productos/:id} : delete the "id" eventoProducto.
     *
     * @param id the id of the eventoProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evento-productos/{id}")
    public ResponseEntity<Void> deleteEventoProducto(@PathVariable Long id) {
        log.debug("REST request to delete EventoProducto : {}", id);
        eventoProductoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
