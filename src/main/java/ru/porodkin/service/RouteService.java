package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.RouteDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Route}.
 */
public interface RouteService {
    /**
     * Save a route.
     *
     * @param routeDTO the entity to save.
     * @return the persisted entity.
     */
    RouteDTO save(RouteDTO routeDTO);

    /**
     * Partially updates a route.
     *
     * @param routeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RouteDTO> partialUpdate(RouteDTO routeDTO);

    /**
     * Get all the routes.
     *
     * @return the list of entities.
     */
    List<RouteDTO> findAll();

    /**
     * Get the "id" route.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RouteDTO> findOne(Long id);

    /**
     * Delete the "id" route.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
