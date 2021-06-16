package ru.porodkin.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.porodkin.repository.CounterpartRepository;
import ru.porodkin.service.CounterpartService;
import ru.porodkin.service.dto.CounterpartDTO;
import ru.porodkin.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ru.porodkin.domain.Counterpart}.
 */
@RestController
@RequestMapping("/api")
public class CounterpartResource {

    private final Logger log = LoggerFactory.getLogger(CounterpartResource.class);

    private static final String ENTITY_NAME = "counterpart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CounterpartService counterpartService;

    private final CounterpartRepository counterpartRepository;

    public CounterpartResource(CounterpartService counterpartService, CounterpartRepository counterpartRepository) {
        this.counterpartService = counterpartService;
        this.counterpartRepository = counterpartRepository;
    }

    /**
     * {@code POST  /counterparts} : Create a new counterpart.
     *
     * @param counterpartDTO the counterpartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new counterpartDTO, or with status {@code 400 (Bad Request)} if the counterpart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counterparts")
    public ResponseEntity<CounterpartDTO> createCounterpart(@RequestBody CounterpartDTO counterpartDTO) throws URISyntaxException {
        log.debug("REST request to save Counterpart : {}", counterpartDTO);
        if (counterpartDTO.getId() != null) {
            throw new BadRequestAlertException("A new counterpart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CounterpartDTO result = counterpartService.save(counterpartDTO);
        return ResponseEntity
            .created(new URI("/api/counterparts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counterparts/:id} : Updates an existing counterpart.
     *
     * @param id the id of the counterpartDTO to save.
     * @param counterpartDTO the counterpartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterpartDTO,
     * or with status {@code 400 (Bad Request)} if the counterpartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the counterpartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counterparts/{id}")
    public ResponseEntity<CounterpartDTO> updateCounterpart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CounterpartDTO counterpartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Counterpart : {}, {}", id, counterpartDTO);
        if (counterpartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, counterpartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!counterpartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CounterpartDTO result = counterpartService.save(counterpartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, counterpartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /counterparts/:id} : Partial updates given fields of an existing counterpart, field will ignore if it is null
     *
     * @param id the id of the counterpartDTO to save.
     * @param counterpartDTO the counterpartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterpartDTO,
     * or with status {@code 400 (Bad Request)} if the counterpartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the counterpartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the counterpartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/counterparts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CounterpartDTO> partialUpdateCounterpart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CounterpartDTO counterpartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Counterpart partially : {}, {}", id, counterpartDTO);
        if (counterpartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, counterpartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!counterpartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CounterpartDTO> result = counterpartService.partialUpdate(counterpartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, counterpartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /counterparts} : get all the counterparts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counterparts in body.
     */
    @GetMapping("/counterparts")
    public List<CounterpartDTO> getAllCounterparts() {
        log.debug("REST request to get all Counterparts");
        return counterpartService.findAll();
    }

    /**
     * {@code GET  /counterparts/:id} : get the "id" counterpart.
     *
     * @param id the id of the counterpartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the counterpartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counterparts/{id}")
    public ResponseEntity<CounterpartDTO> getCounterpart(@PathVariable Long id) {
        log.debug("REST request to get Counterpart : {}", id);
        Optional<CounterpartDTO> counterpartDTO = counterpartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(counterpartDTO);
    }

    /**
     * {@code DELETE  /counterparts/:id} : delete the "id" counterpart.
     *
     * @param id the id of the counterpartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counterparts/{id}")
    public ResponseEntity<Void> deleteCounterpart(@PathVariable Long id) {
        log.debug("REST request to delete Counterpart : {}", id);
        counterpartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
