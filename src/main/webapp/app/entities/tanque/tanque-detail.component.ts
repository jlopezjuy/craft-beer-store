import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITanque } from 'app/shared/model/tanque.model';

@Component({
  selector: 'jhi-tanque-detail',
  templateUrl: './tanque-detail.component.html'
})
export class TanqueDetailComponent implements OnInit {
  tanque: ITanque;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tanque }) => {
      this.tanque = tanque;
    });
  }

  previousState() {
    window.history.back();
  }
}
