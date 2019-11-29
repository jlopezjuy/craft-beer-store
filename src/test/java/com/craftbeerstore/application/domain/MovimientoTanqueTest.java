package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientoTanqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoTanque.class);
        MovimientoTanque movimientoTanque1 = new MovimientoTanque();
        movimientoTanque1.setId(1L);
        MovimientoTanque movimientoTanque2 = new MovimientoTanque();
        movimientoTanque2.setId(movimientoTanque1.getId());
        assertThat(movimientoTanque1).isEqualTo(movimientoTanque2);
        movimientoTanque2.setId(2L);
        assertThat(movimientoTanque1).isNotEqualTo(movimientoTanque2);
        movimientoTanque1.setId(null);
        assertThat(movimientoTanque1).isNotEqualTo(movimientoTanque2);
    }
}
