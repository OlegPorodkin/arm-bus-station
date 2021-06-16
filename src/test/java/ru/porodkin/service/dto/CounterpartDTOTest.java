package ru.porodkin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.porodkin.web.rest.TestUtil;

class CounterpartDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CounterpartDTO.class);
        CounterpartDTO counterpartDTO1 = new CounterpartDTO();
        counterpartDTO1.setId(1L);
        CounterpartDTO counterpartDTO2 = new CounterpartDTO();
        assertThat(counterpartDTO1).isNotEqualTo(counterpartDTO2);
        counterpartDTO2.setId(counterpartDTO1.getId());
        assertThat(counterpartDTO1).isEqualTo(counterpartDTO2);
        counterpartDTO2.setId(2L);
        assertThat(counterpartDTO1).isNotEqualTo(counterpartDTO2);
        counterpartDTO1.setId(null);
        assertThat(counterpartDTO1).isNotEqualTo(counterpartDTO2);
    }
}
