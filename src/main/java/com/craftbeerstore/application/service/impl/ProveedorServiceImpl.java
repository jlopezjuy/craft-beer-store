package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Proveedor;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.repository.ProveedorRepository;
import com.craftbeerstore.application.service.ProveedorService;
import com.craftbeerstore.application.service.dto.ProveedorDTO;
import com.craftbeerstore.application.service.mapper.ProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Proveedor.
 */
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

  private final Logger log = LoggerFactory.getLogger(ProveedorServiceImpl.class);

  private final ProveedorRepository proveedorRepository;

  private final ProveedorMapper proveedorMapper;


  private final EmpresaRepository empresaRepository;

  public ProveedorServiceImpl(ProveedorRepository proveedorRepository,
                              ProveedorMapper proveedorMapper,
                              EmpresaRepository empresaRepository) {
    this.proveedorRepository = proveedorRepository;
    this.proveedorMapper = proveedorMapper;
    this.empresaRepository = empresaRepository;
  }

  /**
   * Save a proveedor.
   *
   * @param proveedorDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public ProveedorDTO save(ProveedorDTO proveedorDTO) {
    log.debug("Request to save Proveedor : {}", proveedorDTO);
    Proveedor proveedor = proveedorMapper.toEntity(proveedorDTO);
    proveedor = proveedorRepository.save(proveedor);
    ProveedorDTO result = proveedorMapper.toDto(proveedor);
    return result;
  }

  /**
   * Get all the proveedors.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<ProveedorDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Proveedors");
    return proveedorRepository.findAll(pageable)
      .map(proveedorMapper::toDto);
  }

  /**
   * @param pageable
   * @param empresaId
   * @return
   */
  @Override
  public Page<ProveedorDTO> findAllByEmpresa(Pageable pageable, Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    return proveedorRepository.findAllByEmpresa(pageable, empresa).map(proveedorMapper::toDto);
  }


  /**
   * Get one proveedor by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<ProveedorDTO> findOne(Long id) {
    log.debug("Request to get Proveedor : {}", id);
    return proveedorRepository.findById(id)
      .map(proveedorMapper::toDto);
  }

  /**
   * Delete the proveedor by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete Proveedor : {}", id);
    proveedorRepository.deleteById(id);
  }
}
