package ru.porodkin.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.Passport;

/**
 * Spring Data SQL repository for the Passport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {}
