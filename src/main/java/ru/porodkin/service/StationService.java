package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.StationDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Station}.
 */
public interface StationService {
    /**
     * Save a station.
     *
     * @param stationDTO the entity to save.
     * @return the persisted entity.
     */
    StationDTO save(StationDTO stationDTO);

    /**
     * Partially updates a station.
     *
     * @param stationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StationDTO> partialUpdate(StationDTO stationDTO);

    /**
     * Get all the stations.
     *
     * @return the list of entities.
     */
    List<StationDTO> findAll();

    /**
     * Get the "id" station.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StationDTO> findOne(Long id);

    /**
     * Delete the "id" station.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
