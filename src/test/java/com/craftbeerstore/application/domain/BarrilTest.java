package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class BarrilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barril.class);
        Barril barril1 = new Barril();
        barril1.setId(1L);
        Barril barril2 = new Barril();
        barril2.setId(barril1.getId());
        assertThat(barril1).isEqualTo(barril2);
        barril2.setId(2L);
        assertThat(barril1).isNotEqualTo(barril2);
        barril1.setId(null);
        assertThat(barril1).isNotEqualTo(barril2);
    }
}
