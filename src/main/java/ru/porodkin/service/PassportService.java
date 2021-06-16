package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.PassportDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Passport}.
 */
public interface PassportService {
    /**
     * Save a passport.
     *
     * @param passportDTO the entity to save.
     * @return the persisted entity.
     */
    PassportDTO save(PassportDTO passportDTO);

    /**
     * Partially updates a passport.
     *
     * @param passportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PassportDTO> partialUpdate(PassportDTO passportDTO);

    /**
     * Get all the passports.
     *
     * @return the list of entities.
     */
    List<PassportDTO> findAll();

    /**
     * Get the "id" passport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassportDTO> findOne(Long id);

    /**
     * Delete the "id" passport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
