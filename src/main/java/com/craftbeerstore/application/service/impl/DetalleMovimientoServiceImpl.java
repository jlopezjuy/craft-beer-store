package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Movimientos;
import com.craftbeerstore.application.repository.MovimientosRepository;
import com.craftbeerstore.application.service.DetalleMovimientoService;
import com.craftbeerstore.application.domain.DetalleMovimiento;
import com.craftbeerstore.application.repository.DetalleMovimientoRepository;
import com.craftbeerstore.application.repository.search.DetalleMovimientoSearchRepository;
import com.craftbeerstore.application.service.dto.DetalleMovimientoDTO;
import com.craftbeerstore.application.service.mapper.DetalleMovimientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DetalleMovimiento.
 */
@Service
@Transactional
public class DetalleMovimientoServiceImpl implements DetalleMovimientoService {

    private final Logger log = LoggerFactory.getLogger(DetalleMovimientoServiceImpl.class);

    private final DetalleMovimientoRepository detalleMovimientoRepository;

    private final DetalleMovimientoMapper detalleMovimientoMapper;

    private final DetalleMovimientoSearchRepository detalleMovimientoSearchRepository;

    private final MovimientosRepository movimientosRepository;

    public DetalleMovimientoServiceImpl(DetalleMovimientoRepository detalleMovimientoRepository,
        DetalleMovimientoMapper detalleMovimientoMapper,
        DetalleMovimientoSearchRepository detalleMovimientoSearchRepository,
        MovimientosRepository movimientosRepository) {
        this.detalleMovimientoRepository = detalleMovimientoRepository;
        this.detalleMovimientoMapper = detalleMovimientoMapper;
        this.detalleMovimientoSearchRepository = detalleMovimientoSearchRepository;
        this.movimientosRepository = movimientosRepository;
    }

    /**
     * Save a detalleMovimiento.
     *
     * @param detalleMovimientoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DetalleMovimientoDTO save(DetalleMovimientoDTO detalleMovimientoDTO) {
        log.debug("Request to save DetalleMovimiento : {}", detalleMovimientoDTO);
        DetalleMovimiento detalleMovimiento = detalleMovimientoMapper.toEntity(detalleMovimientoDTO);
        detalleMovimiento = detalleMovimientoRepository.save(detalleMovimiento);
        DetalleMovimientoDTO result = detalleMovimientoMapper.toDto(detalleMovimiento);
        detalleMovimientoSearchRepository.save(detalleMovimiento);
        return result;
    }

    /**
     * Get all the detalleMovimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleMovimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleMovimientos");
        return detalleMovimientoRepository.findAll(pageable)
            .map(detalleMovimientoMapper::toDto);
    }


    /**
     * Get one detalleMovimiento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleMovimientoDTO> findOne(Long id) {
        log.debug("Request to get DetalleMovimiento : {}", id);
        return detalleMovimientoRepository.findById(id)
            .map(detalleMovimientoMapper::toDto);
    }

    /**
     * Delete the detalleMovimiento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetalleMovimiento : {}", id);
        detalleMovimientoRepository.deleteById(id);
        detalleMovimientoSearchRepository.deleteById(id);
    }

    /**
     * Search for the detalleMovimiento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetalleMovimientoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DetalleMovimientos for query {}", query);
        return detalleMovimientoSearchRepository.search(queryStringQuery(query), pageable)
            .map(detalleMovimientoMapper::toDto);
    }

    /**
     *
     * @param pageable
     * @param movimientoId
     * @return
     */
    @Override
    public Page<DetalleMovimientoDTO> findAllByMovimiento(Pageable pageable, Long movimientoId) {
        Movimientos movimientos = this.movimientosRepository.getOne(movimientoId);
        return detalleMovimientoRepository.findAllByMovimientos(pageable, movimientos)
            .map(detalleMovimientoMapper::toDto);
    }
}
