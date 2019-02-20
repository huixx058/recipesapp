package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.RecipeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recipee and its DTO RecipeeDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class, MealTypeMapper.class})
public interface RecipeeMapper extends EntityMapper<RecipeeDTO, Recipee> {

    @Mapping(source = "mealType.id", target = "mealTypeId")
    @Mapping(source = "mealType.name", target = "mealTypeName")
    RecipeeDTO toDto(Recipee recipee);

    @Mapping(source = "mealTypeId", target = "mealType")
    Recipee toEntity(RecipeeDTO recipeeDTO);

    default Recipee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipee recipee = new Recipee();
        recipee.setId(id);
        return recipee;
    }
}
