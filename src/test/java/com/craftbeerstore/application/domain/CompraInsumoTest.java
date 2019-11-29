package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class CompraInsumoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumo.class);
        CompraInsumo compraInsumo1 = new CompraInsumo();
        compraInsumo1.setId(1L);
        CompraInsumo compraInsumo2 = new CompraInsumo();
        compraInsumo2.setId(compraInsumo1.getId());
        assertThat(compraInsumo1).isEqualTo(compraInsumo2);
        compraInsumo2.setId(2L);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
        compraInsumo1.setId(null);
        assertThat(compraInsumo1).isNotEqualTo(compraInsumo2);
    }
}
