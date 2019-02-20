import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecipesappSharedModule } from 'app/shared';
import {
    MealTypeComponent,
    MealTypeDetailComponent,
    MealTypeUpdateComponent,
    MealTypeDeletePopupComponent,
    MealTypeDeleteDialogComponent,
    mealTypeRoute,
    mealTypePopupRoute
} from './';

const ENTITY_STATES = [...mealTypeRoute, ...mealTypePopupRoute];

@NgModule({
    imports: [RecipesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MealTypeComponent,
        MealTypeDetailComponent,
        MealTypeUpdateComponent,
        MealTypeDeleteDialogComponent,
        MealTypeDeletePopupComponent
    ],
    entryComponents: [MealTypeComponent, MealTypeUpdateComponent, MealTypeDeleteDialogComponent, MealTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipesappMealTypeModule {}
