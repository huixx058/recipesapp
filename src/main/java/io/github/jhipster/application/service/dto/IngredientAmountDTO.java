package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the IngredientAmount entity.
 */
public class IngredientAmountDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal ingredientAmount;

    private String ingredientUnit;


    private Long recipeeId;

    private String recipeeName;

    private Long ingredientId;

    private String ingredientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(BigDecimal ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public Long getRecipeeId() {
        return recipeeId;
    }

    public void setRecipeeId(Long recipeeId) {
        this.recipeeId = recipeeId;
    }

    public String getRecipeeName() {
        return recipeeName;
    }

    public void setRecipeeName(String recipeeName) {
        this.recipeeName = recipeeName;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientAmountDTO ingredientAmountDTO = (IngredientAmountDTO) o;
        if (ingredientAmountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientAmountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientAmountDTO{" +
            "id=" + getId() +
            ", ingredientAmount=" + getIngredientAmount() +
            ", ingredientUnit='" + getIngredientUnit() + "'" +
            ", recipee=" + getRecipeeId() +
            ", recipee='" + getRecipeeName() + "'" +
            ", ingredient=" + getIngredientId() +
            ", ingredient='" + getIngredientName() + "'" +
            "}";
    }
}
