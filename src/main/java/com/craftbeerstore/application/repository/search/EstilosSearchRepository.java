package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Estilos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Estilos entity.
 */
public interface EstilosSearchRepository extends ElasticsearchRepository<Estilos, Long> {
}
