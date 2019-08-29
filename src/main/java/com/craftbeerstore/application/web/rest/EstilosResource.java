package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.EstilosService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.EstilosDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Estilos.
 */
@RestController
@RequestMapping("/api")
public class EstilosResource {

    private final Logger log = LoggerFactory.getLogger(EstilosResource.class);

    private static final String ENTITY_NAME = "estilos";

    private final EstilosService estilosService;

    public EstilosResource(EstilosService estilosService) {
        this.estilosService = estilosService;
    }

    /**
     * POST  /estilos : Create a new estilos.
     *
     * @param estilosDTO the estilosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estilosDTO, or with status 400 (Bad Request) if the estilos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estilos")
    public ResponseEntity<EstilosDTO> createEstilos(@RequestBody EstilosDTO estilosDTO) throws URISyntaxException {
        log.debug("REST request to save Estilos : {}", estilosDTO);
        if (estilosDTO.getId() != null) {
            throw new BadRequestAlertException("A new estilos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstilosDTO result = estilosService.save(estilosDTO);
        return ResponseEntity.created(new URI("/api/estilos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estilos : Updates an existing estilos.
     *
     * @param estilosDTO the estilosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estilosDTO,
     * or with status 400 (Bad Request) if the estilosDTO is not valid,
     * or with status 500 (Internal Server Error) if the estilosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estilos")
    public ResponseEntity<EstilosDTO> updateEstilos(@RequestBody EstilosDTO estilosDTO) throws URISyntaxException {
        log.debug("REST request to update Estilos : {}", estilosDTO);
        if (estilosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstilosDTO result = estilosService.save(estilosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estilosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estilos : get all the estilos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estilos in body
     */
    @GetMapping("/estilos/all")
    public ResponseEntity<List<EstilosDTO>> getAllEstilosSinPageable() {
        log.debug("REST request to get a page of Estilos");
        List<EstilosDTO> page = estilosService.findAllEstilos();
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /estilos : get all the estilos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estilos in body
     */
    @GetMapping("/estilos")
    public ResponseEntity<List<EstilosDTO>> getAllEstilos(Pageable pageable) {
        log.debug("REST request to get a page of Estilos");
        Page<EstilosDTO> page = estilosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estilos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estilos/:id : get the "id" estilos.
     *
     * @param id the id of the estilosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estilosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estilos/{id}")
    public ResponseEntity<EstilosDTO> getEstilos(@PathVariable Long id) {
        log.debug("REST request to get Estilos : {}", id);
        Optional<EstilosDTO> estilosDTO = estilosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estilosDTO);
    }

    /**
     * DELETE  /estilos/:id : delete the "id" estilos.
     *
     * @param id the id of the estilosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estilos/{id}")
    public ResponseEntity<Void> deleteEstilos(@PathVariable Long id) {
        log.debug("REST request to delete Estilos : {}", id);
        estilosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
