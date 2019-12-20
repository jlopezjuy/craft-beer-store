package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.domain.enumeration.EstadoCompra;
import com.craftbeerstore.application.repository.*;
import com.craftbeerstore.application.service.CompraInsumoService;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
import com.craftbeerstore.application.service.dto.CompraInsumoDetailsDTO;
import com.craftbeerstore.application.service.mapper.CompraInsumoDetalleMapper;
import com.craftbeerstore.application.service.mapper.CompraInsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CompraInsumo.
 */
@Service
@Transactional
public class CompraInsumoServiceImpl implements CompraInsumoService {

  private final Logger log = LoggerFactory.getLogger(CompraInsumoServiceImpl.class);

  private final CompraInsumoRepository compraInsumoRepository;

  private final CompraInsumoMapper compraInsumoMapper;

  private final EmpresaRepository empresaRepository;

  private final CompraInsumoDetalleRepository compraInsumoDetalleRepository;

  private final CompraInsumoDetalleMapper compraInsumoDetalleMapper;

  private final InsumoRepository insumoRepository;

  private final InsumoRecomendadoRepository insumoRecomendadoRepository;

  public CompraInsumoServiceImpl(CompraInsumoRepository compraInsumoRepository, CompraInsumoMapper compraInsumoMapper,
                                 EmpresaRepository empresaRepository, CompraInsumoDetalleRepository compraInsumoDetalleRepository, CompraInsumoDetalleMapper compraInsumoDetalleMapper, InsumoRepository insumoRepository, InsumoRecomendadoRepository insumoRecomendadoRepository) {
    this.compraInsumoRepository = compraInsumoRepository;
    this.compraInsumoMapper = compraInsumoMapper;
    this.empresaRepository = empresaRepository;
    this.compraInsumoDetalleRepository = compraInsumoDetalleRepository;
    this.compraInsumoDetalleMapper = compraInsumoDetalleMapper;
    this.insumoRepository = insumoRepository;
    this.insumoRecomendadoRepository = insumoRecomendadoRepository;
  }

  /**
   * Save a compraInsumo.
   *
   * @param compraInsumoDTO the entity to save
   * @return the persisted entity
   */
  @Override
  public CompraInsumoDTO save(CompraInsumoDTO compraInsumoDTO) {
    log.debug("Request to save CompraInsumo : {}", compraInsumoDTO);
    final CompraInsumo compraInsumo = compraInsumoMapper.toEntity(compraInsumoDTO);
    if (compraInsumo.getEstadoCompra().equals(EstadoCompra.PEDIDO_RECIBIDO)) {
      List<CompraInsumoDetalle> detalles = this.compraInsumoDetalleRepository.findAllByCompraInsumo(compraInsumo);
      detalles.forEach(detalle -> {
        if (null == detalle.getInsumoRecomendado()) {
          Insumo insumo = this.insumoRepository.findByNombreInsumoAndEmpresaAndUnidad(detalle.getCodigoReferencia(), compraInsumo.getEmpresa(), detalle.getUnidad());
          if (null == insumo) {
            insumo = new Insumo();
            insumo.setNombreInsumo(detalle.getCodigoReferencia());
            insumo.setMarca("");
            insumo.setEmpresa(compraInsumo.getEmpresa());
            insumo.setStock(detalle.getStock());
            insumo.setUnidad(detalle.getUnidad());
            insumo.setTipo(detalle.getTipo());
            insumo.setPrecioUnitario(detalle.getPrecioUnitario());
            insumo.setPrecioTotal(detalle.getPrecioTotal());
          } else {
            insumo.setStock(insumo.getStock().add(detalle.getStock()));
          }
          this.insumoRepository.save(insumo);
        } else {
          Insumo insumo = this.insumoRepository.findByNombreInsumoAndEmpresaAndUnidad(detalle.getInsumoRecomendado().getNombre(), compraInsumo.getEmpresa(), detalle.getUnidad());
          if (null == insumo) {
            insumo = new Insumo();
            insumo.setEmpresa(compraInsumo.getEmpresa());
            insumo.setInsumoRecomendado(detalle.getInsumoRecomendado());
            insumo.setNombreInsumo(detalle.getInsumoRecomendado().getNombre());
            insumo.setMarca(detalle.getInsumoRecomendado().getMarca());
            insumo.setStock(detalle.getStock());
            insumo.setUnidad(detalle.getUnidad());
            insumo.setTipo(detalle.getTipo());
            insumo.setPrecioUnitario(detalle.getPrecioUnitario());
            insumo.setPrecioTotal(detalle.getPrecioTotal());
          } else {
            insumo.setStock(insumo.getStock().add(detalle.getStock()));
          }
          this.insumoRepository.save(insumo);
        }
      });
    }
    CompraInsumo compraInsumoSalida = compraInsumoRepository.save(compraInsumo);
    return compraInsumoMapper.toDto(compraInsumoSalida);
  }

