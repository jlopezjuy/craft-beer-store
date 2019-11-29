package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InsumoMapperTest {

    private InsumoMapper insumoMapper;

    @BeforeEach
    public void setUp() {
        insumoMapper = new InsumoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(insumoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(insumoMapper.fromId(null)).isNull();
    }
}
