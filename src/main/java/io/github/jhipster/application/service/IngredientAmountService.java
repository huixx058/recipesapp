package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.IngredientAmountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing IngredientAmount.
 */
public interface IngredientAmountService {

    /**
     * Save a ingredientAmount.
     *
     * @param ingredientAmountDTO the entity to save
     * @return the persisted entity
     */
    IngredientAmountDTO save(IngredientAmountDTO ingredientAmountDTO);

    /**
     * Get all the ingredientAmounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IngredientAmountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ingredientAmount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IngredientAmountDTO> findOne(Long id);

    /**
     * Delete the "id" ingredientAmount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ingredientAmount corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IngredientAmountDTO> search(String query, Pageable pageable);
}
