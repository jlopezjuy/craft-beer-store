package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientoBarrilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoBarril.class);
        MovimientoBarril movimientoBarril1 = new MovimientoBarril();
        movimientoBarril1.setId(1L);
        MovimientoBarril movimientoBarril2 = new MovimientoBarril();
        movimientoBarril2.setId(movimientoBarril1.getId());
        assertThat(movimientoBarril1).isEqualTo(movimientoBarril2);
        movimientoBarril2.setId(2L);
        assertThat(movimientoBarril1).isNotEqualTo(movimientoBarril2);
        movimientoBarril1.setId(null);
        assertThat(movimientoBarril1).isNotEqualTo(movimientoBarril2);
    }
}
