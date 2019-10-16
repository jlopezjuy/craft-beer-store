import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ILote } from '../../shared/model/lote.model';

@Component({
  selector: 'jhi-lote-detail-dialog',
  templateUrl: 'lote-detail-dialog.component.html'
})
export class LoteDetailDialogComponent {
  constructor(public dialogRef: MatDialogRef<LoteDetailDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: ILote) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
