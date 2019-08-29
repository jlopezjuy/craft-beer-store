import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';

@Component({
  selector: 'jhi-compra-insumo-detalle-detail',
  templateUrl: './compra-insumo-detalle-detail.component.html'
})
export class CompraInsumoDetalleDetailComponent implements OnInit {
  compraInsumoDetalle: ICompraInsumoDetalle;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compraInsumoDetalle }) => {
      this.compraInsumoDetalle = compraInsumoDetalle;
    });
  }

  previousState() {
    window.history.back();
  }
}
