package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.DetalleMovimiento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetalleMovimiento entity.
 */
public interface DetalleMovimientoSearchRepository extends ElasticsearchRepository<DetalleMovimiento, Long> {
}
