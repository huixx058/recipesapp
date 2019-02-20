package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Recipee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Recipee entity.
 */
public interface RecipeeSearchRepository extends ElasticsearchRepository<Recipee, Long> {
}
