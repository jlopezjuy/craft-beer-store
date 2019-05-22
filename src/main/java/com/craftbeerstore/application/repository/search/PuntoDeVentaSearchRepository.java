package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.PuntoDeVenta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PuntoDeVenta entity.
 */
public interface PuntoDeVentaSearchRepository extends ElasticsearchRepository<PuntoDeVenta, Long> {
}
