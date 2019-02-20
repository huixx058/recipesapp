package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.IngredientService;
import io.github.jhipster.application.domain.Ingredient;
import io.github.jhipster.application.repository.IngredientRepository;
import io.github.jhipster.application.repository.search.IngredientSearchRepository;
import io.github.jhipster.application.service.dto.IngredientDTO;
import io.github.jhipster.application.service.mapper.IngredientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ingredient.
 */
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    private final IngredientSearchRepository ingredientSearchRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, IngredientSearchRepository ingredientSearchRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientSearchRepository = ingredientSearchRepository;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        log.debug("Request to save Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        IngredientDTO result = ingredientMapper.toDto(ingredient);
        ingredientSearchRepository.save(ingredient);
        return result;
    }

    /**
     * Get all the ingredients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll(pageable)
            .map(ingredientMapper::toDto);
    }


    /**
     * Get one ingredient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientDTO> findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id)
            .map(ingredientMapper::toDto);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);        ingredientRepository.deleteById(id);
        ingredientSearchRepository.deleteById(id);
    }

    /**
     * Search for the ingredient corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ingredients for query {}", query);
        return ingredientSearchRepository.search(queryStringQuery(query), pageable)
            .map(ingredientMapper::toDto);
    }
}
