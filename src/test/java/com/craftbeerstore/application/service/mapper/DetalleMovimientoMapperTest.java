package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetalleMovimientoMapperTest {

    private DetalleMovimientoMapper detalleMovimientoMapper;

    @BeforeEach
    public void setUp() {
        detalleMovimientoMapper = new DetalleMovimientoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detalleMovimientoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalleMovimientoMapper.fromId(null)).isNull();
    }
}
