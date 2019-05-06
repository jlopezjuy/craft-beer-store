package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Producto entity.
 */
public interface ProductoSearchRepository extends ElasticsearchRepository<Producto, Long> {

    Page<Producto> findByNombreComercialLikeOrNombreProductoLikeAndEmpresa(String nombreComercial, String nombreProducto, Empresa empresa, Pageable pageable);
}
