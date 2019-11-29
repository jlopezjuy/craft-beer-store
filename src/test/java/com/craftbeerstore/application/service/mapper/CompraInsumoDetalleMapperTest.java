package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompraInsumoDetalleMapperTest {

    private CompraInsumoDetalleMapper compraInsumoDetalleMapper;

    @BeforeEach
    public void setUp() {
        compraInsumoDetalleMapper = new CompraInsumoDetalleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(compraInsumoDetalleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compraInsumoDetalleMapper.fromId(null)).isNull();
    }
}
