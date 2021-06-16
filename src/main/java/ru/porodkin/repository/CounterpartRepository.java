package ru.porodkin.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.Counterpart;

/**
 * Spring Data SQL repository for the Counterpart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CounterpartRepository extends JpaRepository<Counterpart, Long> {}
