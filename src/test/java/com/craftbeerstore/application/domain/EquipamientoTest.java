package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EquipamientoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipamiento.class);
        Equipamiento equipamiento1 = new Equipamiento();
        equipamiento1.setId(1L);
        Equipamiento equipamiento2 = new Equipamiento();
        equipamiento2.setId(equipamiento1.getId());
        assertThat(equipamiento1).isEqualTo(equipamiento2);
        equipamiento2.setId(2L);
        assertThat(equipamiento1).isNotEqualTo(equipamiento2);
        equipamiento1.setId(null);
        assertThat(equipamiento1).isNotEqualTo(equipamiento2);
    }
}
