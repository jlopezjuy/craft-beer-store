package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class InsumoRecomendadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumoRecomendado.class);
        InsumoRecomendado insumoRecomendado1 = new InsumoRecomendado();
        insumoRecomendado1.setId(1L);
        InsumoRecomendado insumoRecomendado2 = new InsumoRecomendado();
        insumoRecomendado2.setId(insumoRecomendado1.getId());
        assertThat(insumoRecomendado1).isEqualTo(insumoRecomendado2);
        insumoRecomendado2.setId(2L);
        assertThat(insumoRecomendado1).isNotEqualTo(insumoRecomendado2);
        insumoRecomendado1.setId(null);
        assertThat(insumoRecomendado1).isNotEqualTo(insumoRecomendado2);
    }
}
