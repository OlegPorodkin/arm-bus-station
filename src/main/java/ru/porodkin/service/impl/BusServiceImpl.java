package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.Bus;
import ru.porodkin.repository.BusRepository;
import ru.porodkin.service.BusService;
import ru.porodkin.service.dto.BusDTO;
import ru.porodkin.service.mapper.BusMapper;

/**
 * Service Implementation for managing {@link Bus}.
 */
@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final Logger log = LoggerFactory.getLogger(BusServiceImpl.class);

    private final BusRepository busRepository;

    private final BusMapper busMapper;

    public BusServiceImpl(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    @Override
    public BusDTO save(BusDTO busDTO) {
        log.debug("Request to save Bus : {}", busDTO);
        Bus bus = busMapper.toEntity(busDTO);
        bus = busRepository.save(bus);
        return busMapper.toDto(bus);
    }

    @Override
    public Optional<BusDTO> partialUpdate(BusDTO busDTO) {
        log.debug("Request to partially update Bus : {}", busDTO);

        return busRepository
            .findById(busDTO.getId())
            .map(
                existingBus -> {
                    busMapper.partialUpdate(existingBus, busDTO);
                    return existingBus;
                }
            )
            .map(busRepository::save)
            .map(busMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusDTO> findAll() {
        log.debug("Request to get all Buses");
        return busRepository.findAll().stream().map(busMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusDTO> findOne(Long id) {
        log.debug("Request to get Bus : {}", id);
        return busRepository.findById(id).map(busMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bus : {}", id);
        busRepository.deleteById(id);
    }
}
