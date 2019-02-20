package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RecipeeService;
import io.github.jhipster.application.domain.Recipee;
import io.github.jhipster.application.repository.RecipeeRepository;
import io.github.jhipster.application.repository.search.RecipeeSearchRepository;
import io.github.jhipster.application.service.dto.RecipeeDTO;
import io.github.jhipster.application.service.mapper.RecipeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Recipee.
 */
@Service
@Transactional
public class RecipeeServiceImpl implements RecipeeService {

    private final Logger log = LoggerFactory.getLogger(RecipeeServiceImpl.class);

    private final RecipeeRepository recipeeRepository;

    private final RecipeeMapper recipeeMapper;

    private final RecipeeSearchRepository recipeeSearchRepository;

    public RecipeeServiceImpl(RecipeeRepository recipeeRepository, RecipeeMapper recipeeMapper, RecipeeSearchRepository recipeeSearchRepository) {
        this.recipeeRepository = recipeeRepository;
        this.recipeeMapper = recipeeMapper;
        this.recipeeSearchRepository = recipeeSearchRepository;
    }

    /**
     * Save a recipee.
     *
     * @param recipeeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipeeDTO save(RecipeeDTO recipeeDTO) {
        log.debug("Request to save Recipee : {}", recipeeDTO);
        Recipee recipee = recipeeMapper.toEntity(recipeeDTO);
        recipee = recipeeRepository.save(recipee);
        RecipeeDTO result = recipeeMapper.toDto(recipee);
        recipeeSearchRepository.save(recipee);
        return result;
    }

    /**
     * Get all the recipees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipees");
        return recipeeRepository.findAll(pageable)
            .map(recipeeMapper::toDto);
    }

    /**
     * Get all the Recipee with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RecipeeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return recipeeRepository.findAllWithEagerRelationships(pageable).map(recipeeMapper::toDto);
    }
    

    /**
     * Get one recipee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeeDTO> findOne(Long id) {
        log.debug("Request to get Recipee : {}", id);
        return recipeeRepository.findOneWithEagerRelationships(id)
            .map(recipeeMapper::toDto);
    }

    /**
     * Delete the recipee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recipee : {}", id);        recipeeRepository.deleteById(id);
        recipeeSearchRepository.deleteById(id);
    }

    /**
     * Search for the recipee corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recipees for query {}", query);
        return recipeeSearchRepository.search(queryStringQuery(query), pageable)
            .map(recipeeMapper::toDto);
    }
}
