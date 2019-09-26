import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtapaLote } from 'app/shared/model/etapa-lote.model';

@Component({
  selector: 'jhi-etapa-lote-detail',
  templateUrl: './etapa-lote-detail.component.html'
})
export class EtapaLoteDetailComponent implements OnInit {
  etapaLote: IEtapaLote;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ etapaLote }) => {
      this.etapaLote = etapaLote;
    });
  }

  previousState() {
    window.history.back();
  }
}
