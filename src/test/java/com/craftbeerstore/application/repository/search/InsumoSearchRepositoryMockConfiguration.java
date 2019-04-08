package com.craftbeerstore.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of InsumoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InsumoSearchRepositoryMockConfiguration {

    @MockBean
    private InsumoSearchRepository mockInsumoSearchRepository;

}
