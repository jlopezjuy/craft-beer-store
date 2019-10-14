import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILote } from 'app/shared/model/lote.model';
import { EtapaLoteService } from '../etapa-lote';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { EtapaLote, IEtapaLote } from '../../shared/model/etapa-lote.model';
import { JhiAlertService } from 'ng-jhipster';
import { RecetaService } from '../receta';
import { IReceta } from '../../shared/model/receta.model';
import { TanqueService } from '../tanque';
import { ITanque } from '../../shared/model/tanque.model';
import moment = require('moment');
import { DATE_FORMAT } from '../../shared';
import { MatTableDataSource } from '@angular/material';
import { IRecetaInsumo } from '../../shared/model/receta-insumo.model';

@Component({
  selector: 'jhi-lote-detail',
  templateUrl: './lote-detail.component.html'
})
export class LoteDetailComponent implements OnInit {
  lote: ILote;
  etapaLotes: IEtapaLote[];
  receta: IReceta;
  etapaLote: IEtapaLote;
  tanques: ITanque[];
  dataSource: any;
  displayedColumns: string[] = ['etapa', 'tanqueNombre', 'litros', 'inicio', 'fin', 'dias'];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected etapaLoteService: EtapaLoteService,
    protected recetaService: RecetaService,
    protected jhiAlertService: JhiAlertService,
    protected tanqueService: TanqueService
  ) {}

  ngOnInit() {
    this.etapaLote = new EtapaLote();
    this.etapaLotes = [];
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      if (this.lote.id) {
        this.loadAll();
      }
    });
  }

  loadAll() {
    console.log(this.lote);
    this.etapaLoteService
      .queryByLote(null, this.lote.id)
      .subscribe(
        (res: HttpResponse<IEtapaLote[]>) => this.paginateEtapaLotes(res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.recetaService.find(this.lote.recetaId).subscribe(response => {
      this.receta = response.body;
    });
    this.tanqueService.queryByEmpresa(null, this.lote.empresaId).subscribe(resp => {
      this.tanques = resp.body;
    });
  }

  protected paginateEtapaLotes(data: IEtapaLote[]) {
    this.etapaLotes = data;
    this.dataSource = new MatTableDataSource<IEtapaLote>(this.etapaLotes);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  saveEtapa() {
    this.etapaLote.inicio = moment(new Date(), DATE_FORMAT);
    this.etapaLotes.push(this.etapaLote);
    this.etapaLote = new EtapaLote();
    console.log(this.etapaLotes);
    this.dataSource = new MatTableDataSource<IEtapaLote>(this.etapaLotes);
  }

  previousState() {
    window.history.back();
  }
}
