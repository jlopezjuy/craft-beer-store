package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PresentacionMapperTest {

    private PresentacionMapper presentacionMapper;

    @BeforeEach
    public void setUp() {
        presentacionMapper = new PresentacionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(presentacionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(presentacionMapper.fromId(null)).isNull();
    }
}
