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
import ru.porodkin.domain.Passport;
import ru.porodkin.repository.PassportRepository;
import ru.porodkin.service.dto.PassportDTO;
import ru.porodkin.service.mapper.PassportMapper;

/**
 * Integration tests for the {@link PassportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassportResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERIAL = 1;
    private static final Integer UPDATED_SERIAL = 2;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final LocalDate DEFAULT_DATE_OF_ISSUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ISSUE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_WHO_ISSUED = "AAAAAAAAAA";
    private static final String UPDATED_WHO_ISSUED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/passports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private PassportMapper passportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassportMockMvc;

    private Passport passport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passport createEntity(EntityManager em) {
        Passport passport = new Passport()
            .uuid(DEFAULT_UUID)
            .serial(DEFAULT_SERIAL)
            .number(DEFAULT_NUMBER)
            .dateOfIssue(DEFAULT_DATE_OF_ISSUE)
            .whoIssued(DEFAULT_WHO_ISSUED);
        return passport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passport createUpdatedEntity(EntityManager em) {
        Passport passport = new Passport()
            .uuid(UPDATED_UUID)
            .serial(UPDATED_SERIAL)
            .number(UPDATED_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .whoIssued(UPDATED_WHO_ISSUED);
        return passport;
    }

    @BeforeEach
    public void initTest() {
        passport = createEntity(em);
    }

    @Test
    @Transactional
    void createPassport() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();
        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);
        restPassportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate + 1);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testPassport.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testPassport.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPassport.getDateOfIssue()).isEqualTo(DEFAULT_DATE_OF_ISSUE);
        assertThat(testPassport.getWhoIssued()).isEqualTo(DEFAULT_WHO_ISSUED);
    }

    @Test
    @Transactional
    void createPassportWithExistingId() throws Exception {
        // Create the Passport with an existing ID
        passport.setId(1L);
        PassportDTO passportDTO = passportMapper.toDto(passport);

        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPassports() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList
        restPassportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passport.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateOfIssue").value(hasItem(DEFAULT_DATE_OF_ISSUE.toString())))
            .andExpect(jsonPath("$.[*].whoIssued").value(hasItem(DEFAULT_WHO_ISSUED)));
    }

    @Test
    @Transactional
    void getPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get the passport
        restPassportMockMvc
            .perform(get(ENTITY_API_URL_ID, passport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passport.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.dateOfIssue").value(DEFAULT_DATE_OF_ISSUE.toString()))
            .andExpect(jsonPath("$.whoIssued").value(DEFAULT_WHO_ISSUED));
    }

    @Test
    @Transactional
    void getNonExistingPassport() throws Exception {
        // Get the passport
        restPassportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport
        Passport updatedPassport = passportRepository.findById(passport.getId()).get();
        // Disconnect from session so that the updates on updatedPassport are not directly saved in db
        em.detach(updatedPassport);
        updatedPassport
            .uuid(UPDATED_UUID)
            .serial(UPDATED_SERIAL)
            .number(UPDATED_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .whoIssued(UPDATED_WHO_ISSUED);
        PassportDTO passportDTO = passportMapper.toDto(updatedPassport);

        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testPassport.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testPassport.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPassport.getDateOfIssue()).isEqualTo(UPDATED_DATE_OF_ISSUE);
        assertThat(testPassport.getWhoIssued()).isEqualTo(UPDATED_WHO_ISSUED);
    }

    @Test
    @Transactional
    void putNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassportWithPatch() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport using partial update
        Passport partialUpdatedPassport = new Passport();
        partialUpdatedPassport.setId(passport.getId());

        partialUpdatedPassport
            .uuid(UPDATED_UUID)
            .serial(UPDATED_SERIAL)
            .number(UPDATED_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .whoIssued(UPDATED_WHO_ISSUED);

        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassport))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testPassport.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testPassport.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPassport.getDateOfIssue()).isEqualTo(UPDATED_DATE_OF_ISSUE);
        assertThat(testPassport.getWhoIssued()).isEqualTo(UPDATED_WHO_ISSUED);
    }

    @Test
    @Transactional
    void fullUpdatePassportWithPatch() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport using partial update
        Passport partialUpdatedPassport = new Passport();
        partialUpdatedPassport.setId(passport.getId());

        partialUpdatedPassport
            .uuid(UPDATED_UUID)
            .serial(UPDATED_SERIAL)
            .number(UPDATED_NUMBER)
            .dateOfIssue(UPDATED_DATE_OF_ISSUE)
            .whoIssued(UPDATED_WHO_ISSUED);

        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPassport))
            )
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testPassport.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testPassport.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPassport.getDateOfIssue()).isEqualTo(UPDATED_DATE_OF_ISSUE);
        assertThat(testPassport.getWhoIssued()).isEqualTo(UPDATED_WHO_ISSUED);
    }

    @Test
    @Transactional
    void patchNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();
        passport.setId(count.incrementAndGet());

        // Create the Passport
        PassportDTO passportDTO = passportMapper.toDto(passport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(passportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        int databaseSizeBeforeDelete = passportRepository.findAll().size();

        // Delete the passport
        restPassportMockMvc
            .perform(delete(ENTITY_API_URL_ID, passport.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
