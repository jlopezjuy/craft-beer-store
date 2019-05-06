package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Caja;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Caja entity.
 */
public interface CajaSearchRepository extends ElasticsearchRepository<Caja, Long> {
}
