package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EquipamientoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipamientoDTO.class);
        EquipamientoDTO equipamientoDTO1 = new EquipamientoDTO();
        equipamientoDTO1.setId(1L);
        EquipamientoDTO equipamientoDTO2 = new EquipamientoDTO();
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
        equipamientoDTO2.setId(equipamientoDTO1.getId());
        assertThat(equipamientoDTO1).isEqualTo(equipamientoDTO2);
        equipamientoDTO2.setId(2L);
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
        equipamientoDTO1.setId(null);
        assertThat(equipamientoDTO1).isNotEqualTo(equipamientoDTO2);
    }
}
