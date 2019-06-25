package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Producto;
import com.craftbeerstore.application.repository.ProductoRepository;
import com.craftbeerstore.application.service.PresentacionService;
import com.craftbeerstore.application.domain.Presentacion;
import com.craftbeerstore.application.repository.PresentacionRepository;
import com.craftbeerstore.application.service.dto.PresentacionDTO;
import com.craftbeerstore.application.service.mapper.PresentacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Presentacion.
 */
@Service
@Transactional
public class PresentacionServiceImpl implements PresentacionService {

    private final Logger log = LoggerFactory.getLogger(PresentacionServiceImpl.class);

    private final PresentacionRepository presentacionRepository;

    private final PresentacionMapper presentacionMapper;


    private final ProductoRepository productoRepository;

    public PresentacionServiceImpl(PresentacionRepository presentacionRepository,
        PresentacionMapper presentacionMapper,
        ProductoRepository productoRepository) {
        this.presentacionRepository = presentacionRepository;
        this.presentacionMapper = presentacionMapper;
        this.productoRepository = productoRepository;
    }

    /**
     * Save a presentacion.
     *
     * @param presentacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PresentacionDTO save(PresentacionDTO presentacionDTO) {
        log.debug("Request to save Presentacion : {}", presentacionDTO);
        Presentacion presentacion = presentacionMapper.toEntity(presentacionDTO);
        presentacion = presentacionRepository.save(presentacion);
        PresentacionDTO result = presentacionMapper.toDto(presentacion);
        return result;
    }

    /**
     * Get all the presentacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PresentacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Presentacions");
        return presentacionRepository.findAll(pageable)
            .map(presentacionMapper::toDto);
    }

    @Override
    public Page<PresentacionDTO> findAllByProducto(Pageable pageable, Long productoId) {
        Producto producto = this.productoRepository.getOne(productoId);
        return this.presentacionRepository.findAllByProducto(pageable, producto)
            .map(presentacionMapper::toDto);
    }


    /**
     * Get one presentacion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresentacionDTO> findOne(Long id) {
        log.debug("Request to get Presentacion : {}", id);
        return presentacionRepository.findById(id)
            .map(presentacionMapper::toDto);
    }

    /**
     * Delete the presentacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentacion : {}", id);
        presentacionRepository.deleteById(id);
    }
}
