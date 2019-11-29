package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.EtapaLoteService;
import com.craftbeerstore.application.domain.EtapaLote;
import com.craftbeerstore.application.repository.EtapaLoteRepository;
import com.craftbeerstore.application.service.dto.EtapaLoteDTO;
import com.craftbeerstore.application.service.mapper.EtapaLoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtapaLote}.
 */
@Service
@Transactional
public class EtapaLoteServiceImpl implements EtapaLoteService {

    private final Logger log = LoggerFactory.getLogger(EtapaLoteServiceImpl.class);

    private final EtapaLoteRepository etapaLoteRepository;

    private final EtapaLoteMapper etapaLoteMapper;

    public EtapaLoteServiceImpl(EtapaLoteRepository etapaLoteRepository, EtapaLoteMapper etapaLoteMapper) {
        this.etapaLoteRepository = etapaLoteRepository;
        this.etapaLoteMapper = etapaLoteMapper;
    }

    /**
     * Save a etapaLote.
     *
     * @param etapaLoteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtapaLoteDTO save(EtapaLoteDTO etapaLoteDTO) {
        log.debug("Request to save EtapaLote : {}", etapaLoteDTO);
        EtapaLote etapaLote = etapaLoteMapper.toEntity(etapaLoteDTO);
        etapaLote = etapaLoteRepository.save(etapaLote);
        return etapaLoteMapper.toDto(etapaLote);
    }

    /**
     * Get all the etapaLotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtapaLoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapaLotes");
        return etapaLoteRepository.findAll(pageable)
            .map(etapaLoteMapper::toDto);
    }


    /**
     * Get one etapaLote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtapaLoteDTO> findOne(Long id) {
        log.debug("Request to get EtapaLote : {}", id);
        return etapaLoteRepository.findById(id)
            .map(etapaLoteMapper::toDto);
    }

    /**
     * Delete the etapaLote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtapaLote : {}", id);
        etapaLoteRepository.deleteById(id);
    }
}
