package com.craftbeerstore.application.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EstilosMapperTest {

    private EstilosMapper estilosMapper;

    @BeforeEach
    public void setUp() {
        estilosMapper = new EstilosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(estilosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(estilosMapper.fromId(null)).isNull();
    }
}
