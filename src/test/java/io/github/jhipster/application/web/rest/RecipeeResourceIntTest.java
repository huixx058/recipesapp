package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.RecipesappApp;

import io.github.jhipster.application.domain.Recipee;
import io.github.jhipster.application.repository.RecipeeRepository;
import io.github.jhipster.application.repository.search.RecipeeSearchRepository;
import io.github.jhipster.application.service.RecipeeService;
import io.github.jhipster.application.service.dto.RecipeeDTO;
import io.github.jhipster.application.service.mapper.RecipeeMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Test class for the RecipeeResource REST controller.
 *
 * @see RecipeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipesappApp.class)
public class RecipeeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STEPS = "AAAAAAAAAA";
    private static final String UPDATED_STEPS = "BBBBBBBBBB";

    @Autowired
    private RecipeeRepository recipeeRepository;

    @Mock
    private RecipeeRepository recipeeRepositoryMock;

    @Autowired
    private RecipeeMapper recipeeMapper;

    @Mock
    private RecipeeService recipeeServiceMock;

    @Autowired
    private RecipeeService recipeeService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RecipeeSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecipeeSearchRepository mockRecipeeSearchRepository;

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

    private MockMvc restRecipeeMockMvc;

    private Recipee recipee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeeResource recipeeResource = new RecipeeResource(recipeeService);
        this.restRecipeeMockMvc = MockMvcBuilders.standaloneSetup(recipeeResource)
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
    public static Recipee createEntity(EntityManager em) {
        Recipee recipee = new Recipee()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .steps(DEFAULT_STEPS);
        return recipee;
    }

    @Before
    public void initTest() {
        recipee = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipee() throws Exception {
        int databaseSizeBeforeCreate = recipeeRepository.findAll().size();

        // Create the Recipee
        RecipeeDTO recipeeDTO = recipeeMapper.toDto(recipee);
        restRecipeeMockMvc.perform(post("/api/recipees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipee in the database
        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipee testRecipee = recipeeList.get(recipeeList.size() - 1);
        assertThat(testRecipee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecipee.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecipee.getSteps()).isEqualTo(DEFAULT_STEPS);

        // Validate the Recipee in Elasticsearch
        verify(mockRecipeeSearchRepository, times(1)).save(testRecipee);
    }

    @Test
    @Transactional
    public void createRecipeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeeRepository.findAll().size();

        // Create the Recipee with an existing ID
        recipee.setId(1L);
        RecipeeDTO recipeeDTO = recipeeMapper.toDto(recipee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeeMockMvc.perform(post("/api/recipees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipee in the database
        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Recipee in Elasticsearch
        verify(mockRecipeeSearchRepository, times(0)).save(recipee);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeeRepository.findAll().size();
        // set the field null
        recipee.setName(null);

        // Create the Recipee, which fails.
        RecipeeDTO recipeeDTO = recipeeMapper.toDto(recipee);

        restRecipeeMockMvc.perform(post("/api/recipees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeeDTO)))
            .andExpect(status().isBadRequest());

        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipees() throws Exception {
        // Initialize the database
        recipeeRepository.saveAndFlush(recipee);

        // Get all the recipeeList
        restRecipeeMockMvc.perform(get("/api/recipees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRecipeesWithEagerRelationshipsIsEnabled() throws Exception {
        RecipeeResource recipeeResource = new RecipeeResource(recipeeServiceMock);
        when(recipeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRecipeeMockMvc = MockMvcBuilders.standaloneSetup(recipeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecipeeMockMvc.perform(get("/api/recipees?eagerload=true"))
        .andExpect(status().isOk());

        verify(recipeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRecipeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RecipeeResource recipeeResource = new RecipeeResource(recipeeServiceMock);
            when(recipeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRecipeeMockMvc = MockMvcBuilders.standaloneSetup(recipeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecipeeMockMvc.perform(get("/api/recipees?eagerload=true"))
        .andExpect(status().isOk());

            verify(recipeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRecipee() throws Exception {
        // Initialize the database
        recipeeRepository.saveAndFlush(recipee);

        // Get the recipee
        restRecipeeMockMvc.perform(get("/api/recipees/{id}", recipee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.steps").value(DEFAULT_STEPS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipee() throws Exception {
        // Get the recipee
        restRecipeeMockMvc.perform(get("/api/recipees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipee() throws Exception {
        // Initialize the database
        recipeeRepository.saveAndFlush(recipee);

        int databaseSizeBeforeUpdate = recipeeRepository.findAll().size();

        // Update the recipee
        Recipee updatedRecipee = recipeeRepository.findById(recipee.getId()).get();
        // Disconnect from session so that the updates on updatedRecipee are not directly saved in db
        em.detach(updatedRecipee);
        updatedRecipee
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .steps(UPDATED_STEPS);
        RecipeeDTO recipeeDTO = recipeeMapper.toDto(updatedRecipee);

        restRecipeeMockMvc.perform(put("/api/recipees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeeDTO)))
            .andExpect(status().isOk());

        // Validate the Recipee in the database
        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeUpdate);
        Recipee testRecipee = recipeeList.get(recipeeList.size() - 1);
        assertThat(testRecipee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecipee.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecipee.getSteps()).isEqualTo(UPDATED_STEPS);

        // Validate the Recipee in Elasticsearch
        verify(mockRecipeeSearchRepository, times(1)).save(testRecipee);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipee() throws Exception {
        int databaseSizeBeforeUpdate = recipeeRepository.findAll().size();

        // Create the Recipee
        RecipeeDTO recipeeDTO = recipeeMapper.toDto(recipee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeeMockMvc.perform(put("/api/recipees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipee in the database
        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Recipee in Elasticsearch
        verify(mockRecipeeSearchRepository, times(0)).save(recipee);
    }

    @Test
    @Transactional
    public void deleteRecipee() throws Exception {
        // Initialize the database
        recipeeRepository.saveAndFlush(recipee);

        int databaseSizeBeforeDelete = recipeeRepository.findAll().size();

        // Delete the recipee
        restRecipeeMockMvc.perform(delete("/api/recipees/{id}", recipee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipee> recipeeList = recipeeRepository.findAll();
        assertThat(recipeeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Recipee in Elasticsearch
        verify(mockRecipeeSearchRepository, times(1)).deleteById(recipee.getId());
    }

    @Test
    @Transactional
    public void searchRecipee() throws Exception {
        // Initialize the database
        recipeeRepository.saveAndFlush(recipee);
        when(mockRecipeeSearchRepository.search(queryStringQuery("id:" + recipee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recipee), PageRequest.of(0, 1), 1));
        // Search the recipee
        restRecipeeMockMvc.perform(get("/api/_search/recipees?query=id:" + recipee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipee.class);
        Recipee recipee1 = new Recipee();
        recipee1.setId(1L);
        Recipee recipee2 = new Recipee();
        recipee2.setId(recipee1.getId());
        assertThat(recipee1).isEqualTo(recipee2);
        recipee2.setId(2L);
        assertThat(recipee1).isNotEqualTo(recipee2);
        recipee1.setId(null);
        assertThat(recipee1).isNotEqualTo(recipee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeeDTO.class);
        RecipeeDTO recipeeDTO1 = new RecipeeDTO();
        recipeeDTO1.setId(1L);
        RecipeeDTO recipeeDTO2 = new RecipeeDTO();
        assertThat(recipeeDTO1).isNotEqualTo(recipeeDTO2);
        recipeeDTO2.setId(recipeeDTO1.getId());
        assertThat(recipeeDTO1).isEqualTo(recipeeDTO2);
        recipeeDTO2.setId(2L);
        assertThat(recipeeDTO1).isNotEqualTo(recipeeDTO2);
        recipeeDTO1.setId(null);
        assertThat(recipeeDTO1).isNotEqualTo(recipeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipeeMapper.fromId(null)).isNull();
    }
}
