package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class TanqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TanqueDTO.class);
        TanqueDTO tanqueDTO1 = new TanqueDTO();
        tanqueDTO1.setId(1L);
        TanqueDTO tanqueDTO2 = new TanqueDTO();
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
        tanqueDTO2.setId(tanqueDTO1.getId());
        assertThat(tanqueDTO1).isEqualTo(tanqueDTO2);
        tanqueDTO2.setId(2L);
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
        tanqueDTO1.setId(null);
        assertThat(tanqueDTO1).isNotEqualTo(tanqueDTO2);
    }
}
