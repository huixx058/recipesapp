import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IngredientAmount } from 'app/shared/model/ingredient-amount.model';
import { IngredientAmountService } from './ingredient-amount.service';
import { IngredientAmountComponent } from './ingredient-amount.component';
import { IngredientAmountDetailComponent } from './ingredient-amount-detail.component';
import { IngredientAmountUpdateComponent } from './ingredient-amount-update.component';
import { IngredientAmountDeletePopupComponent } from './ingredient-amount-delete-dialog.component';
import { IIngredientAmount } from 'app/shared/model/ingredient-amount.model';

@Injectable({ providedIn: 'root' })
export class IngredientAmountResolve implements Resolve<IIngredientAmount> {
    constructor(private service: IngredientAmountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIngredientAmount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IngredientAmount>) => response.ok),
                map((ingredientAmount: HttpResponse<IngredientAmount>) => ingredientAmount.body)
            );
        }
        return of(new IngredientAmount());
    }
}

export const ingredientAmountRoute: Routes = [
    {
        path: '',
        component: IngredientAmountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'IngredientAmounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: IngredientAmountDetailComponent,
        resolve: {
            ingredientAmount: IngredientAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientAmounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: IngredientAmountUpdateComponent,
        resolve: {
            ingredientAmount: IngredientAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientAmounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: IngredientAmountUpdateComponent,
        resolve: {
            ingredientAmount: IngredientAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientAmounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientAmountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: IngredientAmountDeletePopupComponent,
        resolve: {
            ingredientAmount: IngredientAmountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'IngredientAmounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
