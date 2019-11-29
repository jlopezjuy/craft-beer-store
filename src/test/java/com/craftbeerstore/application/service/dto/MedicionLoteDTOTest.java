package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MedicionLoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicionLoteDTO.class);
        MedicionLoteDTO medicionLoteDTO1 = new MedicionLoteDTO();
        medicionLoteDTO1.setId(1L);
        MedicionLoteDTO medicionLoteDTO2 = new MedicionLoteDTO();
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
        medicionLoteDTO2.setId(medicionLoteDTO1.getId());
        assertThat(medicionLoteDTO1).isEqualTo(medicionLoteDTO2);
        medicionLoteDTO2.setId(2L);
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
        medicionLoteDTO1.setId(null);
        assertThat(medicionLoteDTO1).isNotEqualTo(medicionLoteDTO2);
    }
}
