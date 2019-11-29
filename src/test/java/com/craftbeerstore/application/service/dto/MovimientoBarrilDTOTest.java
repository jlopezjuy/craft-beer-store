package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MovimientoBarrilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovimientoBarrilDTO.class);
        MovimientoBarrilDTO movimientoBarrilDTO1 = new MovimientoBarrilDTO();
        movimientoBarrilDTO1.setId(1L);
        MovimientoBarrilDTO movimientoBarrilDTO2 = new MovimientoBarrilDTO();
        assertThat(movimientoBarrilDTO1).isNotEqualTo(movimientoBarrilDTO2);
        movimientoBarrilDTO2.setId(movimientoBarrilDTO1.getId());
        assertThat(movimientoBarrilDTO1).isEqualTo(movimientoBarrilDTO2);
        movimientoBarrilDTO2.setId(2L);
        assertThat(movimientoBarrilDTO1).isNotEqualTo(movimientoBarrilDTO2);
        movimientoBarrilDTO1.setId(null);
        assertThat(movimientoBarrilDTO1).isNotEqualTo(movimientoBarrilDTO2);
    }
}
