import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecipee } from 'app/shared/model/recipee.model';
import { RecipeeService } from './recipee.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';
import { IMealType } from 'app/shared/model/meal-type.model';
import { MealTypeService } from 'app/entities/meal-type';

@Component({
    selector: 'jhi-recipee-update',
    templateUrl: './recipee-update.component.html'
})
export class RecipeeUpdateComponent implements OnInit {
    recipee: IRecipee;
    isSaving: boolean;

    ingredients: IIngredient[];

    mealtypes: IMealType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recipeeService: RecipeeService,
        protected ingredientService: IngredientService,
        protected mealTypeService: MealTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipee }) => {
            this.recipee = recipee;
        });
        this.ingredientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IIngredient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IIngredient[]>) => response.body)
            )
            .subscribe((res: IIngredient[]) => (this.ingredients = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.mealTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMealType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMealType[]>) => response.body)
            )
            .subscribe((res: IMealType[]) => (this.mealtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipee.id !== undefined) {
            this.subscribeToSaveResponse(this.recipeeService.update(this.recipee));
        } else {
            this.subscribeToSaveResponse(this.recipeeService.create(this.recipee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipee>>) {
        result.subscribe((res: HttpResponse<IRecipee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIngredientById(index: number, item: IIngredient) {
        return item.id;
    }

    trackMealTypeById(index: number, item: IMealType) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
