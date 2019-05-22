package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.EquipamientoService;
import com.craftbeerstore.application.domain.Equipamiento;
import com.craftbeerstore.application.repository.EquipamientoRepository;
import com.craftbeerstore.application.repository.search.EquipamientoSearchRepository;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;
import com.craftbeerstore.application.service.mapper.EquipamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Equipamiento.
 */
@Service
@Transactional
public class EquipamientoServiceImpl implements EquipamientoService {

    private final Logger log = LoggerFactory.getLogger(EquipamientoServiceImpl.class);

    private final EquipamientoRepository equipamientoRepository;

    private final EquipamientoMapper equipamientoMapper;

    private final EquipamientoSearchRepository equipamientoSearchRepository;

    private final EmpresaRepository empresaRepository;

    public EquipamientoServiceImpl(EquipamientoRepository equipamientoRepository,
        EquipamientoMapper equipamientoMapper,
        EquipamientoSearchRepository equipamientoSearchRepository,
        EmpresaRepository empresaRepository) {
        this.equipamientoRepository = equipamientoRepository;
        this.equipamientoMapper = equipamientoMapper;
        this.equipamientoSearchRepository = equipamientoSearchRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a equipamiento.
     *
     * @param equipamientoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipamientoDTO save(EquipamientoDTO equipamientoDTO) {
        log.debug("Request to save Equipamiento : {}", equipamientoDTO);
        Equipamiento equipamiento = equipamientoMapper.toEntity(equipamientoDTO);
        equipamiento = equipamientoRepository.save(equipamiento);
        EquipamientoDTO result = equipamientoMapper.toDto(equipamiento);
        equipamientoSearchRepository.save(equipamiento);
        return result;
    }

    /**
     * Get all the equipamientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EquipamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipamientos");
        return equipamientoRepository.findAll(pageable)
            .map(equipamientoMapper::toDto);
    }

    @Override
    public Page<EquipamientoDTO> findAll(Pageable pageable, Long empresaId) {
        log.debug("Request to get all Equipamientos");
        Empresa empresa = empresaRepository.getOne(empresaId);
        return equipamientoRepository.findAllByEmpresa(pageable, empresa)
            .map(equipamientoMapper::toDto);
    }


    /**
     * Get one equipamiento by id.
     *
     * @param id the id of the entity
     * @return the entity
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
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipamiento : {}", id);
        equipamientoRepository.deleteById(id);
        equipamientoSearchRepository.deleteById(id);
    }

    /**
     * Search for the equipamiento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EquipamientoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Equipamientos for query {}", query);
        return equipamientoSearchRepository.search(queryStringQuery(query), pageable)
            .map(equipamientoMapper::toDto);
    }
}
