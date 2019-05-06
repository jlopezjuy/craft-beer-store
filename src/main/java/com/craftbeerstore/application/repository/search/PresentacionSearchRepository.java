package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Presentacion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Presentacion entity.
 */
public interface PresentacionSearchRepository extends ElasticsearchRepository<Presentacion, Long> {
}
