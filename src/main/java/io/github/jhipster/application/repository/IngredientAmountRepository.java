package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.IngredientAmount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IngredientAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Long> {

}
