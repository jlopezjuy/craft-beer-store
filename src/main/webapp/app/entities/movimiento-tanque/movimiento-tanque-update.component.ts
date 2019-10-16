import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMovimientoTanque } from 'app/shared/model/movimiento-tanque.model';
import { MovimientoTanqueService } from './movimiento-tanque.service';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';

@Component({
  selector: 'jhi-movimiento-tanque-update',
  templateUrl: './movimiento-tanque-update.component.html'
})
export class MovimientoTanqueUpdateComponent implements OnInit {
  movimientoTanque: IMovimientoTanque;
  isSaving: boolean;

  tanques: ITanque[];

  productos: IProducto[];

  lotes: ILote[];
  fechaDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movimientoTanqueService: MovimientoTanqueService,
    protected tanqueService: TanqueService,
    protected productoService: ProductoService,
    protected loteService: LoteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movimientoTanque }) => {
      this.movimientoTanque = movimientoTanque;
    });
    this.tanqueService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITanque[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITanque[]>) => response.body)
      )
      .subscribe((res: ITanque[]) => (this.tanques = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.loteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILote[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILote[]>) => response.body)
      )
      .subscribe((res: ILote[]) => (this.lotes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.movimientoTanque.id !== undefined) {
      this.subscribeToSaveResponse(this.movimientoTanqueService.update(this.movimientoTanque));
    } else {
      this.subscribeToSaveResponse(this.movimientoTanqueService.create(this.movimientoTanque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovimientoTanque>>) {
    result.subscribe((res: HttpResponse<IMovimientoTanque>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }
}
