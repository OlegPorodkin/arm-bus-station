package ru.porodkin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.porodkin.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
import ru.porodkin.domain.Route;
import ru.porodkin.repository.RouteRepository;
import ru.porodkin.service.dto.RouteDTO;
import ru.porodkin.service.mapper.RouteMapper;

/**
 * Integration tests for the {@link RouteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RouteResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PLANNED_ARRIVAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_ARRIVAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_DEPARTURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_DEPARTURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_ARRIVAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_ARRIVAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_DEPARTURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_DEPARTURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Instant DEFAULT_TIME_REGISTRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_REGISTRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PLATFORM = "AAAAAAAAAA";
    private static final String UPDATED_PLATFORM = "BBBBBBBBBB";

    private static final String DEFAULT_ROUT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ROUT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/routes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRouteMockMvc;

    private Route route;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Route createEntity(EntityManager em) {
        Route route = new Route()
            .uuid(DEFAULT_UUID)
            .plannedArrival(DEFAULT_PLANNED_ARRIVAL)
            .plannedDeparture(DEFAULT_PLANNED_DEPARTURE)
            .actualArrival(DEFAULT_ACTUAL_ARRIVAL)
            .actualDeparture(DEFAULT_ACTUAL_DEPARTURE)
            .timeRegistration(DEFAULT_TIME_REGISTRATION)
            .platform(DEFAULT_PLATFORM)
            .routStatus(DEFAULT_ROUT_STATUS)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Bus bus;
        if (TestUtil.findAll(em, Bus.class).isEmpty()) {
            bus = BusResourceIT.createEntity(em);
            em.persist(bus);
            em.flush();
        } else {
            bus = TestUtil.findAll(em, Bus.class).get(0);
        }
        route.setBus(bus);
        return route;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Route createUpdatedEntity(EntityManager em) {
        Route route = new Route()
            .uuid(UPDATED_UUID)
            .plannedArrival(UPDATED_PLANNED_ARRIVAL)
            .plannedDeparture(UPDATED_PLANNED_DEPARTURE)
            .actualArrival(UPDATED_ACTUAL_ARRIVAL)
            .actualDeparture(UPDATED_ACTUAL_DEPARTURE)
            .timeRegistration(UPDATED_TIME_REGISTRATION)
            .platform(UPDATED_PLATFORM)
            .routStatus(UPDATED_ROUT_STATUS)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Bus bus;
        if (TestUtil.findAll(em, Bus.class).isEmpty()) {
            bus = BusResourceIT.createUpdatedEntity(em);
            em.persist(bus);
            em.flush();
        } else {
            bus = TestUtil.findAll(em, Bus.class).get(0);
        }
        route.setBus(bus);
        return route;
    }

    @BeforeEach
    public void initTest() {
        route = createEntity(em);
    }

    @Test
    @Transactional
    void createRoute() throws Exception {
        int databaseSizeBeforeCreate = routeRepository.findAll().size();
        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);
        restRouteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate + 1);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testRoute.getPlannedArrival()).isEqualTo(DEFAULT_PLANNED_ARRIVAL);
        assertThat(testRoute.getPlannedDeparture()).isEqualTo(DEFAULT_PLANNED_DEPARTURE);
        assertThat(testRoute.getActualArrival()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL);
        assertThat(testRoute.getActualDeparture()).isEqualTo(DEFAULT_ACTUAL_DEPARTURE);
        assertThat(testRoute.getTimeRegistration()).isEqualTo(DEFAULT_TIME_REGISTRATION);
        assertThat(testRoute.getPlatform()).isEqualTo(DEFAULT_PLATFORM);
        assertThat(testRoute.getRoutStatus()).isEqualTo(DEFAULT_ROUT_STATUS);
        assertThat(testRoute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the id for MapsId, the ids must be same
        assertThat(testRoute.getId()).isEqualTo(testRoute.getBus().getId());
    }

    @Test
    @Transactional
    void createRouteWithExistingId() throws Exception {
        // Create the Route with an existing ID
        route.setId(1L);
        RouteDTO routeDTO = routeMapper.toDto(route);

        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRouteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateRouteMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);
        int databaseSizeBeforeCreate = routeRepository.findAll().size();

        // Add a new parent entity
        Bus bus = BusResourceIT.createUpdatedEntity(em);
        em.persist(bus);
        em.flush();

        // Load the route
        Route updatedRoute = routeRepository.findById(route.getId()).get();
        assertThat(updatedRoute).isNotNull();
        // Disconnect from session so that the updates on updatedRoute are not directly saved in db
        em.detach(updatedRoute);

        // Update the Bus with new association value
        updatedRoute.setBus(bus);
        RouteDTO updatedRouteDTO = routeMapper.toDto(updatedRoute);
        assertThat(updatedRouteDTO).isNotNull();

        // Update the entity
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRouteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRouteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeCreate);
        Route testRoute = routeList.get(routeList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testRoute.getId()).isEqualTo(testRoute.getBus().getId());
    }

    @Test
    @Transactional
    void getAllRoutes() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get all the routeList
        restRouteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(route.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].plannedArrival").value(hasItem(sameInstant(DEFAULT_PLANNED_ARRIVAL))))
            .andExpect(jsonPath("$.[*].plannedDeparture").value(hasItem(sameInstant(DEFAULT_PLANNED_DEPARTURE))))
            .andExpect(jsonPath("$.[*].actualArrival").value(hasItem(sameInstant(DEFAULT_ACTUAL_ARRIVAL))))
            .andExpect(jsonPath("$.[*].actualDeparture").value(hasItem(sameInstant(DEFAULT_ACTUAL_DEPARTURE))))
            .andExpect(jsonPath("$.[*].timeRegistration").value(hasItem(DEFAULT_TIME_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].platform").value(hasItem(DEFAULT_PLATFORM)))
            .andExpect(jsonPath("$.[*].routStatus").value(hasItem(DEFAULT_ROUT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        // Get the route
        restRouteMockMvc
            .perform(get(ENTITY_API_URL_ID, route.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(route.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.plannedArrival").value(sameInstant(DEFAULT_PLANNED_ARRIVAL)))
            .andExpect(jsonPath("$.plannedDeparture").value(sameInstant(DEFAULT_PLANNED_DEPARTURE)))
            .andExpect(jsonPath("$.actualArrival").value(sameInstant(DEFAULT_ACTUAL_ARRIVAL)))
            .andExpect(jsonPath("$.actualDeparture").value(sameInstant(DEFAULT_ACTUAL_DEPARTURE)))
            .andExpect(jsonPath("$.timeRegistration").value(DEFAULT_TIME_REGISTRATION.toString()))
            .andExpect(jsonPath("$.platform").value(DEFAULT_PLATFORM))
            .andExpect(jsonPath("$.routStatus").value(DEFAULT_ROUT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRoute() throws Exception {
        // Get the route
        restRouteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route
        Route updatedRoute = routeRepository.findById(route.getId()).get();
        // Disconnect from session so that the updates on updatedRoute are not directly saved in db
        em.detach(updatedRoute);
        updatedRoute
            .uuid(UPDATED_UUID)
            .plannedArrival(UPDATED_PLANNED_ARRIVAL)
            .plannedDeparture(UPDATED_PLANNED_DEPARTURE)
            .actualArrival(UPDATED_ACTUAL_ARRIVAL)
            .actualDeparture(UPDATED_ACTUAL_DEPARTURE)
            .timeRegistration(UPDATED_TIME_REGISTRATION)
            .platform(UPDATED_PLATFORM)
            .routStatus(UPDATED_ROUT_STATUS)
            .description(UPDATED_DESCRIPTION);
        RouteDTO routeDTO = routeMapper.toDto(updatedRoute);

        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, routeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testRoute.getPlannedArrival()).isEqualTo(UPDATED_PLANNED_ARRIVAL);
        assertThat(testRoute.getPlannedDeparture()).isEqualTo(UPDATED_PLANNED_DEPARTURE);
        assertThat(testRoute.getActualArrival()).isEqualTo(UPDATED_ACTUAL_ARRIVAL);
        assertThat(testRoute.getActualDeparture()).isEqualTo(UPDATED_ACTUAL_DEPARTURE);
        assertThat(testRoute.getTimeRegistration()).isEqualTo(UPDATED_TIME_REGISTRATION);
        assertThat(testRoute.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testRoute.getRoutStatus()).isEqualTo(UPDATED_ROUT_STATUS);
        assertThat(testRoute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, routeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRouteWithPatch() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route using partial update
        Route partialUpdatedRoute = new Route();
        partialUpdatedRoute.setId(route.getId());

        partialUpdatedRoute
            .actualDeparture(UPDATED_ACTUAL_DEPARTURE)
            .timeRegistration(UPDATED_TIME_REGISTRATION)
            .platform(UPDATED_PLATFORM)
            .routStatus(UPDATED_ROUT_STATUS);

        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoute.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoute))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testRoute.getPlannedArrival()).isEqualTo(DEFAULT_PLANNED_ARRIVAL);
        assertThat(testRoute.getPlannedDeparture()).isEqualTo(DEFAULT_PLANNED_DEPARTURE);
        assertThat(testRoute.getActualArrival()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL);
        assertThat(testRoute.getActualDeparture()).isEqualTo(UPDATED_ACTUAL_DEPARTURE);
        assertThat(testRoute.getTimeRegistration()).isEqualTo(UPDATED_TIME_REGISTRATION);
        assertThat(testRoute.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testRoute.getRoutStatus()).isEqualTo(UPDATED_ROUT_STATUS);
        assertThat(testRoute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRouteWithPatch() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeUpdate = routeRepository.findAll().size();

        // Update the route using partial update
        Route partialUpdatedRoute = new Route();
        partialUpdatedRoute.setId(route.getId());

        partialUpdatedRoute
            .uuid(UPDATED_UUID)
            .plannedArrival(UPDATED_PLANNED_ARRIVAL)
            .plannedDeparture(UPDATED_PLANNED_DEPARTURE)
            .actualArrival(UPDATED_ACTUAL_ARRIVAL)
            .actualDeparture(UPDATED_ACTUAL_DEPARTURE)
            .timeRegistration(UPDATED_TIME_REGISTRATION)
            .platform(UPDATED_PLATFORM)
            .routStatus(UPDATED_ROUT_STATUS)
            .description(UPDATED_DESCRIPTION);

        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoute.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoute))
            )
            .andExpect(status().isOk());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
        Route testRoute = routeList.get(routeList.size() - 1);
        assertThat(testRoute.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testRoute.getPlannedArrival()).isEqualTo(UPDATED_PLANNED_ARRIVAL);
        assertThat(testRoute.getPlannedDeparture()).isEqualTo(UPDATED_PLANNED_DEPARTURE);
        assertThat(testRoute.getActualArrival()).isEqualTo(UPDATED_ACTUAL_ARRIVAL);
        assertThat(testRoute.getActualDeparture()).isEqualTo(UPDATED_ACTUAL_DEPARTURE);
        assertThat(testRoute.getTimeRegistration()).isEqualTo(UPDATED_TIME_REGISTRATION);
        assertThat(testRoute.getPlatform()).isEqualTo(UPDATED_PLATFORM);
        assertThat(testRoute.getRoutStatus()).isEqualTo(UPDATED_ROUT_STATUS);
        assertThat(testRoute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, routeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoute() throws Exception {
        int databaseSizeBeforeUpdate = routeRepository.findAll().size();
        route.setId(count.incrementAndGet());

        // Create the Route
        RouteDTO routeDTO = routeMapper.toDto(route);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRouteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(routeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Route in the database
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoute() throws Exception {
        // Initialize the database
        routeRepository.saveAndFlush(route);

        int databaseSizeBeforeDelete = routeRepository.findAll().size();

        // Delete the route
        restRouteMockMvc
            .perform(delete(ENTITY_API_URL_ID, route.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Route> routeList = routeRepository.findAll();
        assertThat(routeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
