package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.CompraInsumoDetalle}.
 */
public interface CompraInsumoDetalleService {

    /**
     * Save a compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the entity to save.
     * @return the persisted entity.
     */
    CompraInsumoDetalleDTO save(CompraInsumoDetalleDTO compraInsumoDetalleDTO);

    /**
     * Get all the compraInsumoDetalles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompraInsumoDetalleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" compraInsumoDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompraInsumoDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" compraInsumoDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * @param compraInsumoId
     * @return
     */
    List<CompraInsumoDetalleDTO> findAll(Long compraInsumoId);

    /**
     * @param comprasInsumoDetalleDTO
     * @return
     */
    List<CompraInsumoDetalleDTO> save(List<CompraInsumoDetalleDTO> comprasInsumoDetalleDTO);
}
