package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EventoProductoMapperTest {

    private EventoProductoMapper eventoProductoMapper;

    @BeforeEach
    public void setUp() {
        eventoProductoMapper = new EventoProductoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(eventoProductoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventoProductoMapper.fromId(null)).isNull();
    }
}
