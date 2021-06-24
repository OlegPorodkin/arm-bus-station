package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.TicketDTO;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring", uses = { StationMapper.class })
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {
    @Mapping(target = "departure", source = "departure", qualifiedByName = "name")
    @Mapping(target = "destination", source = "destination", qualifiedByName = "name")
    TicketDTO toDto(Ticket s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TicketDTO toDtoId(Ticket ticket);
}
