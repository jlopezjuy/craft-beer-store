package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientosDTO.class);
        MovimientosDTO movimientosDTO1 = new MovimientosDTO();
        movimientosDTO1.setId(1L);
        MovimientosDTO movimientosDTO2 = new MovimientosDTO();
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
        movimientosDTO2.setId(movimientosDTO1.getId());
        assertThat(movimientosDTO1).isEqualTo(movimientosDTO2);
        movimientosDTO2.setId(2L);
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
        movimientosDTO1.setId(null);
        assertThat(movimientosDTO1).isNotEqualTo(movimientosDTO2);
    }
}
