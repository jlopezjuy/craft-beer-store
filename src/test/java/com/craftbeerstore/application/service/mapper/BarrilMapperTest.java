package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BarrilMapperTest {

    private BarrilMapper barrilMapper;

    @BeforeEach
    public void setUp() {
        barrilMapper = new BarrilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(barrilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(barrilMapper.fromId(null)).isNull();
    }
}
