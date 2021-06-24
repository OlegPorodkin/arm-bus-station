package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.BusDTO;

/**
 * Mapper for the entity {@link Bus} and its DTO {@link BusDTO}.
 */
@Mapper(componentModel = "spring", uses = { DriverMapper.class, CounterpartMapper.class })
public interface BusMapper extends EntityMapper<BusDTO, Bus> {
    @Mapping(target = "driver", source = "driver")
    @Mapping(target = "counterpart", source = "counterpart")
    BusDTO toDto(Bus s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BusDTO toDtoId(Bus bus);
}
