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
import ru.porodkin.domain.Station;
import ru.porodkin.repository.StationRepository;
import ru.porodkin.service.dto.StationDTO;
import ru.porodkin.service.mapper.StationMapper;

/**
 * Integration tests for the {@link StationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StationResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_OKATO = 1;
    private static final Integer UPDATED_OKATO = 2;

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Integer DEFAULT_DISTANCE = 1;
    private static final Integer UPDATED_DISTANCE = 2;

    private static final String ENTITY_API_URL = "/api/stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStationMockMvc;

    private Station station;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createEntity(EntityManager em) {
        Station station = new Station()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .okato(DEFAULT_OKATO)
            .street(DEFAULT_STREET)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .distance(DEFAULT_DISTANCE);
        return station;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Station createUpdatedEntity(EntityManager em) {
        Station station = new Station()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .okato(UPDATED_OKATO)
            .street(UPDATED_STREET)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .distance(UPDATED_DISTANCE);
        return station;
    }

    @BeforeEach
    public void initTest() {
        station = createEntity(em);
    }

    @Test
    @Transactional
    void createStation() throws Exception {
        int databaseSizeBeforeCreate = stationRepository.findAll().size();
        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);
        restStationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate + 1);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testStation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStation.getOkato()).isEqualTo(DEFAULT_OKATO);
        assertThat(testStation.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testStation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testStation.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    void createStationWithExistingId() throws Exception {
        // Create the Station with an existing ID
        station.setId(1L);
        StationDTO stationDTO = stationMapper.toDto(station);

        int databaseSizeBeforeCreate = stationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStations() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get all the stationList
        restStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(station.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].okato").value(hasItem(DEFAULT_OKATO)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)));
    }

    @Test
    @Transactional
    void getStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        // Get the station
        restStationMockMvc
            .perform(get(ENTITY_API_URL_ID, station.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(station.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.okato").value(DEFAULT_OKATO))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE));
    }

    @Test
    @Transactional
    void getNonExistingStation() throws Exception {
        // Get the station
        restStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station
        Station updatedStation = stationRepository.findById(station.getId()).get();
        // Disconnect from session so that the updates on updatedStation are not directly saved in db
        em.detach(updatedStation);
        updatedStation
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .okato(UPDATED_OKATO)
            .street(UPDATED_STREET)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .distance(UPDATED_DISTANCE);
        StationDTO stationDTO = stationMapper.toDto(updatedStation);

        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStation.getOkato()).isEqualTo(UPDATED_OKATO);
        assertThat(testStation.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void putNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation.uuid(UPDATED_UUID).street(UPDATED_STREET).latitude(UPDATED_LATITUDE);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testStation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStation.getOkato()).isEqualTo(DEFAULT_OKATO);
        assertThat(testStation.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    void fullUpdateStationWithPatch() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeUpdate = stationRepository.findAll().size();

        // Update the station using partial update
        Station partialUpdatedStation = new Station();
        partialUpdatedStation.setId(station.getId());

        partialUpdatedStation
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .okato(UPDATED_OKATO)
            .street(UPDATED_STREET)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .distance(UPDATED_DISTANCE);

        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStation))
            )
            .andExpect(status().isOk());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
        Station testStation = stationList.get(stationList.size() - 1);
        assertThat(testStation.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testStation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStation.getOkato()).isEqualTo(UPDATED_OKATO);
        assertThat(testStation.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testStation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testStation.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void patchNonExistingStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStation() throws Exception {
        int databaseSizeBeforeUpdate = stationRepository.findAll().size();
        station.setId(count.incrementAndGet());

        // Create the Station
        StationDTO stationDTO = stationMapper.toDto(station);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Station in the database
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStation() throws Exception {
        // Initialize the database
        stationRepository.saveAndFlush(station);

        int databaseSizeBeforeDelete = stationRepository.findAll().size();

        // Delete the station
        restStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, station.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Station> stationList = stationRepository.findAll();
        assertThat(stationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
