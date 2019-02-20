package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.IngredientAmountService;
import io.github.jhipster.application.domain.IngredientAmount;
import io.github.jhipster.application.repository.IngredientAmountRepository;
import io.github.jhipster.application.repository.search.IngredientAmountSearchRepository;
import io.github.jhipster.application.service.dto.IngredientAmountDTO;
import io.github.jhipster.application.service.mapper.IngredientAmountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IngredientAmount.
 */
@Service
@Transactional
public class IngredientAmountServiceImpl implements IngredientAmountService {

    private final Logger log = LoggerFactory.getLogger(IngredientAmountServiceImpl.class);

    private final IngredientAmountRepository ingredientAmountRepository;

    private final IngredientAmountMapper ingredientAmountMapper;

    private final IngredientAmountSearchRepository ingredientAmountSearchRepository;

    public IngredientAmountServiceImpl(IngredientAmountRepository ingredientAmountRepository, IngredientAmountMapper ingredientAmountMapper, IngredientAmountSearchRepository ingredientAmountSearchRepository) {
        this.ingredientAmountRepository = ingredientAmountRepository;
        this.ingredientAmountMapper = ingredientAmountMapper;
        this.ingredientAmountSearchRepository = ingredientAmountSearchRepository;
    }

    /**
     * Save a ingredientAmount.
     *
     * @param ingredientAmountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredientAmountDTO save(IngredientAmountDTO ingredientAmountDTO) {
        log.debug("Request to save IngredientAmount : {}", ingredientAmountDTO);
        IngredientAmount ingredientAmount = ingredientAmountMapper.toEntity(ingredientAmountDTO);
        ingredientAmount = ingredientAmountRepository.save(ingredientAmount);
        IngredientAmountDTO result = ingredientAmountMapper.toDto(ingredientAmount);
        ingredientAmountSearchRepository.save(ingredientAmount);
        return result;
    }

    /**
     * Get all the ingredientAmounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientAmountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientAmounts");
        return ingredientAmountRepository.findAll(pageable)
            .map(ingredientAmountMapper::toDto);
    }


    /**
     * Get one ingredientAmount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientAmountDTO> findOne(Long id) {
        log.debug("Request to get IngredientAmount : {}", id);
        return ingredientAmountRepository.findById(id)
            .map(ingredientAmountMapper::toDto);
    }

    /**
     * Delete the ingredientAmount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientAmount : {}", id);        ingredientAmountRepository.deleteById(id);
        ingredientAmountSearchRepository.deleteById(id);
    }

    /**
     * Search for the ingredientAmount corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientAmountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IngredientAmounts for query {}", query);
        return ingredientAmountSearchRepository.search(queryStringQuery(query), pageable)
            .map(ingredientAmountMapper::toDto);
    }
}
