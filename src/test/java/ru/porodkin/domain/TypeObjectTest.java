package ru.porodkin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.porodkin.web.rest.TestUtil;

class TypeObjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeObject.class);
        TypeObject typeObject1 = new TypeObject();
        typeObject1.setId(1L);
        TypeObject typeObject2 = new TypeObject();
        typeObject2.setId(typeObject1.getId());
        assertThat(typeObject1).isEqualTo(typeObject2);
        typeObject2.setId(2L);
        assertThat(typeObject1).isNotEqualTo(typeObject2);
        typeObject1.setId(null);
        assertThat(typeObject1).isNotEqualTo(typeObject2);
    }
}
