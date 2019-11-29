package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.RecetaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Receta}.
 */
public interface RecetaService {

    /**
     * Save a receta.
     *
     * @param recetaDTO the entity to save.
     * @return the persisted entity.
     */
    RecetaDTO save(RecetaDTO recetaDTO);

    /**
     * Get all the recetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecetaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" receta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecetaDTO> findOne(Long id);

    /**
     * Delete the "id" receta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
