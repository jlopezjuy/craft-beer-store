package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CompraInsumo.
 */
public interface CompraInsumoService {

  /**
   * Save a compraInsumo.
   *
   * @param compraInsumoDTO the entity to save
   * @return the persisted entity
   */
  CompraInsumoDTO save(CompraInsumoDTO compraInsumoDTO);

  /**
   * Get all the compraInsumos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<CompraInsumoDTO> findAll(Pageable pageable);


  /**
   * Get the "id" compraInsumo.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<CompraInsumoDTO> findOne(Long id);

  /**
   * Delete the "id" compraInsumo.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  /**
   * Get all the compraInsumos.
   *
   * @param pageable  the pagination information
   * @param empresaId the id of empresa
   * @return the list of entities
   */
  Page<CompraInsumoDTO> findAll(Pageable pageable, Long empresaId);
}
