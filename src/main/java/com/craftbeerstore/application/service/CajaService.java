package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.CajaChartDTO;
import com.craftbeerstore.application.service.dto.CajaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Caja.
 */
public interface CajaService {

    /**
     * Save a caja.
     *
     * @param cajaDTO the entity to save
     * @return the persisted entity
     */
    CajaDTO save(CajaDTO cajaDTO);

    /**
     * Get all the cajas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CajaDTO> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @param empresaId
     * @return
     */
    Page<CajaDTO> findAll(Pageable pageable, Long empresaId);


    /**
     * Get the "id" caja.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CajaDTO> findOne(Long id);

    /**
     * Delete the "id" caja.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the caja corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CajaDTO> search(String query, Pageable pageable);

    Optional<CajaChartDTO> searchIngresoEgreso(Long empresaId);
}
