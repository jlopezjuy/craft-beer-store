package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.RecetaDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Receta.
 */
public interface RecetaService {

    /**
     * Save a receta.
     *
     * @param recetaDTO the entity to save
     * @return the persisted entity
     */
    RecetaDTO save(RecetaDTO recetaDTO);

    /**
     * Get all the recetas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecetaDTO> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @param productoId
     * @return
     */
    Page<RecetaDTO> findAll(Pageable pageable, Long productoId);

    /**
     *
     * @param pageable
     * @param productoId
     * @return
     */
    List<RecetaDTO> findAllByProducto(Long productoId);


    /**
     * Get the "id" receta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecetaDTO> findOne(Long id);

    /**
     * Delete the "id" receta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
