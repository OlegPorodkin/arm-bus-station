package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.DriverDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Driver}.
 */
public interface DriverService {
    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save.
     * @return the persisted entity.
     */
    DriverDTO save(DriverDTO driverDTO);

    /**
     * Partially updates a driver.
     *
     * @param driverDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DriverDTO> partialUpdate(DriverDTO driverDTO);

    /**
     * Get all the drivers.
     *
     * @return the list of entities.
     */
    List<DriverDTO> findAll();

    /**
     * Get the "id" driver.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DriverDTO> findOne(Long id);

    /**
     * Delete the "id" driver.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
