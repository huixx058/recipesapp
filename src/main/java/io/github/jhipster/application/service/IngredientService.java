package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.IngredientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ingredient.
 */
public interface IngredientService {

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save
     * @return the persisted entity
     */
    IngredientDTO save(IngredientDTO ingredientDTO);

    /**
     * Get all the ingredients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IngredientDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ingredient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IngredientDTO> findOne(Long id);

    /**
     * Delete the "id" ingredient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ingredient corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IngredientDTO> search(String query, Pageable pageable);
}
