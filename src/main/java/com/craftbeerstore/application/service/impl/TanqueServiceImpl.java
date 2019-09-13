package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Tanque;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.repository.TanqueRepository;
import com.craftbeerstore.application.service.TanqueService;
import com.craftbeerstore.application.service.dto.TanqueDTO;
import com.craftbeerstore.application.service.mapper.TanqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Tanque.
 */
@Service
@Transactional
public class TanqueServiceImpl implements TanqueService {

  private final Logger log = LoggerFactory.getLogger(TanqueServiceImpl.class);

  private final TanqueRepository tanqueRepository;

  private final TanqueMapper tanqueMapper;

  private final EmpresaRepository empresaRepository;

  public TanqueServiceImpl(TanqueRepository tanqueRepository, TanqueMapper tanqueMapper, EmpresaRepository empresaRepository) {
    this.tanqueRepository = tanqueRepository;
    this.tanqueMapper = tanqueMapper;
    this.empresaRepository = empresaRepository;
  }

  /**
   * Save a tanque.
   *
   * @param tanqueDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public TanqueDTO save(TanqueDTO tanqueDTO) {
    log.debug("Request to save Tanque : {}", tanqueDTO);
    Tanque tanque = tanqueMapper.toEntity(tanqueDTO);
    tanque = tanqueRepository.save(tanque);
    return tanqueMapper.toDto(tanque);
  }

  /**
   * Get all the tanques.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<TanqueDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Tanques");
    return tanqueRepository.findAll(pageable)
      .map(tanqueMapper::toDto);
  }

  @Override
  public Page<TanqueDTO> findAll(Pageable pageable, Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    return tanqueRepository.findAllByEmpresa(pageable, empresa).map(tanqueMapper::toDto);
  }


  /**
   * Get one tanque by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<TanqueDTO> findOne(Long id) {
    log.debug("Request to get Tanque : {}", id);
    return tanqueRepository.findById(id)
      .map(tanqueMapper::toDto);
  }

  /**
   * Delete the tanque by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Tanque : {}", id);
    tanqueRepository.deleteById(id);
  }
}
