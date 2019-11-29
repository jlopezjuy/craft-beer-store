package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProveedorMapperTest {

    private ProveedorMapper proveedorMapper;

    @BeforeEach
    public void setUp() {
        proveedorMapper = new ProveedorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(proveedorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(proveedorMapper.fromId(null)).isNull();
    }
}
