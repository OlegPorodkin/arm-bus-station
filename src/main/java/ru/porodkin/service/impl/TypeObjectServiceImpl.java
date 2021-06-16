package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.TypeObject;
import ru.porodkin.repository.TypeObjectRepository;
import ru.porodkin.service.TypeObjectService;
import ru.porodkin.service.dto.TypeObjectDTO;
import ru.porodkin.service.mapper.TypeObjectMapper;

/**
 * Service Implementation for managing {@link TypeObject}.
 */
@Service
@Transactional
public class TypeObjectServiceImpl implements TypeObjectService {

    private final Logger log = LoggerFactory.getLogger(TypeObjectServiceImpl.class);

    private final TypeObjectRepository typeObjectRepository;

    private final TypeObjectMapper typeObjectMapper;

    public TypeObjectServiceImpl(TypeObjectRepository typeObjectRepository, TypeObjectMapper typeObjectMapper) {
        this.typeObjectRepository = typeObjectRepository;
        this.typeObjectMapper = typeObjectMapper;
    }

    @Override
    public TypeObjectDTO save(TypeObjectDTO typeObjectDTO) {
        log.debug("Request to save TypeObject : {}", typeObjectDTO);
        TypeObject typeObject = typeObjectMapper.toEntity(typeObjectDTO);
        typeObject = typeObjectRepository.save(typeObject);
        return typeObjectMapper.toDto(typeObject);
    }

    @Override
    public Optional<TypeObjectDTO> partialUpdate(TypeObjectDTO typeObjectDTO) {
        log.debug("Request to partially update TypeObject : {}", typeObjectDTO);

        return typeObjectRepository
            .findById(typeObjectDTO.getId())
            .map(
                existingTypeObject -> {
                    typeObjectMapper.partialUpdate(existingTypeObject, typeObjectDTO);
                    return existingTypeObject;
                }
            )
            .map(typeObjectRepository::save)
            .map(typeObjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeObjectDTO> findAll() {
        log.debug("Request to get all TypeObjects");
        return typeObjectRepository.findAll().stream().map(typeObjectMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeObjectDTO> findOne(Long id) {
        log.debug("Request to get TypeObject : {}", id);
        return typeObjectRepository.findById(id).map(typeObjectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeObject : {}", id);
        typeObjectRepository.deleteById(id);
    }
}
