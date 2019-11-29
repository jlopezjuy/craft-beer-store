import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICaja } from 'app/shared/model/caja.model';

@Component({
  selector: 'jhi-caja-detail',
  templateUrl: './caja-detail.component.html'
})
export class CajaDetailComponent implements OnInit {
  caja: ICaja;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ caja }) => {
      this.caja = caja;
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
