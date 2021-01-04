import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProva, Prova } from 'app/shared/model/prova.model';
import { ProvaService } from './prova.service';
import { ProvaComponent } from './prova.component';
import { ProvaDetailComponent } from './prova-detail.component';
import { ProvaUpdateComponent } from './prova-update.component';

@Injectable({ providedIn: 'root' })
export class ProvaResolve implements Resolve<IProva> {
  constructor(private service: ProvaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProva> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prova: HttpResponse<Prova>) => {
          if (prova.body) {
            return of(prova.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Prova());
  }
}

export const provaRoute: Routes = [
  {
    path: '',
    component: ProvaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'escolaApp.prova.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProvaDetailComponent,
    resolve: {
      prova: ProvaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.prova.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProvaUpdateComponent,
    resolve: {
      prova: ProvaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.prova.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProvaUpdateComponent,
    resolve: {
      prova: ProvaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'escolaApp.prova.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
