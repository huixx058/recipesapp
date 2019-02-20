import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipee } from 'app/shared/model/recipee.model';

@Component({
    selector: 'jhi-recipee-detail',
    templateUrl: './recipee-detail.component.html'
})
export class RecipeeDetailComponent implements OnInit {
    recipee: IRecipee;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipee }) => {
            this.recipee = recipee;
        });
    }

    previousState() {
        window.history.back();
    }
}
