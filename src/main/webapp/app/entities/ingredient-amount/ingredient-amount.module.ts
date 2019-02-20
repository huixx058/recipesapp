import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecipesappSharedModule } from 'app/shared';
import {
    IngredientAmountComponent,
    IngredientAmountDetailComponent,
    IngredientAmountUpdateComponent,
    IngredientAmountDeletePopupComponent,
    IngredientAmountDeleteDialogComponent,
    ingredientAmountRoute,
    ingredientAmountPopupRoute
} from './';

const ENTITY_STATES = [...ingredientAmountRoute, ...ingredientAmountPopupRoute];

@NgModule({
    imports: [RecipesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredientAmountComponent,
        IngredientAmountDetailComponent,
        IngredientAmountUpdateComponent,
        IngredientAmountDeleteDialogComponent,
        IngredientAmountDeletePopupComponent
    ],
    entryComponents: [
        IngredientAmountComponent,
        IngredientAmountUpdateComponent,
        IngredientAmountDeleteDialogComponent,
        IngredientAmountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipesappIngredientAmountModule {}
