package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.service.EstilosService;
import com.craftbeerstore.application.domain.Estilos;
import com.craftbeerstore.application.repository.EstilosRepository;
import com.craftbeerstore.application.service.dto.EstilosDTO;
import com.craftbeerstore.application.service.mapper.EstilosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Estilos}.
 */
@Service
@Transactional
public class EstilosServiceImpl implements EstilosService {

    private final Logger log = LoggerFactory.getLogger(EstilosServiceImpl.class);

    private final EstilosRepository estilosRepository;

    private final EstilosMapper estilosMapper;

    public EstilosServiceImpl(EstilosRepository estilosRepository, EstilosMapper estilosMapper) {
        this.estilosRepository = estilosRepository;
        this.estilosMapper = estilosMapper;
    }

    /**
     * Save a estilos.
     *
     * @param estilosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstilosDTO save(EstilosDTO estilosDTO) {
        log.debug("Request to save Estilos : {}", estilosDTO);
        Estilos estilos = estilosMapper.toEntity(estilosDTO);
        estilos = estilosRepository.save(estilos);
        return estilosMapper.toDto(estilos);
    }

    /**
     * Get all the estilos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estilos : {}", id);
        estilosRepository.deleteById(id);
    }
}
