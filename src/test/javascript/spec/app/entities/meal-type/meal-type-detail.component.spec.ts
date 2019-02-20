/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecipesappTestModule } from '../../../test.module';
import { MealTypeDetailComponent } from 'app/entities/meal-type/meal-type-detail.component';
import { MealType } from 'app/shared/model/meal-type.model';

describe('Component Tests', () => {
    describe('MealType Management Detail Component', () => {
        let comp: MealTypeDetailComponent;
        let fixture: ComponentFixture<MealTypeDetailComponent>;
        const route = ({ data: of({ mealType: new MealType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecipesappTestModule],
                declarations: [MealTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MealTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MealTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mealType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
