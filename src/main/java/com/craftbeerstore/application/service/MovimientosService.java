package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.MovimientosDTO;

import com.craftbeerstore.application.service.dto.MovimientosProductoSemanaDTO;
import com.craftbeerstore.application.service.dto.MovimientosSemanaDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Movimientos.
 */
public interface MovimientosService {

    /**
     * Save a movimientos.
     *
     * @param movimientosDTO the entity to save
     * @return the persisted entity
     */
    MovimientosDTO save(MovimientosDTO movimientosDTO);

    /**
     * Get all the movimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovimientosDTO> findAll(Pageable pageable);

    /**
     * Get all movimientos by empresa
     *
     * @param pageable the pagination information
     * @param empresaId the id of empresa entity
     * @return the list of entities
     */
    Page<MovimientosDTO> findAll(Pageable pageable, Long empresaId);


    /**
     * Get the "id" movimientos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovimientosDTO> findOne(Long id);

    /**
     * Delete the "id" movimientos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the movimientos corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovimientosDTO> search(String query, Pageable pageable);

    /**
     *
     * @param empresaId
     * @return
     */
    List<MovimientosSemanaDTO> findMovimientosSemana(Long empresaId);

    /**
     *
     * @param empresaId
     * @return
     */
    List<MovimientosProductoSemanaDTO> findMovimientoProductoSemana(Long empresaId);
}
