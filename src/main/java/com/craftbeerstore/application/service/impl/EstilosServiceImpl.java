package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.EstilosService;
import com.craftbeerstore.application.domain.Estilos;
import com.craftbeerstore.application.repository.EstilosRepository;
import com.craftbeerstore.application.repository.search.EstilosSearchRepository;
import com.craftbeerstore.application.service.dto.EstilosDTO;
import com.craftbeerstore.application.service.mapper.EstilosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Estilos.
 */
@Service
@Transactional
public class EstilosServiceImpl implements EstilosService {

    private final Logger log = LoggerFactory.getLogger(EstilosServiceImpl.class);

    private final EstilosRepository estilosRepository;

    private final EstilosMapper estilosMapper;

    private final EstilosSearchRepository estilosSearchRepository;

    public EstilosServiceImpl(EstilosRepository estilosRepository, EstilosMapper estilosMapper, EstilosSearchRepository estilosSearchRepository) {
        this.estilosRepository = estilosRepository;
        this.estilosMapper = estilosMapper;
        this.estilosSearchRepository = estilosSearchRepository;
    }

    /**
     * Save a estilos.
     *
     * @param estilosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstilosDTO save(EstilosDTO estilosDTO) {
        log.debug("Request to save Estilos : {}", estilosDTO);
        Estilos estilos = estilosMapper.toEntity(estilosDTO);
        estilos = estilosRepository.save(estilos);
        EstilosDTO result = estilosMapper.toDto(estilos);
        estilosSearchRepository.save(estilos);
        return result;
    }

    /**
     * Get all the estilos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstilosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estilos");
        return estilosRepository.findAll(pageable)
            .map(estilosMapper::toDto);
    }


    /**
     * Get one estilos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstilosDTO> findOne(Long id) {
        log.debug("Request to get Estilos : {}", id);
        return estilosRepository.findById(id)
            .map(estilosMapper::toDto);
    }

    /**
     * Delete the estilos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estilos : {}", id);
        estilosRepository.deleteById(id);
        estilosSearchRepository.deleteById(id);
    }

    /**
     * Search for the estilos corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstilosDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Estilos for query {}", query);
        return estilosSearchRepository.search(queryStringQuery(query), pageable)
            .map(estilosMapper::toDto);
    }
}
