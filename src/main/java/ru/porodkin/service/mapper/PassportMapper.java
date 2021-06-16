package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.PassportDTO;

/**
 * Mapper for the entity {@link Passport} and its DTO {@link PassportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PassportMapper extends EntityMapper<PassportDTO, Passport> {}
