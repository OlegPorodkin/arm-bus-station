package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.TypeObjectDTO;

/**
 * Mapper for the entity {@link TypeObject} and its DTO {@link TypeObjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeObjectMapper extends EntityMapper<TypeObjectDTO, TypeObject> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TypeObjectDTO toDtoName(TypeObject typeObject);
}
