package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.RecetaInsumo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RecetaInsumo entity.
 */
public interface RecetaInsumoSearchRepository extends ElasticsearchRepository<RecetaInsumo, Long> {
}
