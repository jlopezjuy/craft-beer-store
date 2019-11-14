import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInsumo } from 'app/shared/model/insumo.model';

@Component({
  selector: 'jhi-insumo-detail',
  templateUrl: './insumo-detail.component.html'
})
export class InsumoDetailComponent implements OnInit {
  insumo: IInsumo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ insumo }) => {
      this.insumo = insumo;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
