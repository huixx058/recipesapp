package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.MealTypeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.MealTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MealType.
 */
@RestController
@RequestMapping("/api")
public class MealTypeResource {

    private final Logger log = LoggerFactory.getLogger(MealTypeResource.class);

    private static final String ENTITY_NAME = "mealType";

    private final MealTypeService mealTypeService;

    public MealTypeResource(MealTypeService mealTypeService) {
        this.mealTypeService = mealTypeService;
    }

    /**
     * POST  /meal-types : Create a new mealType.
     *
     * @param mealTypeDTO the mealTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mealTypeDTO, or with status 400 (Bad Request) if the mealType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meal-types")
    public ResponseEntity<MealTypeDTO> createMealType(@Valid @RequestBody MealTypeDTO mealTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MealType : {}", mealTypeDTO);
        if (mealTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mealType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealTypeDTO result = mealTypeService.save(mealTypeDTO);
        return ResponseEntity.created(new URI("/api/meal-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meal-types : Updates an existing mealType.
     *
     * @param mealTypeDTO the mealTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mealTypeDTO,
     * or with status 400 (Bad Request) if the mealTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the mealTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meal-types")
    public ResponseEntity<MealTypeDTO> updateMealType(@Valid @RequestBody MealTypeDTO mealTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MealType : {}", mealTypeDTO);
        if (mealTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MealTypeDTO result = mealTypeService.save(mealTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mealTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meal-types : get all the mealTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mealTypes in body
     */
    @GetMapping("/meal-types")
    public ResponseEntity<List<MealTypeDTO>> getAllMealTypes(Pageable pageable) {
        log.debug("REST request to get a page of MealTypes");
        Page<MealTypeDTO> page = mealTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meal-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /meal-types/:id : get the "id" mealType.
     *
     * @param id the id of the mealTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mealTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/meal-types/{id}")
    public ResponseEntity<MealTypeDTO> getMealType(@PathVariable Long id) {
        log.debug("REST request to get MealType : {}", id);
        Optional<MealTypeDTO> mealTypeDTO = mealTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealTypeDTO);
    }

    /**
     * DELETE  /meal-types/:id : delete the "id" mealType.
     *
     * @param id the id of the mealTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meal-types/{id}")
    public ResponseEntity<Void> deleteMealType(@PathVariable Long id) {
        log.debug("REST request to delete MealType : {}", id);
        mealTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/meal-types?query=:query : search for the mealType corresponding
     * to the query.
     *
     * @param query the query of the mealType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/meal-types")
    public ResponseEntity<List<MealTypeDTO>> searchMealTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MealTypes for query {}", query);
        Page<MealTypeDTO> page = mealTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/meal-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
