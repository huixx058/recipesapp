package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ingredient and its DTO IngredientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {


    @Mapping(target = "recipees", ignore = true)
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
