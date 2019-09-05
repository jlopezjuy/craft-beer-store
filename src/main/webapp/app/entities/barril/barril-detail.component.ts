import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBarril } from 'app/shared/model/barril.model';

@Component({
  selector: 'jhi-barril-detail',
  templateUrl: './barril-detail.component.html'
})
export class BarrilDetailComponent implements OnInit {
  barril: IBarril;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ barril }) => {
      this.barril = barril;
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
