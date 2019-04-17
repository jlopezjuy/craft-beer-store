package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Movimientos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Movimientos entity.
 */
public interface MovimientosSearchRepository extends ElasticsearchRepository<Movimientos, Long> {
}
