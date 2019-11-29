package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class InsumoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsumoDTO.class);
        InsumoDTO insumoDTO1 = new InsumoDTO();
        insumoDTO1.setId(1L);
        InsumoDTO insumoDTO2 = new InsumoDTO();
        assertThat(insumoDTO1).isNotEqualTo(insumoDTO2);
        insumoDTO2.setId(insumoDTO1.getId());
        assertThat(insumoDTO1).isEqualTo(insumoDTO2);
        insumoDTO2.setId(2L);
        assertThat(insumoDTO1).isNotEqualTo(insumoDTO2);
        insumoDTO1.setId(null);
        assertThat(insumoDTO1).isNotEqualTo(insumoDTO2);
    }
}
