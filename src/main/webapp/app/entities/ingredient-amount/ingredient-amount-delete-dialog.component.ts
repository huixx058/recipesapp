import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientAmount } from 'app/shared/model/ingredient-amount.model';
import { IngredientAmountService } from './ingredient-amount.service';

@Component({
    selector: 'jhi-ingredient-amount-delete-dialog',
    templateUrl: './ingredient-amount-delete-dialog.component.html'
})
export class IngredientAmountDeleteDialogComponent {
    ingredientAmount: IIngredientAmount;

    constructor(
        protected ingredientAmountService: IngredientAmountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredientAmountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ingredientAmountListModification',
                content: 'Deleted an ingredientAmount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingredient-amount-delete-popup',
    template: ''
})
export class IngredientAmountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingredientAmount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IngredientAmountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ingredientAmount = ingredientAmount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ingredient-amount', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ingredient-amount', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
