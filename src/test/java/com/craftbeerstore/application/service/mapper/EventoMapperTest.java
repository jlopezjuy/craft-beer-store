package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EventoMapperTest {

    private EventoMapper eventoMapper;

    @BeforeEach
    public void setUp() {
        eventoMapper = new EventoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(eventoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventoMapper.fromId(null)).isNull();
    }
}
