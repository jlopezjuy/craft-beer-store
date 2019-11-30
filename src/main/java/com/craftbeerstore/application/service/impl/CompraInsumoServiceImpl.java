package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.domain.enumeration.EstadoCompra;
import com.craftbeerstore.application.repository.CompraInsumoDetalleRepository;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.service.CompraInsumoService;
import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.repository.CompraInsumoRepository;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
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
 * Service Implementation for managing {@link CompraInsumo}.
 */
@Service
@Transactional
public class CompraInsumoServiceImpl implements CompraInsumoService {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoServiceImpl.class);

    private final CompraInsumoRepository compraInsumoRepository;

    private final CompraInsumoMapper compraInsumoMapper;

    private final EmpresaRepository empresaRepository;

    private final CompraInsumoDetalleRepository compraInsumoDetalleRepository;

    private final InsumoRepository insumoRepository;

    public CompraInsumoServiceImpl(CompraInsumoRepository compraInsumoRepository, CompraInsumoMapper compraInsumoMapper, EmpresaRepository empresaRepository, CompraInsumoDetalleRepository compraInsumoDetalleRepository, InsumoRepository insumoRepository) {
        this.compraInsumoRepository = compraInsumoRepository;
        this.compraInsumoMapper = compraInsumoMapper;
        this.empresaRepository = empresaRepository;
        this.compraInsumoDetalleRepository = compraInsumoDetalleRepository;
        this.insumoRepository = insumoRepository;
    }

    /**
     * Save a compraInsumo.
     *
     * @param compraInsumoDTO the entity to save.
     * @return the persisted entity.
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

    /**
     * Get all the compraInsumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
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
