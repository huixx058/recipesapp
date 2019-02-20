package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.MealTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MealType.
 */
public interface MealTypeService {

    /**
     * Save a mealType.
     *
     * @param mealTypeDTO the entity to save
     * @return the persisted entity
     */
    MealTypeDTO save(MealTypeDTO mealTypeDTO);

    /**
     * Get all the mealTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MealTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mealType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MealTypeDTO> findOne(Long id);

    /**
     * Delete the "id" mealType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mealType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MealTypeDTO> search(String query, Pageable pageable);
}
