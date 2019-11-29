package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PuntoDeVentaMapperTest {

    private PuntoDeVentaMapper puntoDeVentaMapper;

    @BeforeEach
    public void setUp() {
        puntoDeVentaMapper = new PuntoDeVentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(puntoDeVentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(puntoDeVentaMapper.fromId(null)).isNull();
    }
}
