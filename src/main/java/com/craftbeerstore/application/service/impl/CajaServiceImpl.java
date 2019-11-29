package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.CajaService;
import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.repository.CajaRepository;
import com.craftbeerstore.application.service.dto.CajaDTO;
import com.craftbeerstore.application.service.mapper.CajaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Caja}.
 */
@Service
@Transactional
public class CajaServiceImpl implements CajaService {

    private final Logger log = LoggerFactory.getLogger(CajaServiceImpl.class);

    private final CajaRepository cajaRepository;

    private final CajaMapper cajaMapper;

    public CajaServiceImpl(CajaRepository cajaRepository, CajaMapper cajaMapper) {
        this.cajaRepository = cajaRepository;
        this.cajaMapper = cajaMapper;
    }

    /**
     * Save a caja.
     *
     * @param cajaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CajaDTO save(CajaDTO cajaDTO) {
        log.debug("Request to save Caja : {}", cajaDTO);
        Caja caja = cajaMapper.toEntity(cajaDTO);
        caja = cajaRepository.save(caja);
        return cajaMapper.toDto(caja);
    }

    /**
     * Get all the cajas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CajaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cajas");
        return cajaRepository.findAll(pageable)
            .map(cajaMapper::toDto);
    }


    /**
     * Get one caja by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CajaDTO> findOne(Long id) {
        log.debug("Request to get Caja : {}", id);
        return cajaRepository.findById(id)
            .map(cajaMapper::toDto);
    }

    /**
     * Delete the caja by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caja : {}", id);
        cajaRepository.deleteById(id);
    }
}
