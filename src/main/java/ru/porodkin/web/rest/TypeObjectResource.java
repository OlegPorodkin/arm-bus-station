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
import ru.porodkin.repository.TypeObjectRepository;
import ru.porodkin.service.TypeObjectService;
import ru.porodkin.service.dto.TypeObjectDTO;
import ru.porodkin.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ru.porodkin.domain.TypeObject}.
 */
@RestController
@RequestMapping("/api")
public class TypeObjectResource {

    private final Logger log = LoggerFactory.getLogger(TypeObjectResource.class);

    private static final String ENTITY_NAME = "typeObject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeObjectService typeObjectService;

    private final TypeObjectRepository typeObjectRepository;

    public TypeObjectResource(TypeObjectService typeObjectService, TypeObjectRepository typeObjectRepository) {
        this.typeObjectService = typeObjectService;
        this.typeObjectRepository = typeObjectRepository;
    }

    /**
     * {@code POST  /type-objects} : Create a new typeObject.
     *
     * @param typeObjectDTO the typeObjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeObjectDTO, or with status {@code 400 (Bad Request)} if the typeObject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-objects")
    public ResponseEntity<TypeObjectDTO> createTypeObject(@RequestBody TypeObjectDTO typeObjectDTO) throws URISyntaxException {
        log.debug("REST request to save TypeObject : {}", typeObjectDTO);
        if (typeObjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeObjectDTO result = typeObjectService.save(typeObjectDTO);
        return ResponseEntity
            .created(new URI("/api/type-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-objects/:id} : Updates an existing typeObject.
     *
     * @param id the id of the typeObjectDTO to save.
     * @param typeObjectDTO the typeObjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeObjectDTO,
     * or with status {@code 400 (Bad Request)} if the typeObjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeObjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-objects/{id}")
    public ResponseEntity<TypeObjectDTO> updateTypeObject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeObjectDTO typeObjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeObject : {}, {}", id, typeObjectDTO);
        if (typeObjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeObjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeObjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeObjectDTO result = typeObjectService.save(typeObjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeObjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-objects/:id} : Partial updates given fields of an existing typeObject, field will ignore if it is null
     *
     * @param id the id of the typeObjectDTO to save.
     * @param typeObjectDTO the typeObjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeObjectDTO,
     * or with status {@code 400 (Bad Request)} if the typeObjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeObjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeObjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-objects/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TypeObjectDTO> partialUpdateTypeObject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeObjectDTO typeObjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeObject partially : {}, {}", id, typeObjectDTO);
        if (typeObjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeObjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeObjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeObjectDTO> result = typeObjectService.partialUpdate(typeObjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeObjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-objects} : get all the typeObjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeObjects in body.
     */
    @GetMapping("/type-objects")
    public List<TypeObjectDTO> getAllTypeObjects() {
        log.debug("REST request to get all TypeObjects");
        return typeObjectService.findAll();
    }

    /**
     * {@code GET  /type-objects/:id} : get the "id" typeObject.
     *
     * @param id the id of the typeObjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeObjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-objects/{id}")
    public ResponseEntity<TypeObjectDTO> getTypeObject(@PathVariable Long id) {
        log.debug("REST request to get TypeObject : {}", id);
        Optional<TypeObjectDTO> typeObjectDTO = typeObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeObjectDTO);
    }

    /**
     * {@code DELETE  /type-objects/:id} : delete the "id" typeObject.
     *
     * @param id the id of the typeObjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-objects/{id}")
    public ResponseEntity<Void> deleteTypeObject(@PathVariable Long id) {
        log.debug("REST request to delete TypeObject : {}", id);
        typeObjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
