package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.MovimientosService;
import com.craftbeerstore.application.domain.Movimientos;
import com.craftbeerstore.application.repository.MovimientosRepository;
import com.craftbeerstore.application.service.dto.MovimientosDTO;
import com.craftbeerstore.application.service.mapper.MovimientosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Movimientos}.
 */
@Service
@Transactional
public class MovimientosServiceImpl implements MovimientosService {

    private final Logger log = LoggerFactory.getLogger(MovimientosServiceImpl.class);

    private final MovimientosRepository movimientosRepository;

    private final MovimientosMapper movimientosMapper;

    public MovimientosServiceImpl(MovimientosRepository movimientosRepository, MovimientosMapper movimientosMapper) {
        this.movimientosRepository = movimientosRepository;
        this.movimientosMapper = movimientosMapper;
    }

    /**
     * Save a movimientos.
     *
     * @param movimientosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MovimientosDTO save(MovimientosDTO movimientosDTO) {
        log.debug("Request to save Movimientos : {}", movimientosDTO);
        Movimientos movimientos = movimientosMapper.toEntity(movimientosDTO);
        movimientos = movimientosRepository.save(movimientos);
        return movimientosMapper.toDto(movimientos);
    }

    /**
     * Get all the movimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovimientosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movimientos");
        return movimientosRepository.findAll(pageable)
            .map(movimientosMapper::toDto);
    }


    /**
     * Get one movimientos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientosDTO> findOne(Long id) {
        log.debug("Request to get Movimientos : {}", id);
        return movimientosRepository.findById(id)
            .map(movimientosMapper::toDto);
    }

    /**
     * Delete the movimientos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movimientos : {}", id);
        movimientosRepository.deleteById(id);
    }
}
