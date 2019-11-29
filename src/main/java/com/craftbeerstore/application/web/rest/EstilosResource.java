package com.craftbeerstore.application.web.rest;

import com.craftbeerstore.application.service.EstilosService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.service.dto.EstilosDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.craftbeerstore.application.domain.Estilos}.
 */
@RestController
@RequestMapping("/api")
public class EstilosResource {

    private final Logger log = LoggerFactory.getLogger(EstilosResource.class);

    private static final String ENTITY_NAME = "estilos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstilosService estilosService;

    public EstilosResource(EstilosService estilosService) {
        this.estilosService = estilosService;
    }

    /**
     * {@code POST  /estilos} : Create a new estilos.
     *
     * @param estilosDTO the estilosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estilosDTO, or with status {@code 400 (Bad Request)} if the estilos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estilos")
    public ResponseEntity<EstilosDTO> createEstilos(@RequestBody EstilosDTO estilosDTO) throws URISyntaxException {
        log.debug("REST request to save Estilos : {}", estilosDTO);
        if (estilosDTO.getId() != null) {
            throw new BadRequestAlertException("A new estilos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstilosDTO result = estilosService.save(estilosDTO);
        return ResponseEntity.created(new URI("/api/estilos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estilos} : Updates an existing estilos.
     *
     * @param estilosDTO the estilosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estilosDTO,
     * or with status {@code 400 (Bad Request)} if the estilosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estilosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estilos")
    public ResponseEntity<EstilosDTO> updateEstilos(@RequestBody EstilosDTO estilosDTO) throws URISyntaxException {
        log.debug("REST request to update Estilos : {}", estilosDTO);
        if (estilosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstilosDTO result = estilosService.save(estilosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estilosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estilos} : get all the estilos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estilos in body.
     */
    @GetMapping("/estilos")
    public ResponseEntity<List<EstilosDTO>> getAllEstilos(Pageable pageable) {
        log.debug("REST request to get a page of Estilos");
        Page<EstilosDTO> page = estilosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estilos/:id} : get the "id" estilos.
     *
     * @param id the id of the estilosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estilosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estilos/{id}")
    public ResponseEntity<EstilosDTO> getEstilos(@PathVariable Long id) {
        log.debug("REST request to get Estilos : {}", id);
        Optional<EstilosDTO> estilosDTO = estilosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estilosDTO);
    }

    /**
     * {@code DELETE  /estilos/:id} : delete the "id" estilos.
     *
     * @param id the id of the estilosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estilos/{id}")
    public ResponseEntity<Void> deleteEstilos(@PathVariable Long id) {
        log.debug("REST request to delete Estilos : {}", id);
        estilosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
