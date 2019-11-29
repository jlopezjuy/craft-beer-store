import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovimientos } from 'app/shared/model/movimientos.model';

@Component({
  selector: 'jhi-movimientos-detail',
  templateUrl: './movimientos-detail.component.html'
})
export class MovimientosDetailComponent implements OnInit {
  movimientos: IMovimientos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ movimientos }) => {
      this.movimientos = movimientos;
    });
  }

  previousState() {
    window.history.back();
  }
}
