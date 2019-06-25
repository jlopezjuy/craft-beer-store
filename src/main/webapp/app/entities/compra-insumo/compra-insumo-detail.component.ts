import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';

@Component({
  selector: 'jhi-compra-insumo-detail',
  templateUrl: './compra-insumo-detail.component.html'
})
export class CompraInsumoDetailComponent implements OnInit {
  compraInsumo: ICompraInsumo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      this.compraInsumo = compraInsumo;
    });
  }

  previousState() {
    window.history.back();
  }
}
