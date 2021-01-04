import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EscolaTestModule } from '../../../test.module';
import { ProvaDetailComponent } from 'app/entities/prova/prova-detail.component';
import { Prova } from 'app/shared/model/prova.model';

describe('Component Tests', () => {
  describe('Prova Management Detail Component', () => {
    let comp: ProvaDetailComponent;
    let fixture: ComponentFixture<ProvaDetailComponent>;
    const route = ({ data: of({ prova: new Prova(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EscolaTestModule],
        declarations: [ProvaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProvaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProvaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prova on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prova).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
