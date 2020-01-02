import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { EstadoTanque, ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from './tanque.service';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from 'app/entities/lote';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from '../../shared';

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
  empresa: IEmpresa;
  fechaIngresoDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tanqueService: TanqueService,
    protected loteService: LoteService,
    protected productoService: ProductoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    private $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.empresa = this.$localStorage.retrieve('empresa');
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tanque }) => {
      this.tanque = tanque;
      this.fechaIngresoDp = moment(this.tanque.fechaIngreso, 'dd/MM/yyy').format();
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.tanque.empresaId = this.empresa.id;
    this.tanque.fechaIngreso = this.fechaIngresoDp != null ? moment(this.fechaIngresoDp, DATE_FORMAT) : null;
    if (this.tanque.id !== undefined) {
      this.subscribeToSaveResponse(this.tanqueService.update(this.tanque));
    } else {
      this.tanque.estado = EstadoTanque.VACIO;
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
