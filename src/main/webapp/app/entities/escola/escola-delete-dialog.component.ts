import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscola } from 'app/shared/model/escola.model';
import { EscolaService } from './escola.service';

@Component({
  templateUrl: './escola-delete-dialog.component.html',
})
export class EscolaDeleteDialogComponent {
  escola?: IEscola;

  constructor(protected escolaService: EscolaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.escolaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('escolaListModification');
      this.activeModal.close();
    });
  }
}
