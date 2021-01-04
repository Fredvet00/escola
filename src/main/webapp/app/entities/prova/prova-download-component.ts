import { ProvaService } from 'app/entities/prova/prova.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProva } from 'app/shared/model/prova.model';

@Component({
  selector: 'jhi-prova-download',
  templateUrl: './prova-download-component.html',
})
export class ProvaDownloadComponent implements OnInit {
  prova: IProva | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected provaService: ProvaService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prova }) => (this.prova = prova));
  }

  previousState(): void {
    window.history.back();
  }
  download(format: string): void {
    this.provaService.download(format);
    window.alert('aqui1');
  }
}
