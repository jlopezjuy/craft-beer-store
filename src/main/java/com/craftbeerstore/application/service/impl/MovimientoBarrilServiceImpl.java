package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.MovimientoBarrilService;
import com.craftbeerstore.application.domain.MovimientoBarril;
import com.craftbeerstore.application.repository.MovimientoBarrilRepository;
import com.craftbeerstore.application.service.dto.MovimientoBarrilDTO;
import com.craftbeerstore.application.service.mapper.MovimientoBarrilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MovimientoBarril}.
 */
@Service
@Transactional
public class MovimientoBarrilServiceImpl implements MovimientoBarrilService {

    private final Logger log = LoggerFactory.getLogger(MovimientoBarrilServiceImpl.class);

    private final MovimientoBarrilRepository movimientoBarrilRepository;

    private final MovimientoBarrilMapper movimientoBarrilMapper;

    public MovimientoBarrilServiceImpl(MovimientoBarrilRepository movimientoBarrilRepository, MovimientoBarrilMapper movimientoBarrilMapper) {
        this.movimientoBarrilRepository = movimientoBarrilRepository;
        this.movimientoBarrilMapper = movimientoBarrilMapper;
    }

    /**
     * Save a movimientoBarril.
     *
     * @param movimientoBarrilDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MovimientoBarrilDTO save(MovimientoBarrilDTO movimientoBarrilDTO) {
        log.debug("Request to save MovimientoBarril : {}", movimientoBarrilDTO);
        MovimientoBarril movimientoBarril = movimientoBarrilMapper.toEntity(movimientoBarrilDTO);
        movimientoBarril = movimientoBarrilRepository.save(movimientoBarril);
        return movimientoBarrilMapper.toDto(movimientoBarril);
    }

    /**
     * Get all the movimientoBarrils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoBarrilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovimientoBarrils");
        return movimientoBarrilRepository.findAll(pageable)
            .map(movimientoBarrilMapper::toDto);
    }


    /**
     * Get one movimientoBarril by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientoBarrilDTO> findOne(Long id) {
        log.debug("Request to get MovimientoBarril : {}", id);
        return movimientoBarrilRepository.findById(id)
            .map(movimientoBarrilMapper::toDto);
    }

    /**
     * Delete the movimientoBarril by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovimientoBarril : {}", id);
        movimientoBarrilRepository.deleteById(id);
    }
}
