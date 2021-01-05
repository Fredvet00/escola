import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProva } from 'app/shared/model/prova.model';
import { ProvaService } from './prova.service';

@Component({
  templateUrl: './prova-delete-dialog.component.html',
})
export class ProvaDeleteDialogComponent {
  prova?: IProva;

  constructor(protected provaService: ProvaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.provaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('provaListModification');
      this.activeModal.close();
    });
  }
}
