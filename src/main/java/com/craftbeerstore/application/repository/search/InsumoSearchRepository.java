package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Insumo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Insumo entity.
 */
public interface InsumoSearchRepository extends ElasticsearchRepository<Insumo, Long> {
}
