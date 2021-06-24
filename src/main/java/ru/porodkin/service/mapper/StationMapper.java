package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.StationDTO;

/**
 * Mapper for the entity {@link Station} and its DTO {@link StationDTO}.
 */
@Mapper(componentModel = "spring", uses = { TypeObjectMapper.class, RegionMapper.class })
public interface StationMapper extends EntityMapper<StationDTO, Station> {
    @Mapping(target = "nextStation", source = "nextStation")
    @Mapping(target = "typeObject", source = "typeObject")
    @Mapping(target = "region", source = "region")
    StationDTO toDto(Station s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StationDTO toDtoName(Station station);
}
