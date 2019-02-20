package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.IngredientAmountService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.IngredientAmountDTO;
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
 * REST controller for managing IngredientAmount.
 */
@RestController
@RequestMapping("/api")
public class IngredientAmountResource {

    private final Logger log = LoggerFactory.getLogger(IngredientAmountResource.class);

    private static final String ENTITY_NAME = "ingredientAmount";

    private final IngredientAmountService ingredientAmountService;

    public IngredientAmountResource(IngredientAmountService ingredientAmountService) {
        this.ingredientAmountService = ingredientAmountService;
    }

    /**
     * POST  /ingredient-amounts : Create a new ingredientAmount.
     *
     * @param ingredientAmountDTO the ingredientAmountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientAmountDTO, or with status 400 (Bad Request) if the ingredientAmount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-amounts")
    public ResponseEntity<IngredientAmountDTO> createIngredientAmount(@Valid @RequestBody IngredientAmountDTO ingredientAmountDTO) throws URISyntaxException {
        log.debug("REST request to save IngredientAmount : {}", ingredientAmountDTO);
        if (ingredientAmountDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredientAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientAmountDTO result = ingredientAmountService.save(ingredientAmountDTO);
        return ResponseEntity.created(new URI("/api/ingredient-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-amounts : Updates an existing ingredientAmount.
     *
     * @param ingredientAmountDTO the ingredientAmountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientAmountDTO,
     * or with status 400 (Bad Request) if the ingredientAmountDTO is not valid,
     * or with status 500 (Internal Server Error) if the ingredientAmountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-amounts")
    public ResponseEntity<IngredientAmountDTO> updateIngredientAmount(@Valid @RequestBody IngredientAmountDTO ingredientAmountDTO) throws URISyntaxException {
        log.debug("REST request to update IngredientAmount : {}", ingredientAmountDTO);
        if (ingredientAmountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientAmountDTO result = ingredientAmountService.save(ingredientAmountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientAmountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-amounts : get all the ingredientAmounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientAmounts in body
     */
    @GetMapping("/ingredient-amounts")
    public ResponseEntity<List<IngredientAmountDTO>> getAllIngredientAmounts(Pageable pageable) {
        log.debug("REST request to get a page of IngredientAmounts");
        Page<IngredientAmountDTO> page = ingredientAmountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredient-amounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ingredient-amounts/:id : get the "id" ingredientAmount.
     *
     * @param id the id of the ingredientAmountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientAmountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-amounts/{id}")
    public ResponseEntity<IngredientAmountDTO> getIngredientAmount(@PathVariable Long id) {
        log.debug("REST request to get IngredientAmount : {}", id);
        Optional<IngredientAmountDTO> ingredientAmountDTO = ingredientAmountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientAmountDTO);
    }

    /**
     * DELETE  /ingredient-amounts/:id : delete the "id" ingredientAmount.
     *
     * @param id the id of the ingredientAmountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-amounts/{id}")
    public ResponseEntity<Void> deleteIngredientAmount(@PathVariable Long id) {
        log.debug("REST request to delete IngredientAmount : {}", id);
        ingredientAmountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ingredient-amounts?query=:query : search for the ingredientAmount corresponding
     * to the query.
     *
     * @param query the query of the ingredientAmount search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ingredient-amounts")
    public ResponseEntity<List<IngredientAmountDTO>> searchIngredientAmounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IngredientAmounts for query {}", query);
        Page<IngredientAmountDTO> page = ingredientAmountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ingredient-amounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
