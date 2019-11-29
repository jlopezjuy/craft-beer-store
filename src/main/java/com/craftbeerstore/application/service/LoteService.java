package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.LoteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Lote}.
 */
public interface LoteService {

    /**
     * Save a lote.
     *
     * @param loteDTO the entity to save.
     * @return the persisted entity.
     */
    LoteDTO save(LoteDTO loteDTO);

    /**
     * Get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoteDTO> findOne(Long id);

    /**
     * Delete the "id" lote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
