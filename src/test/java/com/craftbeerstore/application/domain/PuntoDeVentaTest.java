package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class PuntoDeVentaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntoDeVenta.class);
        PuntoDeVenta puntoDeVenta1 = new PuntoDeVenta();
        puntoDeVenta1.setId(1L);
        PuntoDeVenta puntoDeVenta2 = new PuntoDeVenta();
        puntoDeVenta2.setId(puntoDeVenta1.getId());
        assertThat(puntoDeVenta1).isEqualTo(puntoDeVenta2);
        puntoDeVenta2.setId(2L);
        assertThat(puntoDeVenta1).isNotEqualTo(puntoDeVenta2);
        puntoDeVenta1.setId(null);
        assertThat(puntoDeVenta1).isNotEqualTo(puntoDeVenta2);
    }
}
