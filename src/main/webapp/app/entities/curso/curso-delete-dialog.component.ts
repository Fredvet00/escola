import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICurso } from 'app/shared/model/curso.model';
import { CursoService } from './curso.service';

@Component({
  templateUrl: './curso-delete-dialog.component.html',
})
export class CursoDeleteDialogComponent {
  curso?: ICurso;

  constructor(protected cursoService: CursoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cursoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cursoListModification');
      this.activeModal.close();
    });
  }
}
