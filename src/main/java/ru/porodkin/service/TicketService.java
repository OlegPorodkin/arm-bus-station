package ru.porodkin.service;

import java.util.List;
import java.util.Optional;
import ru.porodkin.service.dto.TicketDTO;

/**
 * Service Interface for managing {@link ru.porodkin.domain.Ticket}.
 */
public interface TicketService {
    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    TicketDTO save(TicketDTO ticketDTO);

    /**
     * Partially updates a ticket.
     *
     * @param ticketDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketDTO> partialUpdate(TicketDTO ticketDTO);

    /**
     * Get all the tickets.
     *
     * @return the list of entities.
     */
    List<TicketDTO> findAll();
    /**
     * Get all the TicketDTO where Passenger is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TicketDTO> findAllWherePassengerIsNull();

    /**
     * Get the "id" ticket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketDTO> findOne(Long id);

    /**
     * Delete the "id" ticket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
