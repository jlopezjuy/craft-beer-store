package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.EventoProducto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EventoProducto entity.
 */
public interface EventoProductoSearchRepository extends ElasticsearchRepository<EventoProducto, Long> {
}
