package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.CompraInsumoService;
import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.repository.CompraInsumoRepository;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
import com.craftbeerstore.application.service.mapper.CompraInsumoMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CompraInsumoServiceImpl(CompraInsumoRepository compraInsumoRepository, CompraInsumoMapper compraInsumoMapper,
        EmpresaRepository empresaRepository) {
        this.compraInsumoRepository = compraInsumoRepository;
        this.compraInsumoMapper = compraInsumoMapper;
        this.empresaRepository = empresaRepository;
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
        CompraInsumo compraInsumo = compraInsumoMapper.toEntity(compraInsumoDTO);
        compraInsumo = compraInsumoRepository.save(compraInsumo);
        return compraInsumoMapper.toDto(compraInsumo);
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
