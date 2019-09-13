package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.BarrilDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Barril.
 */
public interface BarrilService {

  /**
   * Save a barril.
   *
   * @param barrilDTO the entity to save
   * @return the persisted entity
   */
  BarrilDTO save(BarrilDTO barrilDTO);

  /**
   * Get all the barrils.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<BarrilDTO> findAll(Pageable pageable);

  /**
   * Get all the barrils.
   *
   * @param pageable  the pagination information
   * @param empresaId the the id of empresa
   * @return the list of entities
   */
  Page<BarrilDTO> findAll(Pageable pageable, Long empresaId);


  /**
   * Get the "id" barril.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<BarrilDTO> findOne(Long id);

  /**
   * Delete the "id" barril.
   *
   * @param id the id of the entity
   */
  void delete(Long id);
}
