import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProva, Prova } from 'app/shared/model/prova.model';
import { ProvaService } from './prova.service';

@Component({
  selector: 'jhi-prova-update',
  templateUrl: './prova-update.component.html',
})
export class ProvaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
    numquestoes: [],
    enunciado: [],
    texto: [],
  });

  constructor(protected provaService: ProvaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prova }) => {
      this.updateForm(prova);
    });
  }

  updateForm(prova: IProva): void {
    this.editForm.patchValue({
      id: prova.id,
      nome: prova.nome,
      numquestoes: prova.numquestoes,
      enunciado: prova.enunciado,
      texto: prova.texto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prova = this.createFromForm();
    if (prova.id !== undefined) {
      this.subscribeToSaveResponse(this.provaService.update(prova));
    } else {
      this.subscribeToSaveResponse(this.provaService.create(prova));
    }
  }

  private createFromForm(): IProva {
    return {
      ...new Prova(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      numquestoes: this.editForm.get(['numquestoes'])!.value,
      enunciado: this.editForm.get(['enunciado'])!.value,
      texto: this.editForm.get(['texto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProva>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
