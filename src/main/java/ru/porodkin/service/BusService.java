package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.BusDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Bus}.
 */
public interface BusService {
    /**
     * Save a bus.
     *
     * @param busDTO the entity to save.
     * @return the persisted entity.
     */
    BusDTO save(BusDTO busDTO);

    /**
     * Partially updates a bus.
     *
     * @param busDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusDTO> partialUpdate(BusDTO busDTO);

    /**
     * Get all the buses.
     *
     * @return the list of entities.
     */
    List<BusDTO> findAll();

    /**
     * Get the "id" bus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusDTO> findOne(Long id);

    /**
     * Delete the "id" bus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
