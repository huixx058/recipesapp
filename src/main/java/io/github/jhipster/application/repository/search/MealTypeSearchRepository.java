package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.MealType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MealType entity.
 */
public interface MealTypeSearchRepository extends ElasticsearchRepository<MealType, Long> {
}
