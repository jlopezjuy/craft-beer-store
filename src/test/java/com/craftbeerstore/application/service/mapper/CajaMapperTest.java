package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CajaMapperTest {

    private CajaMapper cajaMapper;

    @BeforeEach
    public void setUp() {
        cajaMapper = new CajaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(cajaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cajaMapper.fromId(null)).isNull();
    }
}
