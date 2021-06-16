package ru.porodkin.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.Route;
import ru.porodkin.repository.BusRepository;
import ru.porodkin.repository.RouteRepository;
import ru.porodkin.service.RouteService;
import ru.porodkin.service.dto.RouteDTO;
import ru.porodkin.service.mapper.RouteMapper;

/**
 * Service Implementation for managing {@link Route}.
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    private final BusRepository busRepository;

    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper, BusRepository busRepository) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.busRepository = busRepository;
    }

    @Override
    public RouteDTO save(RouteDTO routeDTO) {
        log.debug("Request to save Route : {}", routeDTO);
        Route route = routeMapper.toEntity(routeDTO);
        Long busId = routeDTO.getBus().getId();
        busRepository.findById(busId).ifPresent(route::bus);
        route = routeRepository.save(route);
        return routeMapper.toDto(route);
    }

    @Override
    public Optional<RouteDTO> partialUpdate(RouteDTO routeDTO) {
        log.debug("Request to partially update Route : {}", routeDTO);

        return routeRepository
            .findById(routeDTO.getId())
            .map(
                existingRoute -> {
                    routeMapper.partialUpdate(existingRoute, routeDTO);
                    return existingRoute;
                }
            )
            .map(routeRepository::save)
            .map(routeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteDTO> findAll() {
        log.debug("Request to get all Routes");
        return routeRepository.findAll().stream().map(routeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RouteDTO> findOne(Long id) {
        log.debug("Request to get Route : {}", id);
        return routeRepository.findById(id).map(routeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Route : {}", id);
        routeRepository.deleteById(id);
    }
}
