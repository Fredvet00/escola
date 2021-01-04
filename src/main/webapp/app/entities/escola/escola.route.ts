import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEscola, Escola } from 'app/shared/model/escola.model';
import { EscolaService } from './escola.service';
import { EscolaComponent } from './escola.component';
import { EscolaDetailComponent } from './escola-detail.component';
import { EscolaUpdateComponent } from './escola-update.component';

@Injectable({ providedIn: 'root' })
export class EscolaResolve implements Resolve<IEscola> {
  constructor(private service: EscolaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEscola> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((escola: HttpResponse<Escola>) => {
          if (escola.body) {
            return of(escola.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Escola());
  }
}

export const escolaRoute: Routes = [
  {
    path: '',
    component: EscolaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'escolaApp.escola.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EscolaDetailComponent,
    resolve: {
      escola: EscolaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.escola.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EscolaUpdateComponent,
    resolve: {
      escola: EscolaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.escola.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EscolaUpdateComponent,
    resolve: {
      escola: EscolaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.escola.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
