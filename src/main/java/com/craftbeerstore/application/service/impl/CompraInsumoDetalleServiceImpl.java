package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.repository.CompraInsumoRepository;
import com.craftbeerstore.application.service.CompraInsumoDetalleService;
import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import com.craftbeerstore.application.repository.CompraInsumoDetalleRepository;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;
import com.craftbeerstore.application.service.mapper.CompraInsumoDetalleMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CompraInsumoDetalle.
 */
@Service
@Transactional
public class CompraInsumoDetalleServiceImpl implements CompraInsumoDetalleService {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoDetalleServiceImpl.class);

    private final CompraInsumoDetalleRepository compraInsumoDetalleRepository;

    private final CompraInsumoDetalleMapper compraInsumoDetalleMapper;

    private final CompraInsumoRepository compraInsumoRepository;

    public CompraInsumoDetalleServiceImpl(CompraInsumoDetalleRepository compraInsumoDetalleRepository,
        CompraInsumoDetalleMapper compraInsumoDetalleMapper,
        CompraInsumoRepository compraInsumoRepository) {
        this.compraInsumoDetalleRepository = compraInsumoDetalleRepository;
        this.compraInsumoDetalleMapper = compraInsumoDetalleMapper;
        this.compraInsumoRepository = compraInsumoRepository;
    }

    /**
     * Save a compraInsumoDetalle.
     *
     * @param compraInsumoDetalleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompraInsumoDetalleDTO save(CompraInsumoDetalleDTO compraInsumoDetalleDTO) {
        log.debug("Request to save CompraInsumoDetalle : {}", compraInsumoDetalleDTO);
        CompraInsumoDetalle compraInsumoDetalle = compraInsumoDetalleMapper.toEntity(compraInsumoDetalleDTO);
        compraInsumoDetalle = compraInsumoDetalleRepository.save(compraInsumoDetalle);
        return compraInsumoDetalleMapper.toDto(compraInsumoDetalle);
    }

    /**
     * Get all the compraInsumoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraInsumoDetalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompraInsumoDetalles");
        return compraInsumoDetalleRepository.findAll(pageable)
            .map(compraInsumoDetalleMapper::toDto);
    }


    /**
     * Get one compraInsumoDetalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompraInsumoDetalleDTO> findOne(Long id) {
        log.debug("Request to get CompraInsumoDetalle : {}", id);
        return compraInsumoDetalleRepository.findById(id)
            .map(compraInsumoDetalleMapper::toDto);
    }

    /**
     * Delete the compraInsumoDetalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompraInsumoDetalle : {}", id);
        compraInsumoDetalleRepository.deleteById(id);
    }

    @Override
    public List<CompraInsumoDetalleDTO> findAll(Long compraInsumoId) {
        CompraInsumo compraInsumo = this.compraInsumoRepository.getOne(compraInsumoId);
        return this.compraInsumoDetalleMapper.toDto(this.compraInsumoDetalleRepository.findAllByCompraInsumo(compraInsumo));
    }

    /**
     *
     * @param comprasInsumoDetalleDTO
     * @return
     */
    @Override
    public List<CompraInsumoDetalleDTO> save(List<CompraInsumoDetalleDTO> comprasInsumoDetalleDTO) {
        List<CompraInsumoDetalle> list = this.compraInsumoDetalleRepository.saveAll(this.compraInsumoDetalleMapper.toEntity(comprasInsumoDetalleDTO));
        return this.compraInsumoDetalleMapper.toDto(list);
    }
}
