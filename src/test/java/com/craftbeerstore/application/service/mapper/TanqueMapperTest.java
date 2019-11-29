package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TanqueMapperTest {

    private TanqueMapper tanqueMapper;

    @BeforeEach
    public void setUp() {
        tanqueMapper = new TanqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tanqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tanqueMapper.fromId(null)).isNull();
    }
}
