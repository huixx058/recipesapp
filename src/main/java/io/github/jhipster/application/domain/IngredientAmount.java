package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A IngredientAmount.
 */
@Entity
@Table(name = "ingredient_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ingredientamount")
public class IngredientAmount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ingredient_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal ingredientAmount;

    @Column(name = "ingredient_unit")
    private String ingredientUnit;

    @ManyToOne
    @JsonIgnoreProperties("ingredientAmounts")
    private Recipee recipee;

    @ManyToOne
    @JsonIgnoreProperties("ingredientAmounts")
    private Ingredient ingredient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getIngredientAmount() {
        return ingredientAmount;
    }

    public IngredientAmount ingredientAmount(BigDecimal ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
        return this;
    }

    public void setIngredientAmount(BigDecimal ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public IngredientAmount ingredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
        return this;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public Recipee getRecipee() {
        return recipee;
    }

    public IngredientAmount recipee(Recipee recipee) {
        this.recipee = recipee;
        return this;
    }

    public void setRecipee(Recipee recipee) {
        this.recipee = recipee;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public IngredientAmount ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientAmount ingredientAmount = (IngredientAmount) o;
        if (ingredientAmount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientAmount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientAmount{" +
            "id=" + getId() +
            ", ingredientAmount=" + getIngredientAmount() +
            ", ingredientUnit='" + getIngredientUnit() + "'" +
            "}";
    }
}
