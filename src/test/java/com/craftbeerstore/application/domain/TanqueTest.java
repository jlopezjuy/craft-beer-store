package com.craftbeerstore.application.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class TanqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tanque.class);
        Tanque tanque1 = new Tanque();
        tanque1.setId(1L);
        Tanque tanque2 = new Tanque();
        tanque2.setId(tanque1.getId());
        assertThat(tanque1).isEqualTo(tanque2);
        tanque2.setId(2L);
        assertThat(tanque1).isNotEqualTo(tanque2);
        tanque1.setId(null);
        assertThat(tanque1).isNotEqualTo(tanque2);
    }
}
