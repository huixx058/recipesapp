import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RecipesappSharedModule } from 'app/shared';
import {
    RecipeeComponent,
    RecipeeDetailComponent,
    RecipeeUpdateComponent,
    RecipeeDeletePopupComponent,
    RecipeeDeleteDialogComponent,
    recipeeRoute,
    recipeePopupRoute
} from './';

const ENTITY_STATES = [...recipeeRoute, ...recipeePopupRoute];

@NgModule({
    imports: [RecipesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipeeComponent,
        RecipeeDetailComponent,
        RecipeeUpdateComponent,
        RecipeeDeleteDialogComponent,
        RecipeeDeletePopupComponent
    ],
    entryComponents: [RecipeeComponent, RecipeeUpdateComponent, RecipeeDeleteDialogComponent, RecipeeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RecipesappRecipeeModule {}
