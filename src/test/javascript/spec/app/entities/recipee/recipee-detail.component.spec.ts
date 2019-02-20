/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RecipesappTestModule } from '../../../test.module';
import { RecipeeDetailComponent } from 'app/entities/recipee/recipee-detail.component';
import { Recipee } from 'app/shared/model/recipee.model';

describe('Component Tests', () => {
    describe('Recipee Management Detail Component', () => {
        let comp: RecipeeDetailComponent;
        let fixture: ComponentFixture<RecipeeDetailComponent>;
        const route = ({ data: of({ recipee: new Recipee(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecipesappTestModule],
                declarations: [RecipeeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecipeeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipeeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recipee).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
