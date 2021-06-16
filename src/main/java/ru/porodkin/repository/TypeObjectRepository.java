package ru.porodkin.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.porodkin.domain.TypeObject;

/**
 * Spring Data SQL repository for the TypeObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeObjectRepository extends JpaRepository<TypeObject, Long> {}
