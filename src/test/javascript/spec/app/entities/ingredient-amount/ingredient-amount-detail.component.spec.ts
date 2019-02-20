/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecipesappTestModule } from '../../../test.module';
import { IngredientAmountDetailComponent } from 'app/entities/ingredient-amount/ingredient-amount-detail.component';
import { IngredientAmount } from 'app/shared/model/ingredient-amount.model';

describe('Component Tests', () => {
    describe('IngredientAmount Management Detail Component', () => {
        let comp: IngredientAmountDetailComponent;
        let fixture: ComponentFixture<IngredientAmountDetailComponent>;
        const route = ({ data: of({ ingredientAmount: new IngredientAmount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecipesappTestModule],
                declarations: [IngredientAmountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IngredientAmountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IngredientAmountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ingredientAmount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
