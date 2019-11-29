package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class BarrilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarrilDTO.class);
        BarrilDTO barrilDTO1 = new BarrilDTO();
        barrilDTO1.setId(1L);
        BarrilDTO barrilDTO2 = new BarrilDTO();
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
        barrilDTO2.setId(barrilDTO1.getId());
        assertThat(barrilDTO1).isEqualTo(barrilDTO2);
        barrilDTO2.setId(2L);
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
        barrilDTO1.setId(null);
        assertThat(barrilDTO1).isNotEqualTo(barrilDTO2);
    }
}
