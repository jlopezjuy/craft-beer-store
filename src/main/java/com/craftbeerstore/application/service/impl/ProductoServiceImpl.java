package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.ProductoService;
import com.craftbeerstore.application.domain.Producto;
import com.craftbeerstore.application.repository.ProductoRepository;
import com.craftbeerstore.application.repository.search.ProductoSearchRepository;
import com.craftbeerstore.application.service.dto.ProductoDTO;
import com.craftbeerstore.application.service.mapper.ProductoMapper;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductoSearchRepository productoSearchRepository;

    private final EmpresaRepository empresaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper,
        ProductoSearchRepository productoSearchRepository,
        EmpresaRepository empresaRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoSearchRepository = productoSearchRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        ProductoDTO result = productoMapper.toDto(producto);
        productoSearchRepository.save(producto);
        return result;
    }

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable)
            .map(productoMapper::toDto);
    }

    @Override
    public Page<ProductoDTO> findAllByEmpresa(Pageable pageable, Long empresaId) {
        Empresa empresa = this.empresaRepository.getOne(empresaId);
        return productoRepository.findAllByEmpresa(pageable, empresa).map(productoMapper::toDto);
    }


    /**
     * Get one producto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id)
            .map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
        productoSearchRepository.deleteById(id);
    }

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Productos for query {}", query);

//        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(queryStringQuery(query));
//
//        return productoSearchRepository.search(queryBuilder, pageable)
//            .map(productoMapper::toDto);

        return productoSearchRepository.search(queryStringQuery(query), pageable)
            .map(productoMapper::toDto);
    }
}
