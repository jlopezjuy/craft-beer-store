package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EstilosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estilos.class);
        Estilos estilos1 = new Estilos();
        estilos1.setId(1L);
        Estilos estilos2 = new Estilos();
        estilos2.setId(estilos1.getId());
        assertThat(estilos1).isEqualTo(estilos2);
        estilos2.setId(2L);
        assertThat(estilos1).isNotEqualTo(estilos2);
        estilos1.setId(null);
        assertThat(estilos1).isNotEqualTo(estilos2);
    }
}
