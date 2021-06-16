package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.Counterpart;
import ru.porodkin.repository.CounterpartRepository;
import ru.porodkin.service.CounterpartService;
import ru.porodkin.service.dto.CounterpartDTO;
import ru.porodkin.service.mapper.CounterpartMapper;

/**
 * Service Implementation for managing {@link Counterpart}.
 */
@Service
@Transactional
public class CounterpartServiceImpl implements CounterpartService {

    private final Logger log = LoggerFactory.getLogger(CounterpartServiceImpl.class);

    private final CounterpartRepository counterpartRepository;

    private final CounterpartMapper counterpartMapper;

    public CounterpartServiceImpl(CounterpartRepository counterpartRepository, CounterpartMapper counterpartMapper) {
        this.counterpartRepository = counterpartRepository;
        this.counterpartMapper = counterpartMapper;
    }

    @Override
    public CounterpartDTO save(CounterpartDTO counterpartDTO) {
        log.debug("Request to save Counterpart : {}", counterpartDTO);
        Counterpart counterpart = counterpartMapper.toEntity(counterpartDTO);
        counterpart = counterpartRepository.save(counterpart);
        return counterpartMapper.toDto(counterpart);
    }

    @Override
    public Optional<CounterpartDTO> partialUpdate(CounterpartDTO counterpartDTO) {
        log.debug("Request to partially update Counterpart : {}", counterpartDTO);

        return counterpartRepository
            .findById(counterpartDTO.getId())
            .map(
                existingCounterpart -> {
                    counterpartMapper.partialUpdate(existingCounterpart, counterpartDTO);
                    return existingCounterpart;
                }
            )
            .map(counterpartRepository::save)
            .map(counterpartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CounterpartDTO> findAll() {
        log.debug("Request to get all Counterparts");
        return counterpartRepository.findAll().stream().map(counterpartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CounterpartDTO> findOne(Long id) {
        log.debug("Request to get Counterpart : {}", id);
        return counterpartRepository.findById(id).map(counterpartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Counterpart : {}", id);
        counterpartRepository.deleteById(id);
    }
}
