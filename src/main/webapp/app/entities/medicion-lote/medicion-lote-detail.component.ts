import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicionLote } from 'app/shared/model/medicion-lote.model';

@Component({
  selector: 'jhi-medicion-lote-detail',
  templateUrl: './medicion-lote-detail.component.html'
})
export class MedicionLoteDetailComponent implements OnInit {
  medicionLote: IMedicionLote;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicionLote }) => {
      this.medicionLote = medicionLote;
    });
  }

  previousState() {
    window.history.back();
  }
}
