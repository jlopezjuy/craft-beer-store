package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class PuntoDeVentaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntoDeVentaDTO.class);
        PuntoDeVentaDTO puntoDeVentaDTO1 = new PuntoDeVentaDTO();
        puntoDeVentaDTO1.setId(1L);
        PuntoDeVentaDTO puntoDeVentaDTO2 = new PuntoDeVentaDTO();
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO2.setId(puntoDeVentaDTO1.getId());
        assertThat(puntoDeVentaDTO1).isEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO2.setId(2L);
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
        puntoDeVentaDTO1.setId(null);
        assertThat(puntoDeVentaDTO1).isNotEqualTo(puntoDeVentaDTO2);
    }
}
