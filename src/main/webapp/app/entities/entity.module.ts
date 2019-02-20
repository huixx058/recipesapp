import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'recipee',
                loadChildren: './recipee/recipee.module#RecipesappRecipeeModule'
            },
            {
                path: 'ingredient',
                loadChildren: './ingredient/ingredient.module#RecipesappIngredientModule'
            },
            {
                path: 'ingredient-amount',
                loadChildren: './ingredient-amount/ingredient-amount.module#RecipesappIngredientAmountModule'
            },
            {
                path: 'meal-type',
                loadChildren: './meal-type/meal-type.module#RecipesappMealTypeModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipesappEntityModule {}
