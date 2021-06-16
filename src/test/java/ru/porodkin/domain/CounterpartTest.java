package ru.porodkin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.porodkin.web.rest.TestUtil;

class CounterpartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Counterpart.class);
        Counterpart counterpart1 = new Counterpart();
        counterpart1.setId(1L);
        Counterpart counterpart2 = new Counterpart();
        counterpart2.setId(counterpart1.getId());
        assertThat(counterpart1).isEqualTo(counterpart2);
        counterpart2.setId(2L);
        assertThat(counterpart1).isNotEqualTo(counterpart2);
        counterpart1.setId(null);
        assertThat(counterpart1).isNotEqualTo(counterpart2);
    }
}
