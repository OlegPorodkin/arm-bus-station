package ru.porodkin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.porodkin.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
