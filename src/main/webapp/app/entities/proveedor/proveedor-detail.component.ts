import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProveedor } from 'app/shared/model/proveedor.model';

@Component({
  selector: 'jhi-proveedor-detail',
  templateUrl: './proveedor-detail.component.html'
})
export class ProveedorDetailComponent implements OnInit {
  proveedor: IProveedor;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
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
