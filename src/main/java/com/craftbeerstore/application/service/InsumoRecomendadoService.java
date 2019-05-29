package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing InsumoRecomendado.
 */
public interface InsumoRecomendadoService {

    /**
     * Save a insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the entity to save
     * @return the persisted entity
     */
    InsumoRecomendadoDTO save(InsumoRecomendadoDTO insumoRecomendadoDTO);

    /**
     * Get all the insumoRecomendados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsumoRecomendadoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" insumoRecomendado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InsumoRecomendadoDTO> findOne(Long id);

    /**
     * Delete the "id" insumoRecomendado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insumoRecomendado corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsumoRecomendadoDTO> search(String query, Pageable pageable);
}
