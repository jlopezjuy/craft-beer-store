package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.domain.RecetaInsumo;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.repository.InsumoRepository;
import com.craftbeerstore.application.repository.RecetaInsumoRepository;
import com.craftbeerstore.application.repository.RecetaRepository;
import com.craftbeerstore.application.service.RecetaInsumoService;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;
import com.craftbeerstore.application.service.mapper.RecetaInsumoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RecetaInsumo}.
 */
@Service
@Transactional
public class RecetaInsumoServiceImpl implements RecetaInsumoService {

    private final Logger log = LoggerFactory.getLogger(RecetaInsumoServiceImpl.class);

    private final RecetaInsumoRepository recetaInsumoRepository;

    private final RecetaInsumoMapper recetaInsumoMapper;

    private final RecetaRepository recetaRepository;

    private final InsumoRepository insumoRepository;

    public RecetaInsumoServiceImpl(RecetaInsumoRepository recetaInsumoRepository,
                                   RecetaInsumoMapper recetaInsumoMapper,
                                   RecetaRepository recetaRepository,
                                   InsumoRepository insumoRepository) {
        this.recetaInsumoRepository = recetaInsumoRepository;
        this.recetaInsumoMapper = recetaInsumoMapper;
        this.recetaRepository = recetaRepository;
        this.insumoRepository = insumoRepository;
    }

    /**
     * Save a recetaInsumo.
     *
     * @param recetaInsumoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecetaInsumoDTO save(RecetaInsumoDTO recetaInsumoDTO) {
        log.debug("Request to save RecetaInsumo : {}", recetaInsumoDTO);
        RecetaInsumo recetaInsumo = recetaInsumoMapper.toEntity(recetaInsumoDTO);
        recetaInsumo = recetaInsumoRepository.save(recetaInsumo);
        return recetaInsumoMapper.toDto(recetaInsumo);
    }

    @Override
    public List<RecetaInsumoDTO> save(List<RecetaInsumoDTO> recetaInsumoDTO) {
        List<RecetaInsumo> list = this.recetaInsumoRepository.saveAll(this.recetaInsumoMapper.toEntity(recetaInsumoDTO));
        return this.recetaInsumoMapper.toDto(list);
    }

    /**
     * Get all the recetaInsumos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecetaInsumoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecetaInsumos");
        return recetaInsumoRepository.findAll(pageable)
            .map(recetaInsumoMapper::toDto);
    }

    /**
     * @param recetaId
     * @param tipoInsumo
     * @return
     */
    @Override
    public List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId,
                                                       TipoInsumo tipoInsumo) {
        Receta receta = this.recetaRepository.getOne(recetaId);
        return recetaInsumoMapper.toDto(recetaInsumoRepository.findAllByRecetaAndTipoInsumo(receta, tipoInsumo));
    }

    /**
     * @param recetaId
     * @param tipoInsumos
     * @return
     */
    @Override
    public List<RecetaInsumoDTO> findAllByRecetaImsumo(Long recetaId,
                                                       List<TipoInsumo> tipoInsumos) {
        Receta receta = this.recetaRepository.getOne(recetaId);
        return recetaInsumoMapper.toDto(recetaInsumoRepository.findAllByRecetaAndTipoInsumoNotIn(receta, tipoInsumos));
    }


    /**
     * Get one recetaInsumo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecetaInsumoDTO> findOne(Long id) {
        log.debug("Request to get RecetaInsumo : {}", id);
        return recetaInsumoRepository.findById(id)
            .map(recetaInsumoMapper::toDto);
    }

    /**
     * Delete the recetaInsumo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecetaInsumo : {}", id);
        recetaInsumoRepository.deleteById(id);
    }

    @Override
    public void delete(List<String> list) {
        recetaInsumoRepository.deleteByIdIn(list.stream().map(id -> Long.valueOf(id)).collect(
            Collectors.toList()));
    }
}
