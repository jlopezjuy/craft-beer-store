package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class CompraInsumoDetalleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDetalle.class);
        CompraInsumoDetalle compraInsumoDetalle1 = new CompraInsumoDetalle();
        compraInsumoDetalle1.setId(1L);
        CompraInsumoDetalle compraInsumoDetalle2 = new CompraInsumoDetalle();
        compraInsumoDetalle2.setId(compraInsumoDetalle1.getId());
        assertThat(compraInsumoDetalle1).isEqualTo(compraInsumoDetalle2);
        compraInsumoDetalle2.setId(2L);
        assertThat(compraInsumoDetalle1).isNotEqualTo(compraInsumoDetalle2);
        compraInsumoDetalle1.setId(null);
        assertThat(compraInsumoDetalle1).isNotEqualTo(compraInsumoDetalle2);
    }
}
