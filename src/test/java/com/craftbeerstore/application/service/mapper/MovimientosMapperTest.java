package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MovimientosMapperTest {

    private MovimientosMapper movimientosMapper;

    @BeforeEach
    public void setUp() {
        movimientosMapper = new MovimientosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(movimientosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movimientosMapper.fromId(null)).isNull();
    }
}
