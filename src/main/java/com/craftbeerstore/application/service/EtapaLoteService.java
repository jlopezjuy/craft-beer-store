package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.EtapaLoteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EtapaLote.
 */
public interface EtapaLoteService {

    /**
     * Save a etapaLote.
     *
     * @param etapaLoteDTO the entity to save
     * @return the persisted entity
     */
    EtapaLoteDTO save(EtapaLoteDTO etapaLoteDTO);

    /**
     * Get all the etapaLotes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EtapaLoteDTO> findAll(Pageable pageable);

  /**
   * Get all the etapaLotes.
   *
   * @param pageable the pagination information
   * @param loteId the id lote
   * @return the list of entities
   */
  Page<EtapaLoteDTO> findAll(Pageable pageable, Long loteId);


    /**
     * Get the "id" etapaLote.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EtapaLoteDTO> findOne(Long id);

    /**
     * Delete the "id" etapaLote.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
