package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IngredientAmount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IngredientAmount entity.
 */
public interface IngredientAmountSearchRepository extends ElasticsearchRepository<IngredientAmount, Long> {
}
