import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientAmount } from 'app/shared/model/ingredient-amount.model';

@Component({
    selector: 'jhi-ingredient-amount-detail',
    templateUrl: './ingredient-amount-detail.component.html'
})
export class IngredientAmountDetailComponent implements OnInit {
    ingredientAmount: IIngredientAmount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientAmount }) => {
            this.ingredientAmount = ingredientAmount;
        });
    }

    previousState() {
        window.history.back();
    }
}
