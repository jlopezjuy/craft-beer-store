package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class CompraInsumoDetalleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDetalleDTO.class);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO1 = new CompraInsumoDetalleDTO();
        compraInsumoDetalleDTO1.setId(1L);
        CompraInsumoDetalleDTO compraInsumoDetalleDTO2 = new CompraInsumoDetalleDTO();
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO2.setId(compraInsumoDetalleDTO1.getId());
        assertThat(compraInsumoDetalleDTO1).isEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO2.setId(2L);
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
        compraInsumoDetalleDTO1.setId(null);
        assertThat(compraInsumoDetalleDTO1).isNotEqualTo(compraInsumoDetalleDTO2);
    }
}
