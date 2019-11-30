package com.craftbeerstore.application.service;

import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
import com.craftbeerstore.application.service.dto.MedicionLoteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.MedicionLote}.
 */
public interface MedicionLoteService {

    /**
     * Save a medicionLote.
     *
     * @param medicionLoteDTO the entity to save.
     * @return the persisted entity.
     */
    MedicionLoteDTO save(MedicionLoteDTO medicionLoteDTO);

    /**
     * Get all the medicionLotes.
     *
     * @param pageable the pagination information
     * @param loteId the id of lote
     * @return the list of entities
     */
    Page<MedicionLoteDTO> findAll(Pageable pageable, Long loteId);

    /**
     * Get all the medicionLotes.
     *
     * @param pageable the pagination information
     * @param loteId the id of lote
     * @param tipoMedicion the tipoMedicion of medicion
     * @return the list of entities
     */
    List<MedicionLoteDTO> findAll(Long loteId, TipoMedicion tipoMedicion);

    /**
     * Get all the medicionLotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicionLoteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" medicionLote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicionLoteDTO> findOne(Long id);

    /**
     * Delete the "id" medicionLote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
