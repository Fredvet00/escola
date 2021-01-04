import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscolaTestModule } from '../../../test.module';
import { EscolaDetailComponent } from 'app/entities/escola/escola-detail.component';
import { Escola } from 'app/shared/model/escola.model';

describe('Component Tests', () => {
  describe('Escola Management Detail Component', () => {
    let comp: EscolaDetailComponent;
    let fixture: ComponentFixture<EscolaDetailComponent>;
    const route = ({ data: of({ escola: new Escola(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscolaTestModule],
        declarations: [EscolaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EscolaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscolaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load escola on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.escola).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
