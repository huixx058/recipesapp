/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecipesappTestModule } from '../../../test.module';
import { IngredientAmountUpdateComponent } from 'app/entities/ingredient-amount/ingredient-amount-update.component';
import { IngredientAmountService } from 'app/entities/ingredient-amount/ingredient-amount.service';
import { IngredientAmount } from 'app/shared/model/ingredient-amount.model';

describe('Component Tests', () => {
    describe('IngredientAmount Management Update Component', () => {
        let comp: IngredientAmountUpdateComponent;
        let fixture: ComponentFixture<IngredientAmountUpdateComponent>;
        let service: IngredientAmountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecipesappTestModule],
                declarations: [IngredientAmountUpdateComponent]
            })
                .overrideTemplate(IngredientAmountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IngredientAmountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IngredientAmountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IngredientAmount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientAmount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IngredientAmount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ingredientAmount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
