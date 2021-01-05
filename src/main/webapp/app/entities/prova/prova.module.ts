import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EscolaSharedModule } from 'app/shared/shared.module';
import { ProvaComponent } from './prova.component';
import { ProvaDetailComponent } from './prova-detail.component';
import { ProvaUpdateComponent } from './prova-update.component';
import { ProvaDeleteDialogComponent } from './prova-delete-dialog.component';
import { provaRoute } from './prova.route';
import { ProvaDownloadComponent } from './prova-download-component';

@NgModule({
  imports: [EscolaSharedModule, RouterModule.forChild(provaRoute)],
  declarations: [ProvaComponent, ProvaDetailComponent, ProvaUpdateComponent, ProvaDeleteDialogComponent, ProvaDownloadComponent],
  entryComponents: [ProvaDeleteDialogComponent],
})
export class EscolaProvaModule {}
