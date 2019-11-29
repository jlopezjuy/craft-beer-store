package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class DetalleMovimientoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleMovimientoDTO.class);
        DetalleMovimientoDTO detalleMovimientoDTO1 = new DetalleMovimientoDTO();
        detalleMovimientoDTO1.setId(1L);
        DetalleMovimientoDTO detalleMovimientoDTO2 = new DetalleMovimientoDTO();
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO2.setId(detalleMovimientoDTO1.getId());
        assertThat(detalleMovimientoDTO1).isEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO2.setId(2L);
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
        detalleMovimientoDTO1.setId(null);
        assertThat(detalleMovimientoDTO1).isNotEqualTo(detalleMovimientoDTO2);
    }
}
