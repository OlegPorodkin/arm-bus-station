package ru.porodkin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.porodkin.web.rest.TestUtil;

class TypeObjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeObjectDTO.class);
        TypeObjectDTO typeObjectDTO1 = new TypeObjectDTO();
        typeObjectDTO1.setId(1L);
        TypeObjectDTO typeObjectDTO2 = new TypeObjectDTO();
        assertThat(typeObjectDTO1).isNotEqualTo(typeObjectDTO2);
        typeObjectDTO2.setId(typeObjectDTO1.getId());
        assertThat(typeObjectDTO1).isEqualTo(typeObjectDTO2);
        typeObjectDTO2.setId(2L);
        assertThat(typeObjectDTO1).isNotEqualTo(typeObjectDTO2);
        typeObjectDTO1.setId(null);
        assertThat(typeObjectDTO1).isNotEqualTo(typeObjectDTO2);
    }
}
