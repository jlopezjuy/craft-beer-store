package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
import com.craftbeerstore.application.repository.LoteRepository;
import com.craftbeerstore.application.service.MedicionLoteService;
import com.craftbeerstore.application.domain.MedicionLote;
import com.craftbeerstore.application.repository.MedicionLoteRepository;
import com.craftbeerstore.application.service.dto.MedicionLoteDTO;
import com.craftbeerstore.application.service.mapper.MedicionLoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing MedicionLote.
 */
@Service
@Transactional
public class MedicionLoteServiceImpl implements MedicionLoteService {

    private final Logger log = LoggerFactory.getLogger(MedicionLoteServiceImpl.class);

    private final MedicionLoteRepository medicionLoteRepository;

    private final MedicionLoteMapper medicionLoteMapper;

    private final LoteRepository loteRepository;

    public MedicionLoteServiceImpl(MedicionLoteRepository medicionLoteRepository, MedicionLoteMapper medicionLoteMapper, LoteRepository loteRepository) {
        this.medicionLoteRepository = medicionLoteRepository;
        this.medicionLoteMapper = medicionLoteMapper;
      this.loteRepository = loteRepository;
    }

    /**
     * Save a medicionLote.
     *
     * @param medicionLoteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicionLoteDTO save(MedicionLoteDTO medicionLoteDTO) {
        log.debug("Request to save MedicionLote : {}", medicionLoteDTO);
        MedicionLote medicionLote = medicionLoteMapper.toEntity(medicionLoteDTO);
        medicionLote = medicionLoteRepository.save(medicionLote);
        return medicionLoteMapper.toDto(medicionLote);
    }

  @Override
  public Page<MedicionLoteDTO> findAll(Pageable pageable, Long loteId) {
      Lote lote = this.loteRepository.getOne(loteId);
    return this.medicionLoteRepository.findAllByLoteOrderByFechaRealizadoAsc(pageable, lote).map(medicionLoteMapper::toDto);
  }

  @Override
  public List<MedicionLoteDTO> findAll(Long loteId, TipoMedicion tipoMedicion) {
    Lote lote = this.loteRepository.getOne(loteId);
    log.info("desde");
    log.info(LocalDate.now().minusDays(Long.valueOf(15)).atStartOfDay().toInstant(ZoneOffset.UTC).toString());
    log.info("hasta");
    log.info(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toString());
    //TODO: agregar que la fecha sea desde inicio 00:00 y hasta con 23:59
    return this.medicionLoteMapper.toDto(this.medicionLoteRepository
      .findAllByLoteAndFechaRealizadoGreaterThanEqualAndFechaRealizadoLessThanEqualAndTipoMedicion
      (lote, LocalDate.now().minusDays(Long.valueOf(15)).atStartOfDay().toInstant(ZoneOffset.UTC),
        LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC),
        tipoMedicion));
  }

  /**
     * Get all the medicionLotes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicionLoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicionLotes");
        return medicionLoteRepository.findAll(pageable)
            .map(medicionLoteMapper::toDto);
    }


    /**
     * Get one medicionLote by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicionLoteDTO> findOne(Long id) {
        log.debug("Request to get MedicionLote : {}", id);
        return medicionLoteRepository.findById(id)
            .map(medicionLoteMapper::toDto);
    }

    /**
     * Delete the medicionLote by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicionLote : {}", id);
        medicionLoteRepository.deleteById(id);
    }
}
