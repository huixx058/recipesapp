package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.RecipeeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Recipee.
 */
public interface RecipeeService {

    /**
     * Save a recipee.
     *
     * @param recipeeDTO the entity to save
     * @return the persisted entity
     */
    RecipeeDTO save(RecipeeDTO recipeeDTO);

    /**
     * Get all the recipees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecipeeDTO> findAll(Pageable pageable);

    /**
     * Get all the Recipee with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<RecipeeDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" recipee.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecipeeDTO> findOne(Long id);

    /**
     * Delete the "id" recipee.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the recipee corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecipeeDTO> search(String query, Pageable pageable);
}
