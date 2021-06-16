package ru.porodkin.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.porodkin.domain.*; // for static metamodels
import ru.porodkin.domain.Bus;
import ru.porodkin.repository.BusRepository;
import ru.porodkin.service.criteria.BusCriteria;
import ru.porodkin.service.dto.BusDTO;
import ru.porodkin.service.mapper.BusMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Bus} entities in the database.
 * The main input is a {@link BusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusDTO} or a {@link Page} of {@link BusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusQueryService extends QueryService<Bus> {

    private final Logger log = LoggerFactory.getLogger(BusQueryService.class);

    private final BusRepository busRepository;

    private final BusMapper busMapper;

    public BusQueryService(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    /**
     * Return a {@link List} of {@link BusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusDTO> findByCriteria(BusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bus> specification = createSpecification(criteria);
        return busMapper.toDto(busRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusDTO> findByCriteria(BusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bus> specification = createSpecification(criteria);
        return busRepository.findAll(specification, page).map(busMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bus> specification = createSpecification(criteria);
        return busRepository.count(specification);
    }

    /**
     * Function to convert {@link BusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bus> createSpecification(BusCriteria criteria) {
        Specification<Bus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bus_.id));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Bus_.model));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), Bus_.number));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Bus_.description));
            }
            if (criteria.getPassengerPlaces() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassengerPlaces(), Bus_.passengerPlaces));
            }
            if (criteria.getDriverId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDriverId(), root -> root.join(Bus_.driver, JoinType.LEFT).get(Driver_.id))
                    );
            }
            if (criteria.getCounterpartId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCounterpartId(),
                            root -> root.join(Bus_.counterpart, JoinType.LEFT).get(Counterpart_.id)
                        )
                    );
            }
            if (criteria.getRouteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRouteId(), root -> root.join(Bus_.route, JoinType.LEFT).get(Route_.id))
                    );
            }
        }
        return specification;
    }
}
