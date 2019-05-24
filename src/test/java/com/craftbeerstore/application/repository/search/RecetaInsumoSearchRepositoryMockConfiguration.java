package com.craftbeerstore.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of RecetaInsumoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RecetaInsumoSearchRepositoryMockConfiguration {

    @MockBean
    private RecetaInsumoSearchRepository mockRecetaInsumoSearchRepository;

}
