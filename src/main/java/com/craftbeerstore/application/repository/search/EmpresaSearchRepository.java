package com.craftbeerstore.application.repository.search;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.User;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Empresa entity.
 */
public interface EmpresaSearchRepository extends ElasticsearchRepository<Empresa, Long> {
}
