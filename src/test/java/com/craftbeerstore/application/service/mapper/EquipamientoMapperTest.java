package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EquipamientoMapperTest {

    private EquipamientoMapper equipamientoMapper;

    @BeforeEach
    public void setUp() {
        equipamientoMapper = new EquipamientoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(equipamientoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(equipamientoMapper.fromId(null)).isNull();
    }
}
