package ru.porodkin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerMapperTest {

    private PassengerMapper passengerMapper;

    @BeforeEach
    public void setUp() {
        passengerMapper = new PassengerMapperImpl();
    }
}
