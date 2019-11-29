package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MedicionLoteMapperTest {

    private MedicionLoteMapper medicionLoteMapper;

    @BeforeEach
    public void setUp() {
        medicionLoteMapper = new MedicionLoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(medicionLoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(medicionLoteMapper.fromId(null)).isNull();
    }
}
