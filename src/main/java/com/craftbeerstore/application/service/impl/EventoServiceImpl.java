package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Evento;
import com.craftbeerstore.application.domain.EventoProducto;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.repository.EventoProductoRepository;
import com.craftbeerstore.application.repository.EventoRepository;
import com.craftbeerstore.application.service.EventoService;
import com.craftbeerstore.application.service.dto.EventoDTO;
import com.craftbeerstore.application.service.mapper.EventoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Evento}.
 */
@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

    private final EventoRepository eventoRepository;

    private final EventoMapper eventoMapper;

    private final EmpresaRepository empresaRepository;

    private final EventoProductoRepository eventoProductoRepository;

    public EventoServiceImpl(EventoRepository eventoRepository, EventoMapper eventoMapper,
                             EmpresaRepository empresaRepository,
                             EventoProductoRepository eventoProductoRepository) {
        this.eventoRepository = eventoRepository;
        this.eventoMapper = eventoMapper;
        this.empresaRepository = empresaRepository;
        this.eventoProductoRepository = eventoProductoRepository;
    }

    /**
     * Save a evento.
     *
     * @param eventoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventoDTO save(EventoDTO eventoDTO) {
        log.debug("Request to save Evento : {}", eventoDTO);
        Evento evento = eventoMapper.toEntity(eventoDTO);
        evento = eventoRepository.save(evento);
        return eventoMapper.toDto(evento);
    }

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eventos");
        return eventoRepository.findAll(pageable)
            .map(eventoMapper::toDto);
    }


    /**
     * Get one evento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventoDTO> findOne(Long id) {
        log.debug("Request to get Evento : {}", id);
        return eventoRepository.findById(id)
            .map(eventoMapper::toDto);
    }

    /**
     * Delete the evento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evento : {}", id);
        Evento evento = eventoRepository.getOne(id);
        List<EventoProducto> listDelete = this.eventoProductoRepository
            .findByEvento(evento);
        this.eventoProductoRepository.deleteAll(listDelete);
        eventoRepository.deleteById(id);
    }

    @Override
    public Page<EventoDTO> findAll(Pageable pageable, Long empresaId) {
        log.debug("Request to get all Eventos");
        Empresa empresa = empresaRepository.getOne(empresaId);
        return eventoRepository.findAllByEmpresa(pageable, empresa)
            .map(eventoMapper::toDto);
    }
}
