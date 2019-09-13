package com.craftbeerstore.application.service;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RecetaInsumo.
 */
public interface RecetaInsumoService {

  /**
   * Save a recetaInsumo.
   *
   * @param recetaInsumoDTO the entity to save
   * @return the persisted entity
   */
  RecetaInsumoDTO save(RecetaInsumoDTO recetaInsumoDTO);

  /**
   * Get all the recetaInsumos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<RecetaInsumoDTO> findAll(Pageable pageable);

  /**
   * @param recetaId
   * @param tipoInsumo
   * @return
   */
  List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId, TipoInsumo tipoInsumo);

  /**
   * @param recetaId
   * @param tipoInsumos
   * @return
   */
  List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId, List<TipoInsumo> tipoInsumos);


  /**
   * Get the "id" recetaInsumo.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<RecetaInsumoDTO> findOne(Long id);

  /**
   * Delete the "id" recetaInsumo.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  void delete(List<String> list);
}
