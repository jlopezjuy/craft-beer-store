package com.craftbeerstore.application.web.rest;
import com.craftbeerstore.application.service.ProductoService;
import com.craftbeerstore.application.web.rest.errors.BadRequestAlertException;
import com.craftbeerstore.application.web.rest.util.HeaderUtil;
import com.craftbeerstore.application.web.rest.util.PaginationUtil;
import com.craftbeerstore.application.service.dto.ProductoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    private final ProductoService productoService;

    public ProductoResource(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * POST  /productos : Create a new producto.
     *
     * @param productoDTO the productoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productoDTO, or with status 400 (Bad Request) if the producto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", productoDTO);
        if (productoDTO.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param productoDTO the productoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productoDTO,
     * or with status 400 (Bad Request) if the productoDTO is not valid,
     * or with status 500 (Internal Server Error) if the productoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productos")
    public ResponseEntity<ProductoDTO> updateProducto(@Valid @RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", productoDTO);
        if (productoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     */
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(Pageable pageable) {
        log.debug("REST request to get a page of Productos");
        Page<ProductoDTO> page = productoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /productos/empresa/{empresaId} : get all the productos by empresa
     * @param pageable the pagination information
     * @param empresaId the id of empresaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of productos by empresa in body
     */
    @GetMapping("/productos/empresa/{empresaId}")
    public ResponseEntity<List<ProductoDTO>> getAllProductosByEmpresa(Pageable pageable, @PathVariable Long empresaId){
        log.debug("REST request to get a page of Productos by empresa");
        Page<ProductoDTO> page = productoService.findAllByEmpresa(pageable, empresaId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos/empresa/{empresaId}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the productoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Optional<ProductoDTO> productoDTO = productoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoDTO);
    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the productoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productos?query=:query : search for the producto corresponding
     * to the query.
     *
     * @param query the query of the producto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/productos")
    public ResponseEntity<List<ProductoDTO>> searchProductos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Productos for query {}", query);
        Page<ProductoDTO> page = productoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/productos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * SEARCH  /_search/productos/empresa/{empresaId}?query=:query : search for the producto corresponding
     * to the query.
     *
     * @param query the query of the producto search
     * @param pageable the pagination information
     * @param empresaId
     * @return the result of the search
     */
    @GetMapping("/_search/productos/empresa/{empresaId}")
    public ResponseEntity<List<ProductoDTO>> searchProductosByEmpresa(@RequestParam String nombreComercial, @RequestParam String nombreProducto, Pageable pageable, @PathVariable Long empresaId) {
        log.debug("REST request to search for a page of Productos for query {}", nombreProducto);
        log.debug("REST request to search for a page of Productos for Empresa {}", empresaId);
        Page<ProductoDTO> page = productoService.search(empresaId, nombreComercial, nombreProducto, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(nombreComercial, page, "/api/_search/productos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
