package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Receta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Receta entity.
 */
public interface RecetaSearchRepository extends ElasticsearchRepository<Receta, Long> {
}
