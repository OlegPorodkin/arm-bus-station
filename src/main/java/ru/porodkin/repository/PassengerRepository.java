package ru.porodkin.repository;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.Passenger;

/**
 * Spring Data SQL repository for the Passenger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {
    @Override
    @EntityGraph(value = "passenger.all")
    List<Passenger> findAll(Specification<Passenger> specification);
}
