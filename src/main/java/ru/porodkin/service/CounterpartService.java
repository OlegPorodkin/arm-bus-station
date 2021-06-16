package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.CounterpartDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Counterpart}.
 */
public interface CounterpartService {
    /**
     * Save a counterpart.
     *
     * @param counterpartDTO the entity to save.
     * @return the persisted entity.
     */
    CounterpartDTO save(CounterpartDTO counterpartDTO);

    /**
     * Partially updates a counterpart.
     *
     * @param counterpartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CounterpartDTO> partialUpdate(CounterpartDTO counterpartDTO);

    /**
     * Get all the counterparts.
     *
     * @return the list of entities.
     */
    List<CounterpartDTO> findAll();

    /**
     * Get the "id" counterpart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CounterpartDTO> findOne(Long id);

    /**
     * Delete the "id" counterpart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
