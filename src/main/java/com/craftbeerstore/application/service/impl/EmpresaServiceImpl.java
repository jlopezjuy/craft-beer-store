package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.EmpresaService;
import com.craftbeerstore.application.service.dto.EmpresaDTO;
import com.craftbeerstore.application.service.mapper.EmpresaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Empresa.
 */
@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

  private final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

  private final EmpresaRepository empresaRepository;

  private final EmpresaMapper empresaMapper;

  public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
    this.empresaRepository = empresaRepository;
    this.empresaMapper = empresaMapper;
  }

  /**
   * Save a empresa.
   *
   * @param empresaDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public EmpresaDTO save(EmpresaDTO empresaDTO) {
    log.debug("Request to save Empresa : {}", empresaDTO);
    Empresa empresa = empresaMapper.toEntity(empresaDTO);
    empresa = empresaRepository.save(empresa);
    EmpresaDTO result = empresaMapper.toDto(empresa);
    return result;
  }

  /**
   * Get all the empresas.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<EmpresaDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Empresas");
    return empresaRepository.findByUserIsCurrentUser(pageable)
      .map(empresaMapper::toDto);
  }


  /**
   * Get one empresa by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<EmpresaDTO> findOne(Long id) {
    log.debug("Request to get Empresa : {}", id);
    return empresaRepository.findById(id)
      .map(empresaMapper::toDto);
  }

  /**
   * Delete the empresa by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Empresa : {}", id);
    empresaRepository.deleteById(id);
  }


  @Override
  @Transactional(readOnly = true)
  public Optional<EmpresaDTO> findOne() {
    return empresaRepository.findByUserIsCurrentUser().map(empresaMapper::toDto);
  }

  @Override
  public Optional<EmpresaDTO> findOneByEmail(String email) {
    return empresaRepository.findByCorreo(email).map(empresaMapper::toDto);
  }
}
