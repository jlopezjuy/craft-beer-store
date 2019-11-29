import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEquipamiento } from 'app/shared/model/equipamiento.model';

@Component({
  selector: 'jhi-equipamiento-detail',
  templateUrl: './equipamiento-detail.component.html'
})
export class EquipamientoDetailComponent implements OnInit {
  equipamiento: IEquipamiento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipamiento }) => {
      this.equipamiento = equipamiento;
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
