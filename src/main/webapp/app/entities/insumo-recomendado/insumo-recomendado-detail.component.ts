import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';

@Component({
  selector: 'jhi-insumo-recomendado-detail',
  templateUrl: './insumo-recomendado-detail.component.html'
})
export class InsumoRecomendadoDetailComponent implements OnInit {
  insumoRecomendado: IInsumoRecomendado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ insumoRecomendado }) => {
      this.insumoRecomendado = insumoRecomendado;
    });
  }

  previousState() {
    window.history.back();
  }
}
