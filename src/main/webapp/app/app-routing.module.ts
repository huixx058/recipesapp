import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];
const routes: Routes = [
    {
        path: 'admin',
        loadChildren: './admin/admin.module#RecipesappAdminModule'
    },
    ...LAYOUT_ROUTES
];
@NgModule({
    imports: [RouterModule.forRoot(routes, { useHash: true, enableTracing: DEBUG_INFO_ENABLED })],
    exports: [RouterModule]
})
export class RecipesappAppRoutingModule {}
