package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.EquipamientoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Equipamiento.
 */
public interface EquipamientoService {

    /**
     * Save a equipamiento.
     *
     * @param equipamientoDTO the entity to save
     * @return the persisted entity
     */
    EquipamientoDTO save(EquipamientoDTO equipamientoDTO);

    /**
     * Get all the equipamientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EquipamientoDTO> findAll(Pageable pageable);

    /**
     * Get all the equipamientos by empresa
     * @param pageable
     * @param empresaId
     * @return
     */
    Page<EquipamientoDTO> findAll(Pageable pageable, Long empresaId);


    /**
     * Get the "id" equipamiento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EquipamientoDTO> findOne(Long id);

    /**
     * Delete the "id" equipamiento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
