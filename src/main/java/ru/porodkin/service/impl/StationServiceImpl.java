package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.Station;
import ru.porodkin.repository.StationRepository;
import ru.porodkin.service.StationService;
import ru.porodkin.service.dto.StationDTO;
import ru.porodkin.service.mapper.StationMapper;

/**
 * Service Implementation for managing {@link Station}.
 */
@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    @Override
    public StationDTO save(StationDTO stationDTO) {
        log.debug("Request to save Station : {}", stationDTO);
        Station station = stationMapper.toEntity(stationDTO);
        station = stationRepository.save(station);
        return stationMapper.toDto(station);
    }

    @Override
    public Optional<StationDTO> partialUpdate(StationDTO stationDTO) {
        log.debug("Request to partially update Station : {}", stationDTO);

        return stationRepository
            .findById(stationDTO.getId())
            .map(
                existingStation -> {
                    stationMapper.partialUpdate(existingStation, stationDTO);
                    return existingStation;
                }
            )
            .map(stationRepository::save)
            .map(stationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationDTO> findAll() {
        log.debug("Request to get all Stations");
        return stationRepository.findAll().stream().map(stationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationDTO> findOne(Long id) {
        log.debug("Request to get Station : {}", id);
        return stationRepository.findById(id).map(stationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Station : {}", id);
        stationRepository.deleteById(id);
    }
}
