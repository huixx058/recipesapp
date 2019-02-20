package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.IngredientAmountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IngredientAmount and its DTO IngredientAmountDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipeeMapper.class, IngredientMapper.class})
public interface IngredientAmountMapper extends EntityMapper<IngredientAmountDTO, IngredientAmount> {

    @Mapping(source = "recipee.id", target = "recipeeId")
    @Mapping(source = "recipee.name", target = "recipeeName")
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    IngredientAmountDTO toDto(IngredientAmount ingredientAmount);

    @Mapping(source = "recipeeId", target = "recipee")
    @Mapping(source = "ingredientId", target = "ingredient")
    IngredientAmount toEntity(IngredientAmountDTO ingredientAmountDTO);

    default IngredientAmount fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientAmount ingredientAmount = new IngredientAmount();
        ingredientAmount.setId(id);
        return ingredientAmount;
    }
}
