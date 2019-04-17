package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.DetalleMovimientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DetalleMovimiento.
 */
public interface DetalleMovimientoService {

    /**
     * Save a detalleMovimiento.
     *
     * @param detalleMovimientoDTO the entity to save
     * @return the persisted entity
     */
    DetalleMovimientoDTO save(DetalleMovimientoDTO detalleMovimientoDTO);

    /**
     * Get all the detalleMovimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleMovimientoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" detalleMovimiento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DetalleMovimientoDTO> findOne(Long id);

    /**
     * Delete the "id" detalleMovimiento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the detalleMovimiento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DetalleMovimientoDTO> search(String query, Pageable pageable);
}
