package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Evento;
import com.craftbeerstore.application.repository.EventoRepository;
import com.craftbeerstore.application.service.EventoProductoService;
import com.craftbeerstore.application.domain.EventoProducto;
import com.craftbeerstore.application.repository.EventoProductoRepository;
import com.craftbeerstore.application.repository.search.EventoProductoSearchRepository;
import com.craftbeerstore.application.service.dto.EventoProductoDTO;
import com.craftbeerstore.application.service.mapper.EventoProductoMapper;
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
 * Service Implementation for managing EventoProducto.
 */
@Service
@Transactional
public class EventoProductoServiceImpl implements EventoProductoService {

    private final Logger log = LoggerFactory.getLogger(EventoProductoServiceImpl.class);

    private final EventoProductoRepository eventoProductoRepository;

    private final EventoProductoMapper eventoProductoMapper;

    private final EventoProductoSearchRepository eventoProductoSearchRepository;

    private final EventoRepository eventoRepository;

    public EventoProductoServiceImpl(EventoProductoRepository eventoProductoRepository,
        EventoProductoMapper eventoProductoMapper,
        EventoProductoSearchRepository eventoProductoSearchRepository,
        EventoRepository eventoRepository) {
        this.eventoProductoRepository = eventoProductoRepository;
        this.eventoProductoMapper = eventoProductoMapper;
        this.eventoProductoSearchRepository = eventoProductoSearchRepository;
        this.eventoRepository = eventoRepository;
    }

    /**
     * Save a eventoProducto.
     *
     * @param eventoProductoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventoProductoDTO save(EventoProductoDTO eventoProductoDTO) {
        log.debug("Request to save EventoProducto : {}", eventoProductoDTO);
        EventoProducto eventoProducto = eventoProductoMapper.toEntity(eventoProductoDTO);
        eventoProducto = eventoProductoRepository.save(eventoProducto);
        EventoProductoDTO result = eventoProductoMapper.toDto(eventoProducto);
        eventoProductoSearchRepository.save(eventoProducto);
        return result;
    }

    /**
     * Get all the eventoProductos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventoProductos");
        return eventoProductoRepository.findAll(pageable)
            .map(eventoProductoMapper::toDto);
    }

    @Override
    public List<EventoProductoDTO> findAllByEvento(Long eventoId) {
        Evento evento = this.eventoRepository.getOne(eventoId);
        return eventoProductoMapper.toDto(eventoProductoRepository.findByEvento(evento));
    }

    /**
     * Get one eventoProducto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventoProductoDTO> findOne(Long id) {
        log.debug("Request to get EventoProducto : {}", id);
        return eventoProductoRepository.findById(id)
            .map(eventoProductoMapper::toDto);
    }

    /**
     * Delete the eventoProducto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventoProducto : {}", id);
        eventoProductoRepository.deleteById(id);
        eventoProductoSearchRepository.deleteById(id);
    }

    /**
     * Search for the eventoProducto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoProductoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EventoProductos for query {}", query);
        return eventoProductoSearchRepository.search(queryStringQuery(query), pageable)
            .map(eventoProductoMapper::toDto);
    }
}
