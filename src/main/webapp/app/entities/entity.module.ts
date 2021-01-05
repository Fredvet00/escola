import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'escola',
        loadChildren: () => import('./escola/escola.module').then(m => m.EscolaEscolaModule),
      },
      {
        path: 'curso',
        loadChildren: () => import('./curso/curso.module').then(m => m.EscolaCursoModule),
      },
      {
        path: 'prova',
        loadChildren: () => import('./prova/prova.module').then(m => m.EscolaProvaModule),
      },
      {
        path: 'aluno',
        loadChildren: () => import('./aluno/aluno.module').then(m => m.EscolaAlunoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EscolaEntityModule {}
