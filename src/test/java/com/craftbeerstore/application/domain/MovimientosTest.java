package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movimientos.class);
        Movimientos movimientos1 = new Movimientos();
        movimientos1.setId(1L);
        Movimientos movimientos2 = new Movimientos();
        movimientos2.setId(movimientos1.getId());
        assertThat(movimientos1).isEqualTo(movimientos2);
        movimientos2.setId(2L);
        assertThat(movimientos1).isNotEqualTo(movimientos2);
        movimientos1.setId(null);
        assertThat(movimientos1).isNotEqualTo(movimientos2);
    }
}
