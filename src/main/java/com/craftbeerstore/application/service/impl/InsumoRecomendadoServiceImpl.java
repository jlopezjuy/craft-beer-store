package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.InsumoRecomendadoService;
import com.craftbeerstore.application.domain.InsumoRecomendado;
import com.craftbeerstore.application.repository.InsumoRecomendadoRepository;
import com.craftbeerstore.application.repository.search.InsumoRecomendadoSearchRepository;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;
import com.craftbeerstore.application.service.mapper.InsumoRecomendadoMapper;
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
 * Service Implementation for managing InsumoRecomendado.
 */
@Service
@Transactional
public class InsumoRecomendadoServiceImpl implements InsumoRecomendadoService {

    private final Logger log = LoggerFactory.getLogger(InsumoRecomendadoServiceImpl.class);

    private final InsumoRecomendadoRepository insumoRecomendadoRepository;

    private final InsumoRecomendadoMapper insumoRecomendadoMapper;

    private final InsumoRecomendadoSearchRepository insumoRecomendadoSearchRepository;

    public InsumoRecomendadoServiceImpl(InsumoRecomendadoRepository insumoRecomendadoRepository, InsumoRecomendadoMapper insumoRecomendadoMapper, InsumoRecomendadoSearchRepository insumoRecomendadoSearchRepository) {
        this.insumoRecomendadoRepository = insumoRecomendadoRepository;
        this.insumoRecomendadoMapper = insumoRecomendadoMapper;
        this.insumoRecomendadoSearchRepository = insumoRecomendadoSearchRepository;
    }

    /**
     * Save a insumoRecomendado.
     *
     * @param insumoRecomendadoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsumoRecomendadoDTO save(InsumoRecomendadoDTO insumoRecomendadoDTO) {
        log.debug("Request to save InsumoRecomendado : {}", insumoRecomendadoDTO);
        InsumoRecomendado insumoRecomendado = insumoRecomendadoMapper.toEntity(insumoRecomendadoDTO);
        insumoRecomendado = insumoRecomendadoRepository.save(insumoRecomendado);
        InsumoRecomendadoDTO result = insumoRecomendadoMapper.toDto(insumoRecomendado);
        insumoRecomendadoSearchRepository.save(insumoRecomendado);
        return result;
    }

    /**
     * Get all the insumoRecomendados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoRecomendadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InsumoRecomendados");
        return insumoRecomendadoRepository.findAll(pageable)
            .map(insumoRecomendadoMapper::toDto);
    }


    /**
     * Get one insumoRecomendado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InsumoRecomendadoDTO> findOne(Long id) {
        log.debug("Request to get InsumoRecomendado : {}", id);
        return insumoRecomendadoRepository.findById(id)
            .map(insumoRecomendadoMapper::toDto);
    }

    /**
     * Delete the insumoRecomendado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InsumoRecomendado : {}", id);
        insumoRecomendadoRepository.deleteById(id);
        insumoRecomendadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the insumoRecomendado corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoRecomendadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InsumoRecomendados for query {}", query);
        return insumoRecomendadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(insumoRecomendadoMapper::toDto);
    }

    /**
     * Gel all insumos recomendados
     * @return
     */
    @Override
    public List<InsumoRecomendadoDTO> findAll() {
        return insumoRecomendadoMapper.toDto(insumoRecomendadoRepository.findAll());
    }
}
