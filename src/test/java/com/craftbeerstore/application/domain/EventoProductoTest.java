package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EventoProductoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoProducto.class);
        EventoProducto eventoProducto1 = new EventoProducto();
        eventoProducto1.setId(1L);
        EventoProducto eventoProducto2 = new EventoProducto();
        eventoProducto2.setId(eventoProducto1.getId());
        assertThat(eventoProducto1).isEqualTo(eventoProducto2);
        eventoProducto2.setId(2L);
        assertThat(eventoProducto1).isNotEqualTo(eventoProducto2);
        eventoProducto1.setId(null);
        assertThat(eventoProducto1).isNotEqualTo(eventoProducto2);
    }
}
