package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.EquipamientoService;
import com.craftbeerstore.application.domain.Equipamiento;
import com.craftbeerstore.application.repository.EquipamientoRepository;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;
import com.craftbeerstore.application.service.mapper.EquipamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Equipamiento}.
 */
@Service
@Transactional
public class EquipamientoServiceImpl implements EquipamientoService {

    private final Logger log = LoggerFactory.getLogger(EquipamientoServiceImpl.class);

    private final EquipamientoRepository equipamientoRepository;

    private final EquipamientoMapper equipamientoMapper;

    public EquipamientoServiceImpl(EquipamientoRepository equipamientoRepository, EquipamientoMapper equipamientoMapper) {
        this.equipamientoRepository = equipamientoRepository;
        this.equipamientoMapper = equipamientoMapper;
    }

    /**
     * Save a equipamiento.
     *
     * @param equipamientoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EquipamientoDTO save(EquipamientoDTO equipamientoDTO) {
        log.debug("Request to save Equipamiento : {}", equipamientoDTO);
        Equipamiento equipamiento = equipamientoMapper.toEntity(equipamientoDTO);
        equipamiento = equipamientoRepository.save(equipamiento);
        return equipamientoMapper.toDto(equipamiento);
    }

    /**
     * Get all the equipamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EquipamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipamientos");
        return equipamientoRepository.findAll(pageable)
            .map(equipamientoMapper::toDto);
    }


    /**
     * Get one equipamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EquipamientoDTO> findOne(Long id) {
        log.debug("Request to get Equipamiento : {}", id);
        return equipamientoRepository.findById(id)
            .map(equipamientoMapper::toDto);
    }

    /**
     * Delete the equipamiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipamiento : {}", id);
        equipamientoRepository.deleteById(id);
    }
}
