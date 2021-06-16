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
import ru.porodkin.domain.Counterpart;
import ru.porodkin.repository.CounterpartRepository;
import ru.porodkin.service.dto.CounterpartDTO;
import ru.porodkin.service.mapper.CounterpartMapper;

/**
 * Integration tests for the {@link CounterpartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CounterpartResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIN = "AAAAAAAAAA";
    private static final String UPDATED_TIN = "BBBBBBBBBB";

    private static final String DEFAULT_OGRN = "AAAAAAAAAA";
    private static final String UPDATED_OGRN = "BBBBBBBBBB";

    private static final String DEFAULT_EGIS_OTB_RF = "AAAAAAAAAA";
    private static final String UPDATED_EGIS_OTB_RF = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_TAX_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/counterparts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CounterpartRepository counterpartRepository;

    @Autowired
    private CounterpartMapper counterpartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCounterpartMockMvc;

    private Counterpart counterpart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Counterpart createEntity(EntityManager em) {
        Counterpart counterpart = new Counterpart()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .tin(DEFAULT_TIN)
            .ogrn(DEFAULT_OGRN)
            .egis_otb_rf(DEFAULT_EGIS_OTB_RF)
            .taxSystem(DEFAULT_TAX_SYSTEM)
            .address(DEFAULT_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .country(DEFAULT_COUNTRY);
        return counterpart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Counterpart createUpdatedEntity(EntityManager em) {
        Counterpart counterpart = new Counterpart()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .tin(UPDATED_TIN)
            .ogrn(UPDATED_OGRN)
            .egis_otb_rf(UPDATED_EGIS_OTB_RF)
            .taxSystem(UPDATED_TAX_SYSTEM)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY);
        return counterpart;
    }

    @BeforeEach
    public void initTest() {
        counterpart = createEntity(em);
    }

    @Test
    @Transactional
    void createCounterpart() throws Exception {
        int databaseSizeBeforeCreate = counterpartRepository.findAll().size();
        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);
        restCounterpartMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeCreate + 1);
        Counterpart testCounterpart = counterpartList.get(counterpartList.size() - 1);
        assertThat(testCounterpart.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCounterpart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCounterpart.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCounterpart.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testCounterpart.getOgrn()).isEqualTo(DEFAULT_OGRN);
        assertThat(testCounterpart.getEgis_otb_rf()).isEqualTo(DEFAULT_EGIS_OTB_RF);
        assertThat(testCounterpart.getTaxSystem()).isEqualTo(DEFAULT_TAX_SYSTEM);
        assertThat(testCounterpart.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCounterpart.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCounterpart.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createCounterpartWithExistingId() throws Exception {
        // Create the Counterpart with an existing ID
        counterpart.setId(1L);
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        int databaseSizeBeforeCreate = counterpartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCounterpartMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCounterparts() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        // Get all the counterpartList
        restCounterpartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counterpart.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN)))
            .andExpect(jsonPath("$.[*].ogrn").value(hasItem(DEFAULT_OGRN)))
            .andExpect(jsonPath("$.[*].egis_otb_rf").value(hasItem(DEFAULT_EGIS_OTB_RF)))
            .andExpect(jsonPath("$.[*].taxSystem").value(hasItem(DEFAULT_TAX_SYSTEM)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getCounterpart() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        // Get the counterpart
        restCounterpartMockMvc
            .perform(get(ENTITY_API_URL_ID, counterpart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(counterpart.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN))
            .andExpect(jsonPath("$.ogrn").value(DEFAULT_OGRN))
            .andExpect(jsonPath("$.egis_otb_rf").value(DEFAULT_EGIS_OTB_RF))
            .andExpect(jsonPath("$.taxSystem").value(DEFAULT_TAX_SYSTEM))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getNonExistingCounterpart() throws Exception {
        // Get the counterpart
        restCounterpartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCounterpart() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();

        // Update the counterpart
        Counterpart updatedCounterpart = counterpartRepository.findById(counterpart.getId()).get();
        // Disconnect from session so that the updates on updatedCounterpart are not directly saved in db
        em.detach(updatedCounterpart);
        updatedCounterpart
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .tin(UPDATED_TIN)
            .ogrn(UPDATED_OGRN)
            .egis_otb_rf(UPDATED_EGIS_OTB_RF)
            .taxSystem(UPDATED_TAX_SYSTEM)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY);
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(updatedCounterpart);

        restCounterpartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, counterpartDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isOk());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
        Counterpart testCounterpart = counterpartList.get(counterpartList.size() - 1);
        assertThat(testCounterpart.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCounterpart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCounterpart.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCounterpart.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testCounterpart.getOgrn()).isEqualTo(UPDATED_OGRN);
        assertThat(testCounterpart.getEgis_otb_rf()).isEqualTo(UPDATED_EGIS_OTB_RF);
        assertThat(testCounterpart.getTaxSystem()).isEqualTo(UPDATED_TAX_SYSTEM);
        assertThat(testCounterpart.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCounterpart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCounterpart.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, counterpartDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCounterpartWithPatch() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();

        // Update the counterpart using partial update
        Counterpart partialUpdatedCounterpart = new Counterpart();
        partialUpdatedCounterpart.setId(counterpart.getId());

        partialUpdatedCounterpart.shortName(UPDATED_SHORT_NAME).tin(UPDATED_TIN).ogrn(UPDATED_OGRN);

        restCounterpartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCounterpart.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCounterpart))
            )
            .andExpect(status().isOk());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
        Counterpart testCounterpart = counterpartList.get(counterpartList.size() - 1);
        assertThat(testCounterpart.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCounterpart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCounterpart.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCounterpart.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testCounterpart.getOgrn()).isEqualTo(UPDATED_OGRN);
        assertThat(testCounterpart.getEgis_otb_rf()).isEqualTo(DEFAULT_EGIS_OTB_RF);
        assertThat(testCounterpart.getTaxSystem()).isEqualTo(DEFAULT_TAX_SYSTEM);
        assertThat(testCounterpart.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCounterpart.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCounterpart.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateCounterpartWithPatch() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();

        // Update the counterpart using partial update
        Counterpart partialUpdatedCounterpart = new Counterpart();
        partialUpdatedCounterpart.setId(counterpart.getId());

        partialUpdatedCounterpart
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .tin(UPDATED_TIN)
            .ogrn(UPDATED_OGRN)
            .egis_otb_rf(UPDATED_EGIS_OTB_RF)
            .taxSystem(UPDATED_TAX_SYSTEM)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .country(UPDATED_COUNTRY);

        restCounterpartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCounterpart.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCounterpart))
            )
            .andExpect(status().isOk());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
        Counterpart testCounterpart = counterpartList.get(counterpartList.size() - 1);
        assertThat(testCounterpart.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCounterpart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCounterpart.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCounterpart.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testCounterpart.getOgrn()).isEqualTo(UPDATED_OGRN);
        assertThat(testCounterpart.getEgis_otb_rf()).isEqualTo(UPDATED_EGIS_OTB_RF);
        assertThat(testCounterpart.getTaxSystem()).isEqualTo(UPDATED_TAX_SYSTEM);
        assertThat(testCounterpart.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCounterpart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCounterpart.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, counterpartDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCounterpart() throws Exception {
        int databaseSizeBeforeUpdate = counterpartRepository.findAll().size();
        counterpart.setId(count.incrementAndGet());

        // Create the Counterpart
        CounterpartDTO counterpartDTO = counterpartMapper.toDto(counterpart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterpartMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(counterpartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Counterpart in the database
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCounterpart() throws Exception {
        // Initialize the database
        counterpartRepository.saveAndFlush(counterpart);

        int databaseSizeBeforeDelete = counterpartRepository.findAll().size();

        // Delete the counterpart
        restCounterpartMockMvc
            .perform(delete(ENTITY_API_URL_ID, counterpart.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Counterpart> counterpartList = counterpartRepository.findAll();
        assertThat(counterpartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
