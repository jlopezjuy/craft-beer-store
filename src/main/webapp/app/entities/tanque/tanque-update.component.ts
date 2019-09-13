import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
  selector: 'jhi-tanque-update',
  templateUrl: './tanque-update.component.html'
})
export class TanqueUpdateComponent implements OnInit {
  tanque: ITanque;
  isSaving: boolean;

  lotes: ILote[];

  productos: IProducto[];

  empresas: IEmpresa[];
  fechaIngresoDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tanqueService: TanqueService,
    protected loteService: LoteService,
    protected productoService: ProductoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tanque }) => {
      this.tanque = tanque;
    });
    this.loteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILote[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILote[]>) => response.body)
      )
      .subscribe((res: ILote[]) => (this.lotes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.tanque.id !== undefined) {
      this.subscribeToSaveResponse(this.tanqueService.update(this.tanque));
    } else {
      this.subscribeToSaveResponse(this.tanqueService.create(this.tanque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITanque>>) {
    result.subscribe((res: HttpResponse<ITanque>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackLoteById(index: number, item: ILote) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
