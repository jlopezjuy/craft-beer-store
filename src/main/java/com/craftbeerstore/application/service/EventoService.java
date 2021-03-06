package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.EventoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Evento.
 */
public interface EventoService {

    /**
     * Save a evento.
     *
     * @param eventoDTO the entity to save
     * @return the persisted entity
     */
    EventoDTO save(EventoDTO eventoDTO);

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EventoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" evento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EventoDTO> findOne(Long id);

    /**
     * Delete the "id" evento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EventoDTO> search(String query, Pageable pageable);

    /**
     *
     * @param pageable
     * @param empresaId
     * @return
     */
    Page<EventoDTO> findAll(Pageable pageable, Long empresaId);
}
