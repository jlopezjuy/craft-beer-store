package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EventoProductoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoProductoDTO.class);
        EventoProductoDTO eventoProductoDTO1 = new EventoProductoDTO();
        eventoProductoDTO1.setId(1L);
        EventoProductoDTO eventoProductoDTO2 = new EventoProductoDTO();
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
        eventoProductoDTO2.setId(eventoProductoDTO1.getId());
        assertThat(eventoProductoDTO1).isEqualTo(eventoProductoDTO2);
        eventoProductoDTO2.setId(2L);
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
        eventoProductoDTO1.setId(null);
        assertThat(eventoProductoDTO1).isNotEqualTo(eventoProductoDTO2);
    }
}
