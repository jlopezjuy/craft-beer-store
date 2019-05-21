package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.repository.ClienteRepository;
import com.craftbeerstore.application.service.PuntoDeVentaService;
import com.craftbeerstore.application.domain.PuntoDeVenta;
import com.craftbeerstore.application.repository.PuntoDeVentaRepository;
import com.craftbeerstore.application.repository.search.PuntoDeVentaSearchRepository;
import com.craftbeerstore.application.service.dto.PuntoDeVentaDTO;
import com.craftbeerstore.application.service.mapper.PuntoDeVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PuntoDeVenta.
 */
@Service
@Transactional
public class PuntoDeVentaServiceImpl implements PuntoDeVentaService {

    private final Logger log = LoggerFactory.getLogger(PuntoDeVentaServiceImpl.class);

    private final PuntoDeVentaRepository puntoDeVentaRepository;

    private final PuntoDeVentaMapper puntoDeVentaMapper;

    private final PuntoDeVentaSearchRepository puntoDeVentaSearchRepository;

    private final ClienteRepository clienteRepository;

    public PuntoDeVentaServiceImpl(PuntoDeVentaRepository puntoDeVentaRepository,
        PuntoDeVentaMapper puntoDeVentaMapper,
        PuntoDeVentaSearchRepository puntoDeVentaSearchRepository,
        ClienteRepository clienteRepository) {
        this.puntoDeVentaRepository = puntoDeVentaRepository;
        this.puntoDeVentaMapper = puntoDeVentaMapper;
        this.puntoDeVentaSearchRepository = puntoDeVentaSearchRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Save a puntoDeVenta.
     *
     * @param puntoDeVentaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PuntoDeVentaDTO save(PuntoDeVentaDTO puntoDeVentaDTO) {
        log.debug("Request to save PuntoDeVenta : {}", puntoDeVentaDTO);
        PuntoDeVenta puntoDeVenta = puntoDeVentaMapper.toEntity(puntoDeVentaDTO);
        puntoDeVenta = puntoDeVentaRepository.save(puntoDeVenta);
        PuntoDeVentaDTO result = puntoDeVentaMapper.toDto(puntoDeVenta);
        puntoDeVentaSearchRepository.save(puntoDeVenta);
        return result;
    }

    /**
     * Get all the puntoDeVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PuntoDeVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PuntoDeVentas");
        return puntoDeVentaRepository.findAll(pageable)
            .map(puntoDeVentaMapper::toDto);
    }

    @Override
    public Page<PuntoDeVentaDTO> findAllByCliente(Pageable pageable, Long clienteId) {
        Cliente cliente = this.clienteRepository.getOne(clienteId);
        return this.puntoDeVentaRepository.findAllByCliente(pageable, cliente).map(this.puntoDeVentaMapper::toDto);
    }


    /**
     * Get one puntoDeVenta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PuntoDeVentaDTO> findOne(Long id) {
        log.debug("Request to get PuntoDeVenta : {}", id);
        return puntoDeVentaRepository.findById(id)
            .map(puntoDeVentaMapper::toDto);
    }

    /**
     * Delete the puntoDeVenta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PuntoDeVenta : {}", id);
        puntoDeVentaRepository.deleteById(id);
        puntoDeVentaSearchRepository.deleteById(id);
    }

    /**
     * Search for the puntoDeVenta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PuntoDeVentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PuntoDeVentas for query {}", query);
        return puntoDeVentaSearchRepository.search(queryStringQuery(query), pageable)
            .map(puntoDeVentaMapper::toDto);
    }
}
