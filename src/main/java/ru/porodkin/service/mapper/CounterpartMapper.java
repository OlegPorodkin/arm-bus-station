package ru.porodkin.service.mapper;

import org.mapstruct.*;
import ru.porodkin.domain.*;
import ru.porodkin.service.dto.CounterpartDTO;

/**
 * Mapper for the entity {@link Counterpart} and its DTO {@link CounterpartDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CounterpartMapper extends EntityMapper<CounterpartDTO, Counterpart> {}
