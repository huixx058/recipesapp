import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIngredientAmount } from 'app/shared/model/ingredient-amount.model';
import { IngredientAmountService } from './ingredient-amount.service';
import { IRecipee } from 'app/shared/model/recipee.model';
import { RecipeeService } from 'app/entities/recipee';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-ingredient-amount-update',
    templateUrl: './ingredient-amount-update.component.html'
})
export class IngredientAmountUpdateComponent implements OnInit {
    ingredientAmount: IIngredientAmount;
    isSaving: boolean;

    recipees: IRecipee[];

    ingredients: IIngredient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ingredientAmountService: IngredientAmountService,
        protected recipeeService: RecipeeService,
        protected ingredientService: IngredientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingredientAmount }) => {
            this.ingredientAmount = ingredientAmount;
        });
        this.recipeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRecipee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRecipee[]>) => response.body)
            )
            .subscribe((res: IRecipee[]) => (this.recipees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.ingredientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIngredient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIngredient[]>) => response.body)
            )
            .subscribe((res: IIngredient[]) => (this.ingredients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ingredientAmount.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredientAmountService.update(this.ingredientAmount));
        } else {
            this.subscribeToSaveResponse(this.ingredientAmountService.create(this.ingredientAmount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientAmount>>) {
        result.subscribe((res: HttpResponse<IIngredientAmount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRecipeeById(index: number, item: IRecipee) {
        return item.id;
    }

    trackIngredientById(index: number, item: IIngredient) {
        return item.id;
    }
}
