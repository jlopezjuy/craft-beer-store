package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.MovimientoBarrilDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MovimientoBarril.
 */
public interface MovimientoBarrilService {

    /**
     * Save a movimientoBarril.
     *
     * @param movimientoBarrilDTO the entity to save
     * @return the persisted entity
     */
    MovimientoBarrilDTO save(MovimientoBarrilDTO movimientoBarrilDTO);

    /**
     * Get all the movimientoBarrils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovimientoBarrilDTO> findAll(Pageable pageable);


    /**
     * Get the "id" movimientoBarril.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovimientoBarrilDTO> findOne(Long id);

    /**
     * Delete the "id" movimientoBarril.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
