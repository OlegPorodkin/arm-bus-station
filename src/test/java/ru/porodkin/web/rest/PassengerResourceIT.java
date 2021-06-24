package ru.porodkin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import ru.porodkin.domain.Passenger;
import ru.porodkin.repository.PassengerRepository;
import ru.porodkin.service.dto.PassengerDTO;
import ru.porodkin.service.mapper.PassengerMapper;

/**
 * Integration tests for the {@link PassengerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassengerResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRONYMIC = "AAAAAAAAAA";
    private static final String UPDATED_PATRONYMIC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BOARDING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BOARDING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CITIZENSHIP = "AAAAAAAAAA";
    private static final String UPDATED_CITIZENSHIP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/passengers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassengerMockMvc;

    private Passenger passenger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passenger createEntity(EntityManager em) {
        Passenger passenger = new Passenger()
            .uuid(DEFAULT_UUID)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .patronymic(DEFAULT_PATRONYMIC)
            .birthday(DEFAULT_BIRTHDAY)
            .boardingStatus(DEFAULT_BOARDING_STATUS)
            .sex(DEFAULT_SEX)
            .phone(DEFAULT_PHONE)
            .citizenship(DEFAULT_CITIZENSHIP);
        return passenger;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passenger createUpdatedEntity(EntityManager em) {
        Passenger passenger = new Passenger()
            .uuid(UPDATED_UUID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .birthday(UPDATED_BIRTHDAY)
            .boardingStatus(UPDATED_BOARDING_STATUS)
            .sex(UPDATED_SEX)
            .phone(UPDATED_PHONE)
            .citizenship(UPDATED_CITIZENSHIP);
        return passenger;
    }

    @BeforeEach
    public void initTest() {
        passenger = createEntity(em);
    }

    @Test
    @Transactional
    void createPassenger() throws Exception {
        int databaseSizeBeforeCreate = passengerRepository.findAll().size();
        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);
        restPassengerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeCreate + 1);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testPassenger.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPassenger.getPatronymic()).isEqualTo(DEFAULT_PATRONYMIC);
        assertThat(testPassenger.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testPassenger.getBoardingStatus()).isEqualTo(DEFAULT_BOARDING_STATUS);
        assertThat(testPassenger.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testPassenger.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPassenger.getCitizenship()).isEqualTo(DEFAULT_CITIZENSHIP);
    }

    @Test
    @Transactional
    void createPassengerWithExistingId() throws Exception {
        // Create the Passenger with an existing ID
        passenger.setId(1L);
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        int databaseSizeBeforeCreate = passengerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassengerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPassengers() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get all the passengerList
        restPassengerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passenger.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].patronymic").value(hasItem(DEFAULT_PATRONYMIC)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].boardingStatus").value(hasItem(DEFAULT_BOARDING_STATUS)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].citizenship").value(hasItem(DEFAULT_CITIZENSHIP)));
    }

    @Test
    @Transactional
    void getPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get the passenger
        restPassengerMockMvc
            .perform(get(ENTITY_API_URL_ID, passenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passenger.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.patronymic").value(DEFAULT_PATRONYMIC))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.boardingStatus").value(DEFAULT_BOARDING_STATUS))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.citizenship").value(DEFAULT_CITIZENSHIP));
    }

    @Test
    @Transactional
    void getNonExistingPassenger() throws Exception {
        // Get the passenger
        restPassengerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Update the passenger
        Passenger updatedPassenger = passengerRepository.findById(passenger.getId()).get();
        // Disconnect from session so that the updates on updatedPassenger are not directly saved in db
        em.detach(updatedPassenger);
        updatedPassenger
            .uuid(UPDATED_UUID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .birthday(UPDATED_BIRTHDAY)
            .boardingStatus(UPDATED_BOARDING_STATUS)
            .sex(UPDATED_SEX)
            .phone(UPDATED_PHONE)
            .citizenship(UPDATED_CITIZENSHIP);
        PassengerDTO passengerDTO = passengerMapper.toDto(updatedPassenger);

        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passengerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testPassenger.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPassenger.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testPassenger.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPassenger.getBoardingStatus()).isEqualTo(UPDATED_BOARDING_STATUS);
        assertThat(testPassenger.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPassenger.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPassenger.getCitizenship()).isEqualTo(UPDATED_CITIZENSHIP);
    }

    @Test
    @Transactional
    void putNonExistingPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passengerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassengerWithPatch() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Update the passenger using partial update
        Passenger partialUpdatedPassenger = new Passenger();
        partialUpdatedPassenger.setId(passenger.getId());

        partialUpdatedPassenger
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .birthday(UPDATED_BIRTHDAY)
            .boardingStatus(UPDATED_BOARDING_STATUS)
            .sex(UPDATED_SEX);

        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassenger.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassenger))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testPassenger.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPassenger.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testPassenger.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPassenger.getBoardingStatus()).isEqualTo(UPDATED_BOARDING_STATUS);
        assertThat(testPassenger.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPassenger.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPassenger.getCitizenship()).isEqualTo(DEFAULT_CITIZENSHIP);
    }

    @Test
    @Transactional
    void fullUpdatePassengerWithPatch() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();

        // Update the passenger using partial update
        Passenger partialUpdatedPassenger = new Passenger();
        partialUpdatedPassenger.setId(passenger.getId());

        partialUpdatedPassenger
            .uuid(UPDATED_UUID)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .patronymic(UPDATED_PATRONYMIC)
            .birthday(UPDATED_BIRTHDAY)
            .boardingStatus(UPDATED_BOARDING_STATUS)
            .sex(UPDATED_SEX)
            .phone(UPDATED_PHONE)
            .citizenship(UPDATED_CITIZENSHIP);

        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassenger.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassenger))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
        Passenger testPassenger = passengerList.get(passengerList.size() - 1);
        assertThat(testPassenger.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testPassenger.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPassenger.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPassenger.getPatronymic()).isEqualTo(UPDATED_PATRONYMIC);
        assertThat(testPassenger.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testPassenger.getBoardingStatus()).isEqualTo(UPDATED_BOARDING_STATUS);
        assertThat(testPassenger.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPassenger.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPassenger.getCitizenship()).isEqualTo(UPDATED_CITIZENSHIP);
    }

    @Test
    @Transactional
    void patchNonExistingPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passengerDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassenger() throws Exception {
        int databaseSizeBeforeUpdate = passengerRepository.findAll().size();
        passenger.setId(count.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passengerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passenger in the database
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        int databaseSizeBeforeDelete = passengerRepository.findAll().size();

        // Delete the passenger
        restPassengerMockMvc
            .perform(delete(ENTITY_API_URL_ID, passenger.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passenger> passengerList = passengerRepository.findAll();
        assertThat(passengerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
