import { NgModule } from '@angular/core';

import { RecipesappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [RecipesappSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [RecipesappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RecipesappSharedCommonModule {}
