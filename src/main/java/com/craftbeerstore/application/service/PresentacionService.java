package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.PresentacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Presentacion.
 */
public interface PresentacionService {

  /**
   * Save a presentacion.
   *
   * @param presentacionDTO the entity to save
   * @return the persisted entity
   */
  PresentacionDTO save(PresentacionDTO presentacionDTO);

  /**
   * Get all the presentacions.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<PresentacionDTO> findAll(Pageable pageable);

  /**
   * @param pageable
   * @param productoId
   * @return
   */
  Page<PresentacionDTO> findAllByProducto(Pageable pageable, Long productoId);

  /**
   * @param pageable the pagination information
   * @param empresaId id of empresa
   * @return
   */
  Page<PresentacionDTO> findAllByEmpresa(Pageable pageable, Long empresaId);


  /**
   * Get the "id" presentacion.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<PresentacionDTO> findOne(Long id);

  /**
   * Delete the "id" presentacion.
   *
   * @param id the id of the entity
   */
  void delete(Long id);
}
