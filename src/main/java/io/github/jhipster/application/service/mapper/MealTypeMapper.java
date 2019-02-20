package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.MealTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MealType and its DTO MealTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MealTypeMapper extends EntityMapper<MealTypeDTO, MealType> {


    @Mapping(target = "mealTypeRecipees", ignore = true)
    MealType toEntity(MealTypeDTO mealTypeDTO);

    default MealType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MealType mealType = new MealType();
        mealType.setId(id);
        return mealType;
    }
}
