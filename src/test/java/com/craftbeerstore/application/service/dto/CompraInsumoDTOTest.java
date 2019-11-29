package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class CompraInsumoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraInsumoDTO.class);
        CompraInsumoDTO compraInsumoDTO1 = new CompraInsumoDTO();
        compraInsumoDTO1.setId(1L);
        CompraInsumoDTO compraInsumoDTO2 = new CompraInsumoDTO();
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(compraInsumoDTO1.getId());
        assertThat(compraInsumoDTO1).isEqualTo(compraInsumoDTO2);
        compraInsumoDTO2.setId(2L);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
        compraInsumoDTO1.setId(null);
        assertThat(compraInsumoDTO1).isNotEqualTo(compraInsumoDTO2);
    }
}
