package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RecetaInsumoMapperTest {

    private RecetaInsumoMapper recetaInsumoMapper;

    @BeforeEach
    public void setUp() {
        recetaInsumoMapper = new RecetaInsumoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(recetaInsumoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recetaInsumoMapper.fromId(null)).isNull();
    }
}
