import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipee } from 'app/shared/model/recipee.model';
import { RecipeeService } from './recipee.service';

@Component({
    selector: 'jhi-recipee-delete-dialog',
    templateUrl: './recipee-delete-dialog.component.html'
})
export class RecipeeDeleteDialogComponent {
    recipee: IRecipee;

    constructor(protected recipeeService: RecipeeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipeeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recipeeListModification',
                content: 'Deleted an recipee'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipee-delete-popup',
    template: ''
})
export class RecipeeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipee }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecipeeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.recipee = recipee;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/recipee', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/recipee', { outlets: { popup: null } }]);
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
