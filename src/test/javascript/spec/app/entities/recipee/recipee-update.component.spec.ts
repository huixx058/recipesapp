/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RecipesappTestModule } from '../../../test.module';
import { RecipeeUpdateComponent } from 'app/entities/recipee/recipee-update.component';
import { RecipeeService } from 'app/entities/recipee/recipee.service';
import { Recipee } from 'app/shared/model/recipee.model';

describe('Component Tests', () => {
    describe('Recipee Management Update Component', () => {
        let comp: RecipeeUpdateComponent;
        let fixture: ComponentFixture<RecipeeUpdateComponent>;
        let service: RecipeeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RecipesappTestModule],
                declarations: [RecipeeUpdateComponent]
            })
                .overrideTemplate(RecipeeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipeeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipeeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Recipee(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipee = entity;
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
                    const entity = new Recipee();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recipee = entity;
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
