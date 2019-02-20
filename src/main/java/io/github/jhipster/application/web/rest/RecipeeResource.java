package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.RecipeeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.RecipeeDTO;
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
 * REST controller for managing Recipee.
 */
@RestController
@RequestMapping("/api")
public class RecipeeResource {

    private final Logger log = LoggerFactory.getLogger(RecipeeResource.class);

    private static final String ENTITY_NAME = "recipee";

    private final RecipeeService recipeeService;

    public RecipeeResource(RecipeeService recipeeService) {
        this.recipeeService = recipeeService;
    }

    /**
     * POST  /recipees : Create a new recipee.
     *
     * @param recipeeDTO the recipeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeeDTO, or with status 400 (Bad Request) if the recipee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipees")
    public ResponseEntity<RecipeeDTO> createRecipee(@Valid @RequestBody RecipeeDTO recipeeDTO) throws URISyntaxException {
        log.debug("REST request to save Recipee : {}", recipeeDTO);
        if (recipeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeeDTO result = recipeeService.save(recipeeDTO);
        return ResponseEntity.created(new URI("/api/recipees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipees : Updates an existing recipee.
     *
     * @param recipeeDTO the recipeeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeeDTO,
     * or with status 400 (Bad Request) if the recipeeDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipees")
    public ResponseEntity<RecipeeDTO> updateRecipee(@Valid @RequestBody RecipeeDTO recipeeDTO) throws URISyntaxException {
        log.debug("REST request to update Recipee : {}", recipeeDTO);
        if (recipeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeeDTO result = recipeeService.save(recipeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipees : get all the recipees.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of recipees in body
     */
    @GetMapping("/recipees")
    public ResponseEntity<List<RecipeeDTO>> getAllRecipees(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Recipees");
        Page<RecipeeDTO> page;
        if (eagerload) {
            page = recipeeService.findAllWithEagerRelationships(pageable);
        } else {
            page = recipeeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/recipees?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /recipees/:id : get the "id" recipee.
     *
     * @param id the id of the recipeeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipees/{id}")
    public ResponseEntity<RecipeeDTO> getRecipee(@PathVariable Long id) {
        log.debug("REST request to get Recipee : {}", id);
        Optional<RecipeeDTO> recipeeDTO = recipeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeeDTO);
    }

    /**
     * DELETE  /recipees/:id : delete the "id" recipee.
     *
     * @param id the id of the recipeeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipees/{id}")
    public ResponseEntity<Void> deleteRecipee(@PathVariable Long id) {
        log.debug("REST request to delete Recipee : {}", id);
        recipeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recipees?query=:query : search for the recipee corresponding
     * to the query.
     *
     * @param query the query of the recipee search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recipees")
    public ResponseEntity<List<RecipeeDTO>> searchRecipees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recipees for query {}", query);
        Page<RecipeeDTO> page = recipeeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recipees");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
