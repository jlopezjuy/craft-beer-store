package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InsumoRecomendadoMapperTest {

    private InsumoRecomendadoMapper insumoRecomendadoMapper;

    @BeforeEach
    public void setUp() {
        insumoRecomendadoMapper = new InsumoRecomendadoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(insumoRecomendadoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(insumoRecomendadoMapper.fromId(null)).isNull();
    }
}
