package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EtapaLoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapaLote.class);
        EtapaLote etapaLote1 = new EtapaLote();
        etapaLote1.setId(1L);
        EtapaLote etapaLote2 = new EtapaLote();
        etapaLote2.setId(etapaLote1.getId());
        assertThat(etapaLote1).isEqualTo(etapaLote2);
        etapaLote2.setId(2L);
        assertThat(etapaLote1).isNotEqualTo(etapaLote2);
        etapaLote1.setId(null);
        assertThat(etapaLote1).isNotEqualTo(etapaLote2);
    }
}
