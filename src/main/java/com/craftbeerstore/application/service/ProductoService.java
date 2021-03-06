package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.ProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Producto.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    ProductoDTO save(ProductoDTO productoDTO);

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoDTO> findAll(Pageable pageable);

    /**
     *
     * @param pageable
     * @param empresaId
     * @return
     */
    Page<ProductoDTO> findAllByEmpresa(Pageable pageable, Long empresaId);


    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductoDTO> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoDTO> search(String query, Pageable pageable);
}
