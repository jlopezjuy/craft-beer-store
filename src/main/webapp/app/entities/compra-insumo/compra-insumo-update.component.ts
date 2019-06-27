import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from './compra-insumo.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { DATE_FORMAT } from '../../shared';
import { LocalStorageService } from 'ngx-webstorage';
import { CompraInsumoDetalleService } from '../compra-insumo-detalle';
import { CompraInsumoDetalle, ICompraInsumoDetalle } from '../../shared/model/compra-insumo-detalle.model';
import { IInsumoRecomendado } from '../../shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from '../insumo-recomendado';

@Component({
  selector: 'jhi-compra-insumo-update',
  templateUrl: './compra-insumo-update.component.html'
})
export class CompraInsumoUpdateComponent implements OnInit {
  compraInsumo: ICompraInsumo;
  isSaving: boolean;
  compraInsumoDetalles: ICompraInsumoDetalle[];
  compraInsumoDetalle: ICompraInsumoDetalle;
  insumorecomendados: IInsumoRecomendado[];
  proveedors: IProveedor[];

  empresa: IEmpresa;
  fechaDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoService: CompraInsumoService,
    protected proveedorService: ProveedorService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected $localStorage: LocalStorageService,
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
    protected insumoRecomendadoService: InsumoRecomendadoService
  ) {}

  ngOnInit() {
    this.compraInsumoDetalle = new CompraInsumoDetalle();
    this.isSaving = false;
    this.empresa = this.$localStorage.retrieve('empresa');
    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      this.compraInsumo = compraInsumo;
      if (this.compraInsumo.id) {
        this.fechaDp = moment(this.compraInsumo.fecha, 'dd/MM/yyy').format();
        this.loadAllOnEdit();
      } else {
        this.compraInsumoDetalles = [];
      }
    });
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.insumoRecomendadoService
      .queryAll()
      .pipe(
        filter((mayBeOk: HttpResponse<IInsumoRecomendado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInsumoRecomendado[]>) => response.body)
      )
      .subscribe((res: IInsumoRecomendado[]) => (this.insumorecomendados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.compraInsumo.empresaId = this.empresa.id;
    this.compraInsumo.fecha = this.fechaDp != null ? moment(this.fechaDp, DATE_FORMAT) : null;
    if (this.compraInsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoService.update(this.compraInsumo));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoService.create(this.compraInsumo));
    }
  }

  saveCompraInsumos(compraInsumoId: number) {
    this.compraInsumoDetalles.forEach(compra => {
      compra.compraInsumoId = compraInsumoId;
    });
    this.compraInsumoDetalleService.createList(this.compraInsumoDetalles).subscribe(resp => {
      console.log('ok');
      console.log(resp);
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumo>>) {
    result.subscribe((res: HttpResponse<ICompraInsumo>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess(resp: ICompraInsumo) {
    this.saveCompraInsumos(resp.id);
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  loadAllOnEdit() {
    this.compraInsumoDetalleService.findAllByCompraInsumo(this.compraInsumo.id).subscribe(response => {
      this.compraInsumoDetalles = response.body;
    });
  }

  trackId(index: number, item: ICompraInsumoDetalle) {
    return item.id;
  }

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }

  addInsumo() {
    console.log(this.compraInsumoDetalle);
    this.compraInsumoDetalles.push(this.compraInsumoDetalle);
    this.compraInsumoDetalle = new CompraInsumoDetalle();
  }
}
