import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recipee } from 'app/shared/model/recipee.model';
import { RecipeeService } from './recipee.service';
import { RecipeeComponent } from './recipee.component';
import { RecipeeDetailComponent } from './recipee-detail.component';
import { RecipeeUpdateComponent } from './recipee-update.component';
import { RecipeeDeletePopupComponent } from './recipee-delete-dialog.component';
import { IRecipee } from 'app/shared/model/recipee.model';

@Injectable({ providedIn: 'root' })
export class RecipeeResolve implements Resolve<IRecipee> {
    constructor(private service: RecipeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecipee> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recipee>) => response.ok),
                map((recipee: HttpResponse<Recipee>) => recipee.body)
            );
        }
        return of(new Recipee());
    }
}

export const recipeeRoute: Routes = [
    {
        path: '',
        component: RecipeeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Recipees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RecipeeDetailComponent,
        resolve: {
            recipee: RecipeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RecipeeUpdateComponent,
        resolve: {
            recipee: RecipeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RecipeeUpdateComponent,
        resolve: {
            recipee: RecipeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipeePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RecipeeDeletePopupComponent,
        resolve: {
            recipee: RecipeeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
