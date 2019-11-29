import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceta } from 'app/shared/model/receta.model';

@Component({
  selector: 'jhi-receta-detail',
  templateUrl: './receta-detail.component.html'
})
export class RecetaDetailComponent implements OnInit {
  receta: IReceta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ receta }) => {
      this.receta = receta;
    });
  }

  previousState() {
    window.history.back();
  }
}
