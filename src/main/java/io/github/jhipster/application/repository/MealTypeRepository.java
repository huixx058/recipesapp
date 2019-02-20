package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.MealType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MealType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MealTypeRepository extends JpaRepository<MealType, Long> {

}
