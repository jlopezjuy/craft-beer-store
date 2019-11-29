package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class RecetaInsumoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetaInsumoDTO.class);
        RecetaInsumoDTO recetaInsumoDTO1 = new RecetaInsumoDTO();
        recetaInsumoDTO1.setId(1L);
        RecetaInsumoDTO recetaInsumoDTO2 = new RecetaInsumoDTO();
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(recetaInsumoDTO1.getId());
        assertThat(recetaInsumoDTO1).isEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO2.setId(2L);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
        recetaInsumoDTO1.setId(null);
        assertThat(recetaInsumoDTO1).isNotEqualTo(recetaInsumoDTO2);
    }
}
