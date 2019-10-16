package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.MovimientoTanqueService;
import com.craftbeerstore.application.domain.MovimientoTanque;
import com.craftbeerstore.application.repository.MovimientoTanqueRepository;
import com.craftbeerstore.application.service.dto.MovimientoTanqueDTO;
import com.craftbeerstore.application.service.mapper.MovimientoTanqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MovimientoTanque.
 */
@Service
@Transactional
public class MovimientoTanqueServiceImpl implements MovimientoTanqueService {

    private final Logger log = LoggerFactory.getLogger(MovimientoTanqueServiceImpl.class);

    private final MovimientoTanqueRepository movimientoTanqueRepository;

    private final MovimientoTanqueMapper movimientoTanqueMapper;

    public MovimientoTanqueServiceImpl(MovimientoTanqueRepository movimientoTanqueRepository, MovimientoTanqueMapper movimientoTanqueMapper) {
        this.movimientoTanqueRepository = movimientoTanqueRepository;
        this.movimientoTanqueMapper = movimientoTanqueMapper;
    }

    /**
     * Save a movimientoTanque.
     *
     * @param movimientoTanqueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovimientoTanqueDTO save(MovimientoTanqueDTO movimientoTanqueDTO) {
        log.debug("Request to save MovimientoTanque : {}", movimientoTanqueDTO);
        MovimientoTanque movimientoTanque = movimientoTanqueMapper.toEntity(movimientoTanqueDTO);
        movimientoTanque = movimientoTanqueRepository.save(movimientoTanque);
        return movimientoTanqueMapper.toDto(movimientoTanque);
    }

    /**
     * Get all the movimientoTanques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoTanqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovimientoTanques");
        return movimientoTanqueRepository.findAll(pageable)
            .map(movimientoTanqueMapper::toDto);
    }


    /**
     * Get one movimientoTanque by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientoTanqueDTO> findOne(Long id) {
        log.debug("Request to get MovimientoTanque : {}", id);
        return movimientoTanqueRepository.findById(id)
            .map(movimientoTanqueMapper::toDto);
    }

    /**
     * Delete the movimientoTanque by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovimientoTanque : {}", id);
        movimientoTanqueRepository.deleteById(id);
    }
}
