package com.craftbeerstore.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of RecetaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RecetaSearchRepositoryMockConfiguration {

    @MockBean
    private RecetaSearchRepository mockRecetaSearchRepository;

}
