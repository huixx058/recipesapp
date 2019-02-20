package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Recipee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Recipee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeeRepository extends JpaRepository<Recipee, Long> {

    @Query(value = "select distinct recipee from Recipee recipee left join fetch recipee.ingredients",
        countQuery = "select count(distinct recipee) from Recipee recipee")
    Page<Recipee> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct recipee from Recipee recipee left join fetch recipee.ingredients")
    List<Recipee> findAllWithEagerRelationships();

    @Query("select recipee from Recipee recipee left join fetch recipee.ingredients where recipee.id =:id")
    Optional<Recipee> findOneWithEagerRelationships(@Param("id") Long id);

}
