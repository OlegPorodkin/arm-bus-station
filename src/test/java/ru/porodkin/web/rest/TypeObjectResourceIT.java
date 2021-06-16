package ru.porodkin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.IntegrationTest;
import ru.porodkin.domain.TypeObject;
import ru.porodkin.repository.TypeObjectRepository;
import ru.porodkin.service.dto.TypeObjectDTO;
import ru.porodkin.service.mapper.TypeObjectMapper;

/**
 * Integration tests for the {@link TypeObjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeObjectResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-objects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeObjectRepository typeObjectRepository;

    @Autowired
    private TypeObjectMapper typeObjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeObjectMockMvc;

    private TypeObject typeObject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeObject createEntity(EntityManager em) {
        TypeObject typeObject = new TypeObject().uuid(DEFAULT_UUID).name(DEFAULT_NAME);
        return typeObject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeObject createUpdatedEntity(EntityManager em) {
        TypeObject typeObject = new TypeObject().uuid(UPDATED_UUID).name(UPDATED_NAME);
        return typeObject;
    }

    @BeforeEach
    public void initTest() {
        typeObject = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeObject() throws Exception {
        int databaseSizeBeforeCreate = typeObjectRepository.findAll().size();
        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);
        restTypeObjectMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeCreate + 1);
        TypeObject testTypeObject = typeObjectList.get(typeObjectList.size() - 1);
        assertThat(testTypeObject.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testTypeObject.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTypeObjectWithExistingId() throws Exception {
        // Create the TypeObject with an existing ID
        typeObject.setId(1L);
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        int databaseSizeBeforeCreate = typeObjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeObjectMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeObjects() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        // Get all the typeObjectList
        restTypeObjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTypeObject() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        // Get the typeObject
        restTypeObjectMockMvc
            .perform(get(ENTITY_API_URL_ID, typeObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeObject.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTypeObject() throws Exception {
        // Get the typeObject
        restTypeObjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeObject() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();

        // Update the typeObject
        TypeObject updatedTypeObject = typeObjectRepository.findById(typeObject.getId()).get();
        // Disconnect from session so that the updates on updatedTypeObject are not directly saved in db
        em.detach(updatedTypeObject);
        updatedTypeObject.uuid(UPDATED_UUID).name(UPDATED_NAME);
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(updatedTypeObject);

        restTypeObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeObjectDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
        TypeObject testTypeObject = typeObjectList.get(typeObjectList.size() - 1);
        assertThat(testTypeObject.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testTypeObject.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeObjectDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeObjectWithPatch() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();

        // Update the typeObject using partial update
        TypeObject partialUpdatedTypeObject = new TypeObject();
        partialUpdatedTypeObject.setId(typeObject.getId());

        partialUpdatedTypeObject.name(UPDATED_NAME);

        restTypeObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeObject.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeObject))
            )
            .andExpect(status().isOk());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
        TypeObject testTypeObject = typeObjectList.get(typeObjectList.size() - 1);
        assertThat(testTypeObject.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testTypeObject.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTypeObjectWithPatch() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();

        // Update the typeObject using partial update
        TypeObject partialUpdatedTypeObject = new TypeObject();
        partialUpdatedTypeObject.setId(typeObject.getId());

        partialUpdatedTypeObject.uuid(UPDATED_UUID).name(UPDATED_NAME);

        restTypeObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeObject.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeObject))
            )
            .andExpect(status().isOk());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
        TypeObject testTypeObject = typeObjectList.get(typeObjectList.size() - 1);
        assertThat(testTypeObject.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testTypeObject.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeObjectDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeObject() throws Exception {
        int databaseSizeBeforeUpdate = typeObjectRepository.findAll().size();
        typeObject.setId(count.incrementAndGet());

        // Create the TypeObject
        TypeObjectDTO typeObjectDTO = typeObjectMapper.toDto(typeObject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeObjectMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeObjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeObject in the database
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeObject() throws Exception {
        // Initialize the database
        typeObjectRepository.saveAndFlush(typeObject);

        int databaseSizeBeforeDelete = typeObjectRepository.findAll().size();

        // Delete the typeObject
        restTypeObjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeObject.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeObject> typeObjectList = typeObjectRepository.findAll();
        assertThat(typeObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
