package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "country", source = "country", qualifiedByName = "id")
    @Mapping(target = "countryOfLocation", source = "countryOfLocation", qualifiedByName = "id")
    RegionDTO toDto(Region s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RegionDTO toDtoName(Region region);
}
