package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.BarrilService;
import com.craftbeerstore.application.domain.Barril;
import com.craftbeerstore.application.repository.BarrilRepository;
import com.craftbeerstore.application.service.dto.BarrilDTO;
import com.craftbeerstore.application.service.mapper.BarrilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Barril.
 */
@Service
@Transactional
public class BarrilServiceImpl implements BarrilService {

  private final Logger log = LoggerFactory.getLogger(BarrilServiceImpl.class);

  private final BarrilRepository barrilRepository;

  private final BarrilMapper barrilMapper;

  private final EmpresaRepository empresaRepository;

  public BarrilServiceImpl(BarrilRepository barrilRepository, BarrilMapper barrilMapper,
                           EmpresaRepository empresaRepository) {
    this.barrilRepository = barrilRepository;
    this.barrilMapper = barrilMapper;
    this.empresaRepository = empresaRepository;
  }

  /**
   * Save a barril.
   *
   * @param barrilDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public BarrilDTO save(BarrilDTO barrilDTO) {
    log.debug("Request to save Barril : {}", barrilDTO);
    Barril barril = barrilMapper.toEntity(barrilDTO);
    barril = barrilRepository.save(barril);
    return barrilMapper.toDto(barril);
  }

  /**
   * Get all the barrils.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<BarrilDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Barrils");
    return barrilRepository.findAll(pageable)
      .map(barrilMapper::toDto);
  }

  /**
   * Get all the barrils.
   *
   * @param pageable  the pagination information
   * @param empresaId the the id of empresa
   * @return the list of entities
   */
  @Override
  public Page<BarrilDTO> findAll(Pageable pageable, Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    return barrilRepository.findAllByEmpresa(pageable, empresa).map(barrilMapper::toDto);
  }


  /**
   * Get one barril by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<BarrilDTO> findOne(Long id) {
    log.debug("Request to get Barril : {}", id);
    return barrilRepository.findById(id)
      .map(barrilMapper::toDto);
  }

  /**
   * Delete the barril by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Barril : {}", id);
    barrilRepository.deleteById(id);
  }
}
