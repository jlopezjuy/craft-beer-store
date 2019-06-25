package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.EventoProductoDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EventoProducto.
 */
public interface EventoProductoService {

    /**
     * Save a eventoProducto.
     *
     * @param eventoProductoDTO the entity to save
     * @return the persisted entity
     */
    EventoProductoDTO save(EventoProductoDTO eventoProductoDTO);

    /**
     * Get all the eventoProductos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EventoProductoDTO> findAll(Pageable pageable);

    /**
     *
     * @param eventoId
     * @return
     */
    List<EventoProductoDTO> findAllByEvento(Long eventoId);

    /**
     * Get the "id" eventoProducto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EventoProductoDTO> findOne(Long id);

    /**
     * Delete the "id" eventoProducto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
