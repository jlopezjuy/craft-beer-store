package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class RecetaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receta.class);
        Receta receta1 = new Receta();
        receta1.setId(1L);
        Receta receta2 = new Receta();
        receta2.setId(receta1.getId());
        assertThat(receta1).isEqualTo(receta2);
        receta2.setId(2L);
        assertThat(receta1).isNotEqualTo(receta2);
        receta1.setId(null);
        assertThat(receta1).isNotEqualTo(receta2);
    }
}
