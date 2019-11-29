import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventoProducto } from 'app/shared/model/evento-producto.model';

@Component({
  selector: 'jhi-evento-producto-detail',
  templateUrl: './evento-producto-detail.component.html'
})
export class EventoProductoDetailComponent implements OnInit {
  eventoProducto: IEventoProducto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventoProducto }) => {
      this.eventoProducto = eventoProducto;
    });
  }

  previousState() {
    window.history.back();
  }
}
