package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.TypeObjectDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.TypeObject}.
 */
public interface TypeObjectService {
    /**
     * Save a typeObject.
     *
     * @param typeObjectDTO the entity to save.
     * @return the persisted entity.
     */
    TypeObjectDTO save(TypeObjectDTO typeObjectDTO);

    /**
     * Partially updates a typeObject.
     *
     * @param typeObjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeObjectDTO> partialUpdate(TypeObjectDTO typeObjectDTO);

    /**
     * Get all the typeObjects.
     *
     * @return the list of entities.
     */
    List<TypeObjectDTO> findAll();

    /**
     * Get the "id" typeObject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeObjectDTO> findOne(Long id);

    /**
     * Delete the "id" typeObject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
