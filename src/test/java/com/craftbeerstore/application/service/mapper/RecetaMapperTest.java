package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RecetaMapperTest {

    private RecetaMapper recetaMapper;

    @BeforeEach
    public void setUp() {
        recetaMapper = new RecetaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(recetaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recetaMapper.fromId(null)).isNull();
    }
}
