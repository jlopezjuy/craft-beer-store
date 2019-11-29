package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class RecetaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaDTO.class);
        RecetaDTO recetaDTO1 = new RecetaDTO();
        recetaDTO1.setId(1L);
        RecetaDTO recetaDTO2 = new RecetaDTO();
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
        recetaDTO2.setId(recetaDTO1.getId());
        assertThat(recetaDTO1).isEqualTo(recetaDTO2);
        recetaDTO2.setId(2L);
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
        recetaDTO1.setId(null);
        assertThat(recetaDTO1).isNotEqualTo(recetaDTO2);
    }
}
