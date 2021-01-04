import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICurso, Curso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';
import { CursoComponent } from './curso.component';
import { CursoDetailComponent } from './curso-detail.component';
import { CursoUpdateComponent } from './curso-update.component';

@Injectable({ providedIn: 'root' })
export class CursoResolve implements Resolve<ICurso> {
  constructor(private service: CursoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((curso: HttpResponse<Curso>) => {
          if (curso.body) {
            return of(curso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Curso());
  }
}

export const cursoRoute: Routes = [
  {
    path: '',
    component: CursoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'escolaApp.curso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CursoDetailComponent,
    resolve: {
      curso: CursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.curso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CursoUpdateComponent,
    resolve: {
      curso: CursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.curso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CursoUpdateComponent,
    resolve: {
      curso: CursoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.curso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
