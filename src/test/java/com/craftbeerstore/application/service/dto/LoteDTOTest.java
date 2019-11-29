package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class LoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoteDTO.class);
        LoteDTO loteDTO1 = new LoteDTO();
        loteDTO1.setId(1L);
        LoteDTO loteDTO2 = new LoteDTO();
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
        loteDTO2.setId(loteDTO1.getId());
        assertThat(loteDTO1).isEqualTo(loteDTO2);
        loteDTO2.setId(2L);
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
        loteDTO1.setId(null);
        assertThat(loteDTO1).isNotEqualTo(loteDTO2);
    }
}
