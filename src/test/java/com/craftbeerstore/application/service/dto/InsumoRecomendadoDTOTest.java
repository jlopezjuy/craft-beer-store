package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class InsumoRecomendadoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumoRecomendadoDTO.class);
        InsumoRecomendadoDTO insumoRecomendadoDTO1 = new InsumoRecomendadoDTO();
        insumoRecomendadoDTO1.setId(1L);
        InsumoRecomendadoDTO insumoRecomendadoDTO2 = new InsumoRecomendadoDTO();
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO2.setId(insumoRecomendadoDTO1.getId());
        assertThat(insumoRecomendadoDTO1).isEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO2.setId(2L);
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
        insumoRecomendadoDTO1.setId(null);
        assertThat(insumoRecomendadoDTO1).isNotEqualTo(insumoRecomendadoDTO2);
    }
}
