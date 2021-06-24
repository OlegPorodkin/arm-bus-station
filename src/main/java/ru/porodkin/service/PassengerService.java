package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.PassengerDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Passenger}.
 */
public interface PassengerService {
    /**
     * Save a passenger.
     *
     * @param passengerDTO the entity to save.
     * @return the persisted entity.
     */
    PassengerDTO save(PassengerDTO passengerDTO);

    /**
     * Partially updates a passenger.
     *
     * @param passengerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PassengerDTO> partialUpdate(PassengerDTO passengerDTO);

    /**
     * Get all the passengers.
     *
     * @return the list of entities.
     */
    List<PassengerDTO> findAll();

    /**
     * Get the "id" passenger.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassengerDTO> findOne(Long id);

    /**
     * Delete the "id" passenger.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<PassengerDTO> findByRoute(Long id);
}
