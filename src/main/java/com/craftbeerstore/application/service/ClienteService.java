package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Cliente}.
 */
public interface ClienteService {

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    ClienteDTO save(ClienteDTO clienteDTO);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteDTO> findAll(Pageable pageable);

  /**
   * @param pageable
   * @param empresaId
   * @return
   */
  Page<ClienteDTO> findAll(Pageable pageable, Long empresaId);


    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteDTO> findOne(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
