package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.InsumoService;
import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.service.dto.InsumoDTO;
import com.craftbeerstore.application.service.mapper.InsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Insumo}.
 */
@Service
@Transactional
public class InsumoServiceImpl implements InsumoService {

    private final Logger log = LoggerFactory.getLogger(InsumoServiceImpl.class);

    private final InsumoRepository insumoRepository;

    private final InsumoMapper insumoMapper;

    public InsumoServiceImpl(InsumoRepository insumoRepository, InsumoMapper insumoMapper) {
        this.insumoRepository = insumoRepository;
        this.insumoMapper = insumoMapper;
    }

    /**
     * Save a insumo.
     *
     * @param insumoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InsumoDTO save(InsumoDTO insumoDTO) {
        log.debug("Request to save Insumo : {}", insumoDTO);
        Insumo insumo = insumoMapper.toEntity(insumoDTO);
        insumo = insumoRepository.save(insumo);
        return insumoMapper.toDto(insumo);
    }

    /**
     * Get all the insumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insumos");
        return insumoRepository.findAll(pageable)
            .map(insumoMapper::toDto);
    }


    /**
     * Get one insumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InsumoDTO> findOne(Long id) {
        log.debug("Request to get Insumo : {}", id);
        return insumoRepository.findById(id)
            .map(insumoMapper::toDto);
    }

    /**
     * Delete the insumo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insumo : {}", id);
        insumoRepository.deleteById(id);
    }
}
