package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.MealTypeService;
import io.github.jhipster.application.domain.MealType;
import io.github.jhipster.application.repository.MealTypeRepository;
import io.github.jhipster.application.repository.search.MealTypeSearchRepository;
import io.github.jhipster.application.service.dto.MealTypeDTO;
import io.github.jhipster.application.service.mapper.MealTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MealType.
 */
@Service
@Transactional
public class MealTypeServiceImpl implements MealTypeService {

    private final Logger log = LoggerFactory.getLogger(MealTypeServiceImpl.class);

    private final MealTypeRepository mealTypeRepository;

    private final MealTypeMapper mealTypeMapper;

    private final MealTypeSearchRepository mealTypeSearchRepository;

    public MealTypeServiceImpl(MealTypeRepository mealTypeRepository, MealTypeMapper mealTypeMapper, MealTypeSearchRepository mealTypeSearchRepository) {
        this.mealTypeRepository = mealTypeRepository;
        this.mealTypeMapper = mealTypeMapper;
        this.mealTypeSearchRepository = mealTypeSearchRepository;
    }

    /**
     * Save a mealType.
     *
     * @param mealTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MealTypeDTO save(MealTypeDTO mealTypeDTO) {
        log.debug("Request to save MealType : {}", mealTypeDTO);
        MealType mealType = mealTypeMapper.toEntity(mealTypeDTO);
        mealType = mealTypeRepository.save(mealType);
        MealTypeDTO result = mealTypeMapper.toDto(mealType);
        mealTypeSearchRepository.save(mealType);
        return result;
    }

    /**
     * Get all the mealTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MealTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MealTypes");
        return mealTypeRepository.findAll(pageable)
            .map(mealTypeMapper::toDto);
    }


    /**
     * Get one mealType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MealTypeDTO> findOne(Long id) {
        log.debug("Request to get MealType : {}", id);
        return mealTypeRepository.findById(id)
            .map(mealTypeMapper::toDto);
    }

    /**
     * Delete the mealType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MealType : {}", id);        mealTypeRepository.deleteById(id);
        mealTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the mealType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MealTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MealTypes for query {}", query);
        return mealTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(mealTypeMapper::toDto);
    }
}
