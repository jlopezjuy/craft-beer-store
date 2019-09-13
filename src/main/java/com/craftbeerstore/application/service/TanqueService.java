package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.TanqueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Tanque.
 */
public interface TanqueService {

  /**
   * Save a tanque.
   *
   * @param tanqueDTO the entity to save
   * @return the persisted entity
   */
  TanqueDTO save(TanqueDTO tanqueDTO);

  /**
   * Get all the tanques.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<TanqueDTO> findAll(Pageable pageable);

  /**
   * Get all the tanques.
   *
   * @param pageable  the pagination information
   * @param empresaId the the id of empresa
   * @return the list of entities
   */
  Page<TanqueDTO> findAll(Pageable pageable, Long empresaId);


  /**
   * Get the "id" tanque.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<TanqueDTO> findOne(Long id);

  /**
   * Delete the "id" tanque.
   *
   * @param id the id of the entity
   */
  void delete(Long id);
}
