package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class MedicionLoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicionLote.class);
        MedicionLote medicionLote1 = new MedicionLote();
        medicionLote1.setId(1L);
        MedicionLote medicionLote2 = new MedicionLote();
        medicionLote2.setId(medicionLote1.getId());
        assertThat(medicionLote1).isEqualTo(medicionLote2);
        medicionLote2.setId(2L);
        assertThat(medicionLote1).isNotEqualTo(medicionLote2);
        medicionLote1.setId(null);
        assertThat(medicionLote1).isNotEqualTo(medicionLote2);
    }
}
