package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.InsumoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Insumo}.
 */
public interface InsumoService {

    /**
     * Save a insumo.
     *
     * @param insumoDTO the entity to save.
     * @return the persisted entity.
     */
    InsumoDTO save(InsumoDTO insumoDTO);

    /**
     * Get all the insumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InsumoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" insumo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InsumoDTO> findOne(Long id);

    /**
     * Delete the "id" insumo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
