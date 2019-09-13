package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.MovimientoLitroDTO;
import com.craftbeerstore.application.service.dto.MovimientosDTO;
import com.craftbeerstore.application.service.dto.MovimientosProductoSemanaDTO;
import com.craftbeerstore.application.service.dto.MovimientosSemanaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Movimientos.
 */
public interface MovimientosService {

  /**
   * Save a movimientos.
   *
   * @param movimientosDTO the entity to save
   * @return the persisted entity
   */
  MovimientosDTO save(MovimientosDTO movimientosDTO);

  /**
   * Get all the movimientos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<MovimientosDTO> findAll(Pageable pageable);

  /**
   * Get all movimientos by empresa
   *
   * @param pageable  the pagination information
   * @param empresaId the id of empresa entity
   * @return the list of entities
   */
  Page<MovimientosDTO> findAll(Pageable pageable, Long empresaId);


  /**
   * Get the "id" movimientos.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<MovimientosDTO> findOne(Long id);

  /**
   * Delete the "id" movimientos.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  /**
   * @param empresaId
   * @param dias
   * @return
   */
  List<MovimientosSemanaDTO> findMovimientosSemana(Long empresaId, String dias);

  /**
   * @param empresaId
   * @param dias
   * @return
   */
  List<MovimientosProductoSemanaDTO> findMovimientoProductoSemana(Long empresaId,
                                                                  String dias);

  /**
   * @param empresaId
   * @param dias
   * @return
   */
  MovimientosProductoSemanaDTO findLitrosSemana(Long empresaId, String dias);

  /**
   * @param empresaId
   * @return
   */
  List<MovimientoLitroDTO> findPeriodoLitrosSemana(Long empresaId);

  /**
   * @param empresaId
   * @return
   */
  List<MovimientoLitroDTO> findPeriodoLitrosMes(Long empresaId);
}
