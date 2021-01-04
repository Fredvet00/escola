import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EscolaTestModule } from '../../../test.module';
import { EscolaUpdateComponent } from 'app/entities/escola/escola-update.component';
import { EscolaService } from 'app/entities/escola/escola.service';
import { Escola } from 'app/shared/model/escola.model';

describe('Component Tests', () => {
  describe('Escola Management Update Component', () => {
    let comp: EscolaUpdateComponent;
    let fixture: ComponentFixture<EscolaUpdateComponent>;
    let service: EscolaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscolaTestModule],
        declarations: [EscolaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EscolaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscolaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscolaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Escola(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Escola();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
