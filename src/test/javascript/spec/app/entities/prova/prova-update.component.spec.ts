import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EscolaTestModule } from '../../../test.module';
import { ProvaUpdateComponent } from 'app/entities/prova/prova-update.component';
import { ProvaService } from 'app/entities/prova/prova.service';
import { Prova } from 'app/shared/model/prova.model';

describe('Component Tests', () => {
  describe('Prova Management Update Component', () => {
    let comp: ProvaUpdateComponent;
    let fixture: ComponentFixture<ProvaUpdateComponent>;
    let service: ProvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscolaTestModule],
        declarations: [ProvaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProvaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProvaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProvaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Prova(123);
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
        const entity = new Prova();
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
