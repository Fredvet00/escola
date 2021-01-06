import { IProva, Prova } from './../../shared/model/prova.model';
import { ProvaService } from 'app/entities/prova/prova.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

declare const require: any;
const FileSaver = require('file-saver');

@Component({
  selector: 'jhi-prova-download',
  templateUrl: './prova-download-component.html',
})
export class ProvaDownloadComponent implements OnInit {
  prova: IProva | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected provaService: ProvaService) {}

  ngOnInit(): void {
    this.prova = new Prova();
    this.activatedRoute.data.subscribe(({ prova }) => (this.prova = prova));
  }

  previousState(): void {
    window.history.back();
  }

  download(): void {
    this.provaService.imprimir(this.prova!).subscribe(data => {
      // eslint-disable-next-line no-console
      console.log(data);
      var blob = new Blob([data], { type: 'application/pdf' });
      var url = URL.createObjectURL(blob);
      FileSaver.saveAs(url, 'prova.pdf');
    });
  }
}
