package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Evento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Evento entity.
 */
public interface EventoSearchRepository extends ElasticsearchRepository<Evento, Long> {
}
