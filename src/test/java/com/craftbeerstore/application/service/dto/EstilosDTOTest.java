package com.craftbeerstore.application.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.craftbeerstore.application.web.rest.TestUtil;

public class EstilosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstilosDTO.class);
        EstilosDTO estilosDTO1 = new EstilosDTO();
        estilosDTO1.setId(1L);
        EstilosDTO estilosDTO2 = new EstilosDTO();
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
        estilosDTO2.setId(estilosDTO1.getId());
        assertThat(estilosDTO1).isEqualTo(estilosDTO2);
        estilosDTO2.setId(2L);
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
        estilosDTO1.setId(null);
        assertThat(estilosDTO1).isNotEqualTo(estilosDTO2);
    }
}
