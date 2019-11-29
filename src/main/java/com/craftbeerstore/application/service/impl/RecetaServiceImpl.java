package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.RecetaService;
import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.repository.RecetaRepository;
import com.craftbeerstore.application.service.dto.RecetaDTO;
import com.craftbeerstore.application.service.mapper.RecetaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Receta}.
 */
@Service
@Transactional
public class RecetaServiceImpl implements RecetaService {

    private final Logger log = LoggerFactory.getLogger(RecetaServiceImpl.class);

    private final RecetaRepository recetaRepository;

    private final RecetaMapper recetaMapper;

    public RecetaServiceImpl(RecetaRepository recetaRepository, RecetaMapper recetaMapper) {
        this.recetaRepository = recetaRepository;
        this.recetaMapper = recetaMapper;
    }

    /**
     * Save a receta.
     *
     * @param recetaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecetaDTO save(RecetaDTO recetaDTO) {
        log.debug("Request to save Receta : {}", recetaDTO);
        Receta receta = recetaMapper.toEntity(recetaDTO);
        receta = recetaRepository.save(receta);
        return recetaMapper.toDto(receta);
    }

    /**
     * Get all the recetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecetaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recetas");
        return recetaRepository.findAll(pageable)
            .map(recetaMapper::toDto);
    }


    /**
     * Get one receta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecetaDTO> findOne(Long id) {
        log.debug("Request to get Receta : {}", id);
        return recetaRepository.findById(id)
            .map(recetaMapper::toDto);
    }

    /**
     * Delete the receta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receta : {}", id);
        recetaRepository.deleteById(id);
    }
}
