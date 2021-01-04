import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProva } from 'app/shared/model/prova.model';

@Component({
  selector: 'jhi-prova-detail',
  templateUrl: './prova-detail.component.html',
})
export class ProvaDetailComponent implements OnInit {
  prova: IProva | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prova }) => (this.prova = prova));
  }

  previousState(): void {
    window.history.back();
  }
}
