package io.github.jhipster.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MealType.
 */
@Entity
@Table(name = "meal_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mealtype")
public class MealType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "mealType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recipee> mealTypeRecipees = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MealType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipee> getMealTypeRecipees() {
        return mealTypeRecipees;
    }

    public MealType mealTypeRecipees(Set<Recipee> recipees) {
        this.mealTypeRecipees = recipees;
        return this;
    }

    public MealType addMealTypeRecipee(Recipee recipee) {
        this.mealTypeRecipees.add(recipee);
        recipee.setMealType(this);
        return this;
    }

    public MealType removeMealTypeRecipee(Recipee recipee) {
        this.mealTypeRecipees.remove(recipee);
        recipee.setMealType(null);
        return this;
    }

    public void setMealTypeRecipees(Set<Recipee> recipees) {
        this.mealTypeRecipees = recipees;
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
        MealType mealType = (MealType) o;
        if (mealType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mealType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MealType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
