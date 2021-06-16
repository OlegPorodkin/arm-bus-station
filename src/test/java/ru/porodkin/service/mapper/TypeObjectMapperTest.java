package ru.porodkin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeObjectMapperTest {

    private TypeObjectMapper typeObjectMapper;

    @BeforeEach
    public void setUp() {
        typeObjectMapper = new TypeObjectMapperImpl();
    }
}
