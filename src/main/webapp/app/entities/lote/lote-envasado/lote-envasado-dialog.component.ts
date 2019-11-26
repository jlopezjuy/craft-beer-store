import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IPresentacion } from '../../../shared/model/presentacion.model';

@Component({
  selector: 'jhi-lote-envasado-dialog',
  templateUrl: 'lote-envasado-dialog.component.html'
})
export class LoteEnvasadoDialogComponent {
  constructor(public dialogRef: MatDialogRef<LoteEnvasadoDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: IPresentacion) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
