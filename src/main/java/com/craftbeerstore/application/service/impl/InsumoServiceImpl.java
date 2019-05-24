package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.InsumoService;
import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.repository.search.InsumoSearchRepository;
import com.craftbeerstore.application.service.dto.InsumoDTO;
import com.craftbeerstore.application.service.mapper.InsumoMapper;
import com.craftbeerstore.application.web.rest.EmpresaResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Insumo.
 */
@Service
@Transactional
public class InsumoServiceImpl implements InsumoService {

    private final Logger log = LoggerFactory.getLogger(InsumoServiceImpl.class);

    private final InsumoRepository insumoRepository;

    private final InsumoMapper insumoMapper;

    private final InsumoSearchRepository insumoSearchRepository;

    private final EmpresaRepository empresaRepository;

    public InsumoServiceImpl(InsumoRepository insumoRepository, InsumoMapper insumoMapper,
        InsumoSearchRepository insumoSearchRepository,
        EmpresaRepository empresaRepository) {
        this.insumoRepository = insumoRepository;
        this.insumoMapper = insumoMapper;
        this.insumoSearchRepository = insumoSearchRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a insumo.
     *
     * @param insumoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsumoDTO save(InsumoDTO insumoDTO) {
        log.debug("Request to save Insumo : {}", insumoDTO);
        Insumo insumo = insumoMapper.toEntity(insumoDTO);
        insumo = insumoRepository.save(insumo);
        InsumoDTO result = insumoMapper.toDto(insumo);
        insumoSearchRepository.save(insumo);
        return result;
    }

    /**
     * Get all the insumos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insumos");
        return insumoRepository.findAll(pageable)
            .map(insumoMapper::toDto);
    }

    /**
     * Get all the insumos by empresa.
     * @param pageable the pagination information
     * @param empresaId the empresa identificator.
     * @return the list of insumos by empresa
     */
    @Override
    public Page<InsumoDTO> findAllByEmpresa(Pageable pageable, Long empresaId) {
        Empresa empresa = empresaRepository.getOne(empresaId);
        return insumoRepository.findAllByEmpresa(pageable, empresa).map(insumoMapper::toDto);
    }

    @Override
    public List<InsumoDTO> findAllByEmpresaAndTipo(Long empresaId, TipoInsumo tipoInsumo) {
        Empresa empresa = empresaRepository.getOne(empresaId);
        return insumoMapper.toDto(insumoRepository.findAllByEmpresaAndTipo(empresa, tipoInsumo));
    }

    /**
     * Get one insumo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InsumoDTO> findOne(Long id) {
        log.debug("Request to get Insumo : {}", id);
        return insumoRepository.findById(id)
            .map(insumoMapper::toDto);
    }

    /**
     * Delete the insumo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insumo : {}", id);
        insumoRepository.deleteById(id);
        insumoSearchRepository.deleteById(id);
    }

    /**
     * Search for the insumo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsumoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Insumos for query {}", query);
        return insumoSearchRepository.search(queryStringQuery(query), pageable)
            .map(insumoMapper::toDto);
    }
}
