package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MovimientoBarrilMapperTest {

    private MovimientoBarrilMapper movimientoBarrilMapper;

    @BeforeEach
    public void setUp() {
        movimientoBarrilMapper = new MovimientoBarrilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(movimientoBarrilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movimientoBarrilMapper.fromId(null)).isNull();
    }
}
