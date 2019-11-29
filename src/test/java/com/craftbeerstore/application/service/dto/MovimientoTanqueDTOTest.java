package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientoTanqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoTanqueDTO.class);
        MovimientoTanqueDTO movimientoTanqueDTO1 = new MovimientoTanqueDTO();
        movimientoTanqueDTO1.setId(1L);
        MovimientoTanqueDTO movimientoTanqueDTO2 = new MovimientoTanqueDTO();
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO2.setId(movimientoTanqueDTO1.getId());
        assertThat(movimientoTanqueDTO1).isEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO2.setId(2L);
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
        movimientoTanqueDTO1.setId(null);
        assertThat(movimientoTanqueDTO1).isNotEqualTo(movimientoTanqueDTO2);
    }
}
