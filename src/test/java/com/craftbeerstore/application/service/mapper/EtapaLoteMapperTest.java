package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EtapaLoteMapperTest {

    private EtapaLoteMapper etapaLoteMapper;

    @BeforeEach
    public void setUp() {
        etapaLoteMapper = new EtapaLoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(etapaLoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(etapaLoteMapper.fromId(null)).isNull();
    }
}
