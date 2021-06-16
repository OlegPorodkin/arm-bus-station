package ru.porodkin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CounterpartMapperTest {

    private CounterpartMapper counterpartMapper;

    @BeforeEach
    public void setUp() {
        counterpartMapper = new CounterpartMapperImpl();
    }
}
