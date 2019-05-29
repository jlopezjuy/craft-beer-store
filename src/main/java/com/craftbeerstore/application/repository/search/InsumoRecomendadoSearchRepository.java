package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.InsumoRecomendado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InsumoRecomendado entity.
 */
public interface InsumoRecomendadoSearchRepository extends ElasticsearchRepository<InsumoRecomendado, Long> {
}
