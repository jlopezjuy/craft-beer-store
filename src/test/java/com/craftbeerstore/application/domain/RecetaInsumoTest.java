package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class RecetaInsumoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumo.class);
        RecetaInsumo recetaInsumo1 = new RecetaInsumo();
        recetaInsumo1.setId(1L);
        RecetaInsumo recetaInsumo2 = new RecetaInsumo();
        recetaInsumo2.setId(recetaInsumo1.getId());
        assertThat(recetaInsumo1).isEqualTo(recetaInsumo2);
        recetaInsumo2.setId(2L);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
        recetaInsumo1.setId(null);
        assertThat(recetaInsumo1).isNotEqualTo(recetaInsumo2);
    }
}
