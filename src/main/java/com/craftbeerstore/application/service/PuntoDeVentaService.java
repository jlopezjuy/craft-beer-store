package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.PuntoDeVentaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PuntoDeVenta.
 */
public interface PuntoDeVentaService {

  /**
   * Save a puntoDeVenta.
   *
   * @param puntoDeVentaDTO the entity to save
   * @return the persisted entity
   */
  PuntoDeVentaDTO save(PuntoDeVentaDTO puntoDeVentaDTO);

  /**
   * Get all the puntoDeVentas.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  Page<PuntoDeVentaDTO> findAll(Pageable pageable);

  /**
   * @param pageable
   * @param clienteId
   * @return
   */
  Page<PuntoDeVentaDTO> findAllByCliente(Pageable pageable, Long clienteId);


  /**
   * Get the "id" puntoDeVenta.
   *
   * @param id the id of the entity
   * @return the entity
   */
  Optional<PuntoDeVentaDTO> findOne(Long id);

  /**
   * Delete the "id" puntoDeVenta.
   *
   * @param id the id of the entity
   */
  void delete(Long id);

  /**
   * @param clienteId
   * @return
   */
  List<PuntoDeVentaDTO> findOneByCliente(Long clienteId);
}
