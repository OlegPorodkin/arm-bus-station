package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.PassengerDTO;

/**
 * Mapper for the entity {@link Passenger} and its DTO {@link PassengerDTO}.
 */
@Mapper(componentModel = "spring", uses = { RouteMapper.class, TicketMapper.class })
public interface PassengerMapper extends EntityMapper<PassengerDTO, Passenger> {
    @Mapping(target = "route", source = "route", qualifiedByName = "id")
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "id")
    PassengerDTO toDto(Passenger s);
}
