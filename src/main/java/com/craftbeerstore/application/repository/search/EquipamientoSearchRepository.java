package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Equipamiento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Equipamiento entity.
 */
public interface EquipamientoSearchRepository extends ElasticsearchRepository<Equipamiento, Long> {
}
