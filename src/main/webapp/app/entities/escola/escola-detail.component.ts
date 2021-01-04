import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEscola } from 'app/shared/model/escola.model';

@Component({
  selector: 'jhi-escola-detail',
  templateUrl: './escola-detail.component.html',
})
export class EscolaDetailComponent implements OnInit {
  escola: IEscola | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ escola }) => (this.escola = escola));
  }

  previousState(): void {
    window.history.back();
  }
}
