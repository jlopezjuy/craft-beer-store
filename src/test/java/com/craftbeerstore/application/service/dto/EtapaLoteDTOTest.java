package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EtapaLoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaLoteDTO.class);
        EtapaLoteDTO etapaLoteDTO1 = new EtapaLoteDTO();
        etapaLoteDTO1.setId(1L);
        EtapaLoteDTO etapaLoteDTO2 = new EtapaLoteDTO();
        assertThat(etapaLoteDTO1).isNotEqualTo(etapaLoteDTO2);
        etapaLoteDTO2.setId(etapaLoteDTO1.getId());
        assertThat(etapaLoteDTO1).isEqualTo(etapaLoteDTO2);
        etapaLoteDTO2.setId(2L);
        assertThat(etapaLoteDTO1).isNotEqualTo(etapaLoteDTO2);
        etapaLoteDTO1.setId(null);
        assertThat(etapaLoteDTO1).isNotEqualTo(etapaLoteDTO2);
    }
}
