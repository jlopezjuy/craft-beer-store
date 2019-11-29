package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.LoteService;
import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.repository.LoteRepository;
import com.craftbeerstore.application.service.dto.LoteDTO;
import com.craftbeerstore.application.service.mapper.LoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lote}.
 */
@Service
@Transactional
public class LoteServiceImpl implements LoteService {

    private final Logger log = LoggerFactory.getLogger(LoteServiceImpl.class);

    private final LoteRepository loteRepository;

    private final LoteMapper loteMapper;

    public LoteServiceImpl(LoteRepository loteRepository, LoteMapper loteMapper) {
        this.loteRepository = loteRepository;
        this.loteMapper = loteMapper;
    }

    /**
     * Save a lote.
     *
     * @param loteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LoteDTO save(LoteDTO loteDTO) {
        log.debug("Request to save Lote : {}", loteDTO);
        Lote lote = loteMapper.toEntity(loteDTO);
        lote = loteRepository.save(lote);
        return loteMapper.toDto(lote);
    }

    /**
     * Get all the lotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lotes");
        return loteRepository.findAll(pageable)
            .map(loteMapper::toDto);
    }


    /**
     * Get one lote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoteDTO> findOne(Long id) {
        log.debug("Request to get Lote : {}", id);
        return loteRepository.findById(id)
            .map(loteMapper::toDto);
    }

    /**
     * Delete the lote by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lote : {}", id);
        loteRepository.deleteById(id);
    }
}
