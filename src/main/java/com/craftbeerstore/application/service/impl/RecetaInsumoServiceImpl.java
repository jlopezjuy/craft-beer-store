package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.repository.RecetaRepository;
import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.domain.RecetaInsumo;
import com.craftbeerstore.application.repository.RecetaInsumoRepository;
import com.craftbeerstore.application.repository.search.RecetaInsumoSearchRepository;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
import com.craftbeerstore.application.service.mapper.RecetaInsumoMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RecetaInsumo.
 */
@Service
@Transactional
public class RecetaInsumoServiceImpl implements RecetaInsumoService {

    private final Logger log = LoggerFactory.getLogger(RecetaInsumoServiceImpl.class);

    private final RecetaInsumoRepository recetaInsumoRepository;

    private final RecetaInsumoMapper recetaInsumoMapper;

    private final RecetaInsumoSearchRepository recetaInsumoSearchRepository;

    private final RecetaRepository recetaRepository;

    private final InsumoRepository insumoRepository;

    public RecetaInsumoServiceImpl(RecetaInsumoRepository recetaInsumoRepository,
        RecetaInsumoMapper recetaInsumoMapper,
        RecetaInsumoSearchRepository recetaInsumoSearchRepository,
        RecetaRepository recetaRepository,
        InsumoRepository insumoRepository) {
        this.recetaInsumoRepository = recetaInsumoRepository;
        this.recetaInsumoMapper = recetaInsumoMapper;
        this.recetaInsumoSearchRepository = recetaInsumoSearchRepository;
        this.recetaRepository = recetaRepository;
        this.insumoRepository = insumoRepository;
    }

    /**
     * Save a recetaInsumo.
     *
     * @param recetaInsumoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecetaInsumoDTO save(RecetaInsumoDTO recetaInsumoDTO) {
        log.debug("Request to save RecetaInsumo : {}", recetaInsumoDTO);
        RecetaInsumo recetaInsumo = recetaInsumoMapper.toEntity(recetaInsumoDTO);
        recetaInsumo = recetaInsumoRepository.save(recetaInsumo);
        RecetaInsumoDTO result = recetaInsumoMapper.toDto(recetaInsumo);
        recetaInsumoSearchRepository.save(recetaInsumo);
        return result;
    }

    /**
     * Get all the recetaInsumos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecetaInsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecetaInsumos");
        return recetaInsumoRepository.findAll(pageable)
            .map(recetaInsumoMapper::toDto);
    }

    /**
     *
     * @param recetaId
     * @param tipoInsumo
     * @return
     */
    @Override
    public List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId,
        TipoInsumo tipoInsumo) {
        Receta receta = this.recetaRepository.getOne(recetaId);
        return recetaInsumoMapper.toDto(recetaInsumoRepository.findAllByRecetaAndTipoInsumo(receta, tipoInsumo));
    }

    /**
     *
     * @param recetaId
     * @param tipoInsumos
     * @return
     */
    @Override
    public List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId,
        List<TipoInsumo> tipoInsumos) {
        Receta receta = this.recetaRepository.getOne(recetaId);
        return recetaInsumoMapper.toDto(recetaInsumoRepository.findAllByRecetaAndTipoInsumoNotIn(receta, tipoInsumos));
    }


    /**
     * Get one recetaInsumo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecetaInsumoDTO> findOne(Long id) {
        log.debug("Request to get RecetaInsumo : {}", id);
        return recetaInsumoRepository.findById(id)
            .map(recetaInsumoMapper::toDto);
    }

    /**
     * Delete the recetaInsumo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecetaInsumo : {}", id);
        recetaInsumoRepository.deleteById(id);
        recetaInsumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the recetaInsumo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecetaInsumoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RecetaInsumos for query {}", query);
        return recetaInsumoSearchRepository.search(queryStringQuery(query), pageable)
            .map(recetaInsumoMapper::toDto);
    }
}
