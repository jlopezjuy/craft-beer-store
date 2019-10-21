package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.MovimientoTanqueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MovimientoTanque.
 */
public interface MovimientoTanqueService {

    /**
     * Save a movimientoTanque.
     *
     * @param movimientoTanqueDTO the entity to save
     * @return the persisted entity
     */
    MovimientoTanqueDTO save(MovimientoTanqueDTO movimientoTanqueDTO);

    /**
     * Get all the movimientoTanques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovimientoTanqueDTO> findAll(Pageable pageable);

  /**
   * Get all the movimientoTanques.
   *
   * @param pageable the pagination information
   * @param tanqueId the id of tanque entity
   * @return the list of entities
   */
  Page<MovimientoTanqueDTO> findAll(Pageable pageable, Long tanqueId);


    /**
     * Get the "id" movimientoTanque.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovimientoTanqueDTO> findOne(Long id);

    /**
     * Delete the "id" movimientoTanque.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
