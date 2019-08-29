package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.EventoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.EventoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Evento.
 */
@RestController
@RequestMapping("/api")
public class EventoResource {

    private final Logger log = LoggerFactory.getLogger(EventoResource.class);

    private static final String ENTITY_NAME = "evento";

    private final EventoService eventoService;

    public EventoResource(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * POST  /eventos : Create a new evento.
     *
     * @param eventoDTO the eventoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventoDTO, or with status 400 (Bad Request) if the evento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eventos")
    public ResponseEntity<EventoDTO> createEvento(@Valid @RequestBody EventoDTO eventoDTO) throws URISyntaxException {
        log.debug("REST request to save Evento : {}", eventoDTO);
        if (eventoDTO.getId() != null) {
            throw new BadRequestAlertException("A new evento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventoDTO result = eventoService.save(eventoDTO);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eventos : Updates an existing evento.
     *
     * @param eventoDTO the eventoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventoDTO,
     * or with status 400 (Bad Request) if the eventoDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eventos")
    public ResponseEntity<EventoDTO> updateEvento(@Valid @RequestBody EventoDTO eventoDTO) throws URISyntaxException {
        log.debug("REST request to update Evento : {}", eventoDTO);
        if (eventoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventoDTO result = eventoService.save(eventoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eventos : get all the eventos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventos in body
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<EventoDTO>> getAllEventos(Pageable pageable) {
        log.debug("REST request to get a page of Eventos");
        Page<EventoDTO> page = eventoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /eventos/empresa/:empresaId : get eventos by empresa
     * @param pageable
     * @param empresaId
     * @return
     */
    @GetMapping("/eventos/empresa/{empresaId}")
    public ResponseEntity<List<EventoDTO>> getAllEventosByEmpresa(Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to get a page of Eventos");
        Page<EventoDTO> page = eventoService.findAll(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eventos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /eventos/:id : get the "id" evento.
     *
     * @param id the id of the eventoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eventos/{id}")
    public ResponseEntity<EventoDTO> getEvento(@PathVariable Long id) {
        log.debug("REST request to get Evento : {}", id);
        Optional<EventoDTO> eventoDTO = eventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoDTO);
    }

    /**
     * DELETE  /eventos/:id : delete the "id" evento.
     *
     * @param id the id of the eventoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        log.debug("REST request to delete Evento : {}", id);
        eventoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
