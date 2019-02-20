package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RecipesappApp;

import io.github.jhipster.application.domain.IngredientAmount;
import io.github.jhipster.application.repository.IngredientAmountRepository;
import io.github.jhipster.application.repository.search.IngredientAmountSearchRepository;
import io.github.jhipster.application.service.IngredientAmountService;
import io.github.jhipster.application.service.dto.IngredientAmountDTO;
import io.github.jhipster.application.service.mapper.IngredientAmountMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IngredientAmountResource REST controller.
 *
 * @see IngredientAmountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipesappApp.class)
public class IngredientAmountResourceIntTest {

    private static final BigDecimal DEFAULT_INGREDIENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INGREDIENT_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_INGREDIENT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENT_UNIT = "BBBBBBBBBB";

    @Autowired
    private IngredientAmountRepository ingredientAmountRepository;

    @Autowired
    private IngredientAmountMapper ingredientAmountMapper;

    @Autowired
    private IngredientAmountService ingredientAmountService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.IngredientAmountSearchRepositoryMockConfiguration
     */
    @Autowired
    private IngredientAmountSearchRepository mockIngredientAmountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIngredientAmountMockMvc;

    private IngredientAmount ingredientAmount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredientAmountResource ingredientAmountResource = new IngredientAmountResource(ingredientAmountService);
        this.restIngredientAmountMockMvc = MockMvcBuilders.standaloneSetup(ingredientAmountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientAmount createEntity(EntityManager em) {
        IngredientAmount ingredientAmount = new IngredientAmount()
            .ingredientAmount(DEFAULT_INGREDIENT_AMOUNT)
            .ingredientUnit(DEFAULT_INGREDIENT_UNIT);
        return ingredientAmount;
    }

    @Before
    public void initTest() {
        ingredientAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientAmount() throws Exception {
        int databaseSizeBeforeCreate = ingredientAmountRepository.findAll().size();

        // Create the IngredientAmount
        IngredientAmountDTO ingredientAmountDTO = ingredientAmountMapper.toDto(ingredientAmount);
        restIngredientAmountMockMvc.perform(post("/api/ingredient-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAmountDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientAmount in the database
        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientAmount testIngredientAmount = ingredientAmountList.get(ingredientAmountList.size() - 1);
        assertThat(testIngredientAmount.getIngredientAmount()).isEqualTo(DEFAULT_INGREDIENT_AMOUNT);
        assertThat(testIngredientAmount.getIngredientUnit()).isEqualTo(DEFAULT_INGREDIENT_UNIT);

        // Validate the IngredientAmount in Elasticsearch
        verify(mockIngredientAmountSearchRepository, times(1)).save(testIngredientAmount);
    }

    @Test
    @Transactional
    public void createIngredientAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientAmountRepository.findAll().size();

        // Create the IngredientAmount with an existing ID
        ingredientAmount.setId(1L);
        IngredientAmountDTO ingredientAmountDTO = ingredientAmountMapper.toDto(ingredientAmount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientAmountMockMvc.perform(post("/api/ingredient-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientAmount in the database
        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeCreate);

        // Validate the IngredientAmount in Elasticsearch
        verify(mockIngredientAmountSearchRepository, times(0)).save(ingredientAmount);
    }

    @Test
    @Transactional
    public void checkIngredientAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientAmountRepository.findAll().size();
        // set the field null
        ingredientAmount.setIngredientAmount(null);

        // Create the IngredientAmount, which fails.
        IngredientAmountDTO ingredientAmountDTO = ingredientAmountMapper.toDto(ingredientAmount);

        restIngredientAmountMockMvc.perform(post("/api/ingredient-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAmountDTO)))
            .andExpect(status().isBadRequest());

        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredientAmounts() throws Exception {
        // Initialize the database
        ingredientAmountRepository.saveAndFlush(ingredientAmount);

        // Get all the ingredientAmountList
        restIngredientAmountMockMvc.perform(get("/api/ingredient-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].ingredientAmount").value(hasItem(DEFAULT_INGREDIENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].ingredientUnit").value(hasItem(DEFAULT_INGREDIENT_UNIT.toString())));
    }
    
    @Test
    @Transactional
    public void getIngredientAmount() throws Exception {
        // Initialize the database
        ingredientAmountRepository.saveAndFlush(ingredientAmount);

        // Get the ingredientAmount
        restIngredientAmountMockMvc.perform(get("/api/ingredient-amounts/{id}", ingredientAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientAmount.getId().intValue()))
            .andExpect(jsonPath("$.ingredientAmount").value(DEFAULT_INGREDIENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.ingredientUnit").value(DEFAULT_INGREDIENT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientAmount() throws Exception {
        // Get the ingredientAmount
        restIngredientAmountMockMvc.perform(get("/api/ingredient-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientAmount() throws Exception {
        // Initialize the database
        ingredientAmountRepository.saveAndFlush(ingredientAmount);

        int databaseSizeBeforeUpdate = ingredientAmountRepository.findAll().size();

        // Update the ingredientAmount
        IngredientAmount updatedIngredientAmount = ingredientAmountRepository.findById(ingredientAmount.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientAmount are not directly saved in db
        em.detach(updatedIngredientAmount);
        updatedIngredientAmount
            .ingredientAmount(UPDATED_INGREDIENT_AMOUNT)
            .ingredientUnit(UPDATED_INGREDIENT_UNIT);
        IngredientAmountDTO ingredientAmountDTO = ingredientAmountMapper.toDto(updatedIngredientAmount);

        restIngredientAmountMockMvc.perform(put("/api/ingredient-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAmountDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientAmount in the database
        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeUpdate);
        IngredientAmount testIngredientAmount = ingredientAmountList.get(ingredientAmountList.size() - 1);
        assertThat(testIngredientAmount.getIngredientAmount()).isEqualTo(UPDATED_INGREDIENT_AMOUNT);
        assertThat(testIngredientAmount.getIngredientUnit()).isEqualTo(UPDATED_INGREDIENT_UNIT);

        // Validate the IngredientAmount in Elasticsearch
        verify(mockIngredientAmountSearchRepository, times(1)).save(testIngredientAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientAmount() throws Exception {
        int databaseSizeBeforeUpdate = ingredientAmountRepository.findAll().size();

        // Create the IngredientAmount
        IngredientAmountDTO ingredientAmountDTO = ingredientAmountMapper.toDto(ingredientAmount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientAmountMockMvc.perform(put("/api/ingredient-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientAmountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientAmount in the database
        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IngredientAmount in Elasticsearch
        verify(mockIngredientAmountSearchRepository, times(0)).save(ingredientAmount);
    }

    @Test
    @Transactional
    public void deleteIngredientAmount() throws Exception {
        // Initialize the database
        ingredientAmountRepository.saveAndFlush(ingredientAmount);

        int databaseSizeBeforeDelete = ingredientAmountRepository.findAll().size();

        // Delete the ingredientAmount
        restIngredientAmountMockMvc.perform(delete("/api/ingredient-amounts/{id}", ingredientAmount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientAmount> ingredientAmountList = ingredientAmountRepository.findAll();
        assertThat(ingredientAmountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IngredientAmount in Elasticsearch
        verify(mockIngredientAmountSearchRepository, times(1)).deleteById(ingredientAmount.getId());
    }

    @Test
    @Transactional
    public void searchIngredientAmount() throws Exception {
        // Initialize the database
        ingredientAmountRepository.saveAndFlush(ingredientAmount);
        when(mockIngredientAmountSearchRepository.search(queryStringQuery("id:" + ingredientAmount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ingredientAmount), PageRequest.of(0, 1), 1));
        // Search the ingredientAmount
        restIngredientAmountMockMvc.perform(get("/api/_search/ingredient-amounts?query=id:" + ingredientAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].ingredientAmount").value(hasItem(DEFAULT_INGREDIENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].ingredientUnit").value(hasItem(DEFAULT_INGREDIENT_UNIT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientAmount.class);
        IngredientAmount ingredientAmount1 = new IngredientAmount();
        ingredientAmount1.setId(1L);
        IngredientAmount ingredientAmount2 = new IngredientAmount();
        ingredientAmount2.setId(ingredientAmount1.getId());
        assertThat(ingredientAmount1).isEqualTo(ingredientAmount2);
        ingredientAmount2.setId(2L);
        assertThat(ingredientAmount1).isNotEqualTo(ingredientAmount2);
        ingredientAmount1.setId(null);
        assertThat(ingredientAmount1).isNotEqualTo(ingredientAmount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientAmountDTO.class);
        IngredientAmountDTO ingredientAmountDTO1 = new IngredientAmountDTO();
        ingredientAmountDTO1.setId(1L);
        IngredientAmountDTO ingredientAmountDTO2 = new IngredientAmountDTO();
        assertThat(ingredientAmountDTO1).isNotEqualTo(ingredientAmountDTO2);
        ingredientAmountDTO2.setId(ingredientAmountDTO1.getId());
        assertThat(ingredientAmountDTO1).isEqualTo(ingredientAmountDTO2);
        ingredientAmountDTO2.setId(2L);
        assertThat(ingredientAmountDTO1).isNotEqualTo(ingredientAmountDTO2);
        ingredientAmountDTO1.setId(null);
        assertThat(ingredientAmountDTO1).isNotEqualTo(ingredientAmountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ingredientAmountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ingredientAmountMapper.fromId(null)).isNull();
    }
}
