package com.craftbeerstore.application.service;

import com.craftbeerstore.application.service.dto.EstilosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.craftbeerstore.application.domain.Estilos}.
 */
public interface EstilosService {

    /**
     * Save a estilos.
     *
     * @param estilosDTO the entity to save.
     * @return the persisted entity.
     */
    EstilosDTO save(EstilosDTO estilosDTO);

    /**
     * Get all the estilos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstilosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estilos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstilosDTO> findOne(Long id);

    /**
     * Delete the "id" estilos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * @return
     */
    List<EstilosDTO> findAllEstilos();
}
