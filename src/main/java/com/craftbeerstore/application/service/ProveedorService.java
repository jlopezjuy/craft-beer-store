package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.ProveedorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Proveedor.
 */
public interface ProveedorService {

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save
     * @return the persisted entity
     */
    ProveedorDTO save(ProveedorDTO proveedorDTO);

    /**
     * Get all the proveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProveedorDTO> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @param empresaId
     * @return
     */
    Page<ProveedorDTO> findAllByEmpresa(Pageable pageable, Long empresaId);

    /**
     * Get the "id" proveedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" proveedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
