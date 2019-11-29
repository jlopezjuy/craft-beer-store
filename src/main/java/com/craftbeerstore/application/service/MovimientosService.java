package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.MovimientosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Movimientos}.
 */
public interface MovimientosService {

    /**
     * Save a movimientos.
     *
     * @param movimientosDTO the entity to save.
     * @return the persisted entity.
     */
    MovimientosDTO save(MovimientosDTO movimientosDTO);

    /**
     * Get all the movimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MovimientosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" movimientos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MovimientosDTO> findOne(Long id);

    /**
     * Delete the "id" movimientos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
