package com.craftbeerstore.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CajaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CajaSearchRepositoryMockConfiguration {

    @MockBean
    private CajaSearchRepository mockCajaSearchRepository;

}
