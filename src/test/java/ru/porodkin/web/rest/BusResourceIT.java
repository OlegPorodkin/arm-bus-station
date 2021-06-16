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
import ru.porodkin.domain.Bus;
import ru.porodkin.repository.BusRepository;
import ru.porodkin.service.criteria.BusCriteria;
import ru.porodkin.service.dto.BusDTO;
import ru.porodkin.service.mapper.BusMapper;

/**
 * Integration tests for the {@link BusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PASSENGER_PLACES = 1;
    private static final Integer UPDATED_PASSENGER_PLACES = 2;
    private static final Integer SMALLER_PASSENGER_PLACES = 1 - 1;

    private static final String ENTITY_API_URL = "/api/buses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusMapper busMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusMockMvc;

    private Bus bus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createEntity(EntityManager em) {
        Bus bus = new Bus()
            .model(DEFAULT_MODEL)
            .number(DEFAULT_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .passengerPlaces(DEFAULT_PASSENGER_PLACES);
        return bus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createUpdatedEntity(EntityManager em) {
        Bus bus = new Bus()
            .model(UPDATED_MODEL)
            .number(UPDATED_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .passengerPlaces(UPDATED_PASSENGER_PLACES);
        return bus;
    }

    @BeforeEach
    public void initTest() {
        bus = createEntity(em);
    }

    @Test
    @Transactional
    void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();
        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);
        restBusMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBus.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBus.getPassengerPlaces()).isEqualTo(DEFAULT_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void createBusWithExistingId() throws Exception {
        // Create the Bus with an existing ID
        bus.setId(1L);
        BusDTO busDTO = busMapper.toDto(bus);

        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuses() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].passengerPlaces").value(hasItem(DEFAULT_PASSENGER_PLACES)));
    }

    @Test
    @Transactional
    void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc
            .perform(get(ENTITY_API_URL_ID, bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bus.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.passengerPlaces").value(DEFAULT_PASSENGER_PLACES));
    }

    @Test
    @Transactional
    void getBusesByIdFiltering() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        Long id = bus.getId();

        defaultBusShouldBeFound("id.equals=" + id);
        defaultBusShouldNotBeFound("id.notEquals=" + id);

        defaultBusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusShouldNotBeFound("id.greaterThan=" + id);

        defaultBusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBusesByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model equals to DEFAULT_MODEL
        defaultBusShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the busList where model equals to UPDATED_MODEL
        defaultBusShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBusesByModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model not equals to DEFAULT_MODEL
        defaultBusShouldNotBeFound("model.notEquals=" + DEFAULT_MODEL);

        // Get all the busList where model not equals to UPDATED_MODEL
        defaultBusShouldBeFound("model.notEquals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBusesByModelIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultBusShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the busList where model equals to UPDATED_MODEL
        defaultBusShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBusesByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model is not null
        defaultBusShouldBeFound("model.specified=true");

        // Get all the busList where model is null
        defaultBusShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    void getAllBusesByModelContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model contains DEFAULT_MODEL
        defaultBusShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the busList where model contains UPDATED_MODEL
        defaultBusShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBusesByModelNotContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where model does not contain DEFAULT_MODEL
        defaultBusShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the busList where model does not contain UPDATED_MODEL
        defaultBusShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBusesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number equals to DEFAULT_NUMBER
        defaultBusShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the busList where number equals to UPDATED_NUMBER
        defaultBusShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBusesByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number not equals to DEFAULT_NUMBER
        defaultBusShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the busList where number not equals to UPDATED_NUMBER
        defaultBusShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBusesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultBusShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the busList where number equals to UPDATED_NUMBER
        defaultBusShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBusesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number is not null
        defaultBusShouldBeFound("number.specified=true");

        // Get all the busList where number is null
        defaultBusShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllBusesByNumberContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number contains DEFAULT_NUMBER
        defaultBusShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the busList where number contains UPDATED_NUMBER
        defaultBusShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBusesByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where number does not contain DEFAULT_NUMBER
        defaultBusShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the busList where number does not contain UPDATED_NUMBER
        defaultBusShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description equals to DEFAULT_DESCRIPTION
        defaultBusShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the busList where description equals to UPDATED_DESCRIPTION
        defaultBusShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description not equals to DEFAULT_DESCRIPTION
        defaultBusShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the busList where description not equals to UPDATED_DESCRIPTION
        defaultBusShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBusShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the busList where description equals to UPDATED_DESCRIPTION
        defaultBusShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description is not null
        defaultBusShouldBeFound("description.specified=true");

        // Get all the busList where description is null
        defaultBusShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description contains DEFAULT_DESCRIPTION
        defaultBusShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the busList where description contains UPDATED_DESCRIPTION
        defaultBusShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBusesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where description does not contain DEFAULT_DESCRIPTION
        defaultBusShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the busList where description does not contain UPDATED_DESCRIPTION
        defaultBusShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces equals to DEFAULT_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.equals=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces equals to UPDATED_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.equals=" + UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces not equals to DEFAULT_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.notEquals=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces not equals to UPDATED_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.notEquals=" + UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsInShouldWork() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces in DEFAULT_PASSENGER_PLACES or UPDATED_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.in=" + DEFAULT_PASSENGER_PLACES + "," + UPDATED_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces equals to UPDATED_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.in=" + UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsNullOrNotNull() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces is not null
        defaultBusShouldBeFound("passengerPlaces.specified=true");

        // Get all the busList where passengerPlaces is null
        defaultBusShouldNotBeFound("passengerPlaces.specified=false");
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces is greater than or equal to DEFAULT_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.greaterThanOrEqual=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces is greater than or equal to UPDATED_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.greaterThanOrEqual=" + UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces is less than or equal to DEFAULT_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.lessThanOrEqual=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces is less than or equal to SMALLER_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.lessThanOrEqual=" + SMALLER_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsLessThanSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces is less than DEFAULT_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.lessThan=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces is less than UPDATED_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.lessThan=" + UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void getAllBusesByPassengerPlacesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList where passengerPlaces is greater than DEFAULT_PASSENGER_PLACES
        defaultBusShouldNotBeFound("passengerPlaces.greaterThan=" + DEFAULT_PASSENGER_PLACES);

        // Get all the busList where passengerPlaces is greater than SMALLER_PASSENGER_PLACES
        defaultBusShouldBeFound("passengerPlaces.greaterThan=" + SMALLER_PASSENGER_PLACES);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusShouldBeFound(String filter) throws Exception {
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].passengerPlaces").value(hasItem(DEFAULT_PASSENGER_PLACES)));

        // Check, that the count call also returns 1
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusShouldNotBeFound(String filter) throws Exception {
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        Bus updatedBus = busRepository.findById(bus.getId()).get();
        // Disconnect from session so that the updates on updatedBus are not directly saved in db
        em.detach(updatedBus);
        updatedBus.model(UPDATED_MODEL).number(UPDATED_NUMBER).description(UPDATED_DESCRIPTION).passengerPlaces(UPDATED_PASSENGER_PLACES);
        BusDTO busDTO = busMapper.toDto(updatedBus);

        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, busDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBus.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBus.getPassengerPlaces()).isEqualTo(UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void putNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, busDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusWithPatch() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus using partial update
        Bus partialUpdatedBus = new Bus();
        partialUpdatedBus.setId(bus.getId());

        partialUpdatedBus.number(UPDATED_NUMBER).description(UPDATED_DESCRIPTION);

        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBus))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBus.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBus.getPassengerPlaces()).isEqualTo(DEFAULT_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void fullUpdateBusWithPatch() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus using partial update
        Bus partialUpdatedBus = new Bus();
        partialUpdatedBus.setId(bus.getId());

        partialUpdatedBus
            .model(UPDATED_MODEL)
            .number(UPDATED_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .passengerPlaces(UPDATED_PASSENGER_PLACES);

        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBus))
            )
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBus.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBus.getPassengerPlaces()).isEqualTo(UPDATED_PASSENGER_PLACES);
    }

    @Test
    @Transactional
    void patchNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, busDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();
        bus.setId(count.incrementAndGet());

        // Create the Bus
        BusDTO busDTO = busMapper.toDto(bus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(busDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Delete the bus
        restBusMockMvc
            .perform(delete(ENTITY_API_URL_ID, bus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
