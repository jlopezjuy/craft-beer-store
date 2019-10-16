import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';

@Component({
  selector: 'jhi-movimiento-tanque-detail',
  templateUrl: './movimiento-tanque-detail.component.html'
})
export class MovimientoTanqueDetailComponent implements OnInit {
  movimientoTanque: IMovimientoTanque;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movimientoTanque }) => {
      this.movimientoTanque = movimientoTanque;
    });
  }

  previousState() {
    window.history.back();
  }
}