  @Override
  public CompraInsumoDTO save(CompraInsumoDetailsDTO compraInsumoDetailsDTO) {
    CompraInsumoDTO compraInsumoDTO = new CompraInsumoDTO();
    compraInsumoDTO.setId(compraInsumoDetailsDTO.getId());
    compraInsumoDTO.setNroFactura(compraInsumoDetailsDTO.getNroFactura());
    compraInsumoDTO.setFecha(compraInsumoDetailsDTO.getFecha());
    compraInsumoDTO.setSubtotal(compraInsumoDetailsDTO.getSubtotal());
    compraInsumoDTO.setGastoDeEnvio(compraInsumoDetailsDTO.getGastoDeEnvio());
    compraInsumoDTO.setImpuesto(compraInsumoDetailsDTO.getImpuesto());
    compraInsumoDTO.setTotal(compraInsumoDetailsDTO.getTotal());
    compraInsumoDTO.setEstadoCompra(compraInsumoDetailsDTO.getEstadoCompra());
    compraInsumoDTO.setProveedorId(compraInsumoDetailsDTO.getProveedorId());
    compraInsumoDTO.setProveedorNombreProveedor(compraInsumoDetailsDTO.getProveedorNombreProveedor());
    compraInsumoDTO.setEmpresaId(compraInsumoDetailsDTO.getEmpresaId());
    compraInsumoDTO.setEmpresaNombreEmpresa(compraInsumoDetailsDTO.getEmpresaNombreEmpresa());
    final CompraInsumo compraInsumo = compraInsumoMapper.toEntity(compraInsumoDTO);
    CompraInsumo compraInsumoSalida = compraInsumoRepository.save(compraInsumo);
    if(compraInsumoSalida.getId() != null){
      List<CompraInsumoDetalle> detalles = this.compraInsumoDetalleRepository.saveAll(this.compraInsumoDetalleMapper.toEntity(compraInsumoDetailsDTO.getCompraInsumoDetalleList()));
      detalles.forEach(detalle -> {
        InsumoRecomendado insumoRecomendado = this.insumoRecomendadoRepository.getOne(detalle.getInsumoRecomendado().getId());
        if (null == insumoRecomendado) {
          Insumo insumo = this.insumoRepository.findByNombreInsumoAndEmpresaAndUnidad(detalle.getCodigoReferencia(), compraInsumo.getEmpresa(), detalle.getUnidad());
          if (null == insumo) {
            insumo = new Insumo();
            insumo.setNombreInsumo(detalle.getCodigoReferencia());
            insumo.setMarca("");
            insumo.setEmpresa(compraInsumo.getEmpresa());
            insumo.setStock(detalle.getStock());
            insumo.setUnidad(detalle.getUnidad());
            insumo.setTipo(detalle.getTipo());
            insumo.setPrecioUnitario(detalle.getPrecioUnitario());
            insumo.setPrecioTotal(detalle.getPrecioTotal());
          } else {
            insumo.setStock(insumo.getStock().add(detalle.getStock()));
          }
          this.insumoRepository.save(insumo);
        } else {
          Insumo insumo = this.insumoRepository.findByNombreInsumoAndEmpresaAndUnidad(insumoRecomendado.getNombre(), compraInsumo.getEmpresa(), detalle.getUnidad());
          if (null == insumo) {
            insumo = new Insumo();
            insumo.setEmpresa(compraInsumo.getEmpresa());
            insumo.setInsumoRecomendado(insumoRecomendado);
            insumo.setNombreInsumo(insumoRecomendado.getNombre());
            insumo.setMarca(insumoRecomendado.getMarca());
            insumo.setStock(detalle.getStock());
            insumo.setUnidad(detalle.getUnidad());
            insumo.setTipo(detalle.getTipo());
            insumo.setPrecioUnitario(detalle.getPrecioUnitario());
            insumo.setPrecioTotal(detalle.getPrecioTotal());
          } else {
            insumo.setStock(insumo.getStock().add(detalle.getStock()));
          }
          this.insumoRepository.save(insumo);
        }
      });
    }

    return compraInsumoMapper.toDto(compraInsumoSalida);
  }

  /**
   * Get all the compraInsumos.
   *
   * @param pageable the pagination information
   * @return the list of entities
   */
  @Override
  @Transactional(readOnly = true)
  public Page<CompraInsumoDTO> findAll(Pageable pageable) {
    log.debug("Request to get all CompraInsumos");
    return compraInsumoRepository.findAll(pageable)
      .map(compraInsumoMapper::toDto);
  }


  /**
   * Get one compraInsumo by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<CompraInsumoDTO> findOne(Long id) {
    log.debug("Request to get CompraInsumo : {}", id);
    return compraInsumoRepository.findById(id)
      .map(compraInsumoMapper::toDto);
  }

  /**
   * Delete the compraInsumo by id.
   *
   * @param id the id of the entity
   */
  @Override
  public void delete(Long id) {
    log.debug("Request to delete CompraInsumo : {}", id);
    compraInsumoRepository.deleteById(id);
  }

  @Override
  public Page<CompraInsumoDTO> findAll(Pageable pageable, Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    return compraInsumoRepository.findAllByEmpresa(pageable, empresa)
      .map(compraInsumoMapper::toDto);
  }
}
