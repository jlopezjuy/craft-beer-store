package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.EventoProductoService;
import com.craftbeerstore.application.domain.EventoProducto;
import com.craftbeerstore.application.repository.EventoProductoRepository;
import com.craftbeerstore.application.service.dto.EventoProductoDTO;
import com.craftbeerstore.application.service.mapper.EventoProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventoProducto}.
 */
@Service
@Transactional
public class EventoProductoServiceImpl implements EventoProductoService {

    private final Logger log = LoggerFactory.getLogger(EventoProductoServiceImpl.class);

    private final EventoProductoRepository eventoProductoRepository;

    private final EventoProductoMapper eventoProductoMapper;

    public EventoProductoServiceImpl(EventoProductoRepository eventoProductoRepository, EventoProductoMapper eventoProductoMapper) {
        this.eventoProductoRepository = eventoProductoRepository;
        this.eventoProductoMapper = eventoProductoMapper;
    }

    /**
     * Save a eventoProducto.
     *
     * @param eventoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventoProductoDTO save(EventoProductoDTO eventoProductoDTO) {
        log.debug("Request to save EventoProducto : {}", eventoProductoDTO);
        EventoProducto eventoProducto = eventoProductoMapper.toEntity(eventoProductoDTO);
        eventoProducto = eventoProductoRepository.save(eventoProducto);
        return eventoProductoMapper.toDto(eventoProducto);
    }

    /**
     * Get all the eventoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventoProductos");
        return eventoProductoRepository.findAll(pageable)
            .map(eventoProductoMapper::toDto);
    }


    /**
     * Get one eventoProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventoProducto : {}", id);
        eventoProductoRepository.deleteById(id);
    }
}
