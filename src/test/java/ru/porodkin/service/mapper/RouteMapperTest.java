package ru.porodkin.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RouteMapperTest {

    private RouteMapper routeMapper;

    @BeforeEach
    public void setUp() {
        routeMapper = new RouteMapperImpl();
    }
}
