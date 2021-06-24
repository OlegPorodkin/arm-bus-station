package ru.porodkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.Bus;
import ru.porodkin.domain.Route;

/**
 * Spring Data SQL repository for the Bus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusRepository extends JpaRepository<Bus, Long>, JpaSpecificationExecutor<Bus> {
    @Override
    @EntityGraph(value = "bus.all")
    List<Bus> findAll();

    @Override
    @EntityGraph(value = "bus.all")
    Optional<Bus> findById(Long var1);
}
