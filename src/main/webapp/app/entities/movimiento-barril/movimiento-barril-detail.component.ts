import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovimientoBarril } from 'app/shared/model/movimiento-barril.model';

@Component({
  selector: 'jhi-movimiento-barril-detail',
  templateUrl: './movimiento-barril-detail.component.html'
})
export class MovimientoBarrilDetailComponent implements OnInit {
  movimientoBarril: IMovimientoBarril;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movimientoBarril }) => {
      this.movimientoBarril = movimientoBarril;
    });
  }

  previousState() {
    window.history.back();
  }
}
