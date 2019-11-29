package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompraInsumoMapperTest {

    private CompraInsumoMapper compraInsumoMapper;

    @BeforeEach
    public void setUp() {
        compraInsumoMapper = new CompraInsumoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(compraInsumoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compraInsumoMapper.fromId(null)).isNull();
    }
}
