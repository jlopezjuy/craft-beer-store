import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstilos } from 'app/shared/model/estilos.model';

@Component({
  selector: 'jhi-estilos-detail',
  templateUrl: './estilos-detail.component.html'
})
export class EstilosDetailComponent implements OnInit {
  estilos: IEstilos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estilos }) => {
      this.estilos = estilos;
    });
  }

  previousState() {
    window.history.back();
  }
}
