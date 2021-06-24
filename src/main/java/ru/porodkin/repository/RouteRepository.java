package ru.porodkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.Route;

/**
 * Spring Data SQL repository for the Route entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Override
    @EntityGraph(value = "route.all")
    List<Route> findAll();

    @Override
    @EntityGraph(value = "route.all")
    Optional<Route> findById(Long var1);
}
