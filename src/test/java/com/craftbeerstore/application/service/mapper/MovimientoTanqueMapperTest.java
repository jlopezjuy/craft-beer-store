package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MovimientoTanqueMapperTest {

    private MovimientoTanqueMapper movimientoTanqueMapper;

    @BeforeEach
    public void setUp() {
        movimientoTanqueMapper = new MovimientoTanqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(movimientoTanqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movimientoTanqueMapper.fromId(null)).isNull();
    }
}
