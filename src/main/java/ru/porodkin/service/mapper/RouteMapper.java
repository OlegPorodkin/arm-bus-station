package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.RouteDTO;

/**
 * Mapper for the entity {@link Route} and its DTO {@link RouteDTO}.
 */
@Mapper(componentModel = "spring", uses = { BusMapper.class, StationMapper.class })
public interface RouteMapper extends EntityMapper<RouteDTO, Route> {
    @Mapping(target = "bus", source = "bus")
    @Mapping(target = "station", source = "station")
    RouteDTO toDto(Route s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "bus", source = "bus")
    RouteDTO toDtoId(Route route);
}
