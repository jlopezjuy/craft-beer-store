import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { EstadoLote, ILote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from 'app/entities/receta';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { ITanque } from 'app/shared/model/tanque.model';
import { TanqueService } from 'app/entities/tanque';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from '../../shared';

@Component({
  selector: 'jhi-lote-update',
  templateUrl: './lote-update.component.html'
})
export class LoteUpdateComponent implements OnInit {
  lote: ILote;
  isSaving: boolean;

  recetas: IReceta[];
  events: string[] = [];
  empresas: IEmpresa[];

  empresa: IEmpresa;

  productos: IProducto[];

  tanques: ITanque[];
  fechaCoccionDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loteService: LoteService,
    protected recetaService: RecetaService,
    protected empresaService: EmpresaService,
    protected productoService: ProductoService,
    protected tanqueService: TanqueService,
    protected activatedRoute: ActivatedRoute,
    private $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.empresa = this.$localStorage.retrieve('empresa');
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      if (this.lote.id) {
        this.fechaCoccionDp = moment(this.lote.fechaCoccion, 'dd/MM/yyy').format();
      }
    });

    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.lote.empresaId = this.empresa.id;
    this.lote.fechaCoccion = this.fechaCoccionDp != null ? moment(this.fechaCoccionDp, DATE_FORMAT) : null;
    if (this.lote.id !== undefined) {
      this.subscribeToSaveResponse(this.loteService.update(this.lote));
    } else {
      this.lote.estado = EstadoLote.PLANIFICADO;
      this.subscribeToSaveResponse(this.loteService.create(this.lote));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILote>>) {
    result.subscribe((res: HttpResponse<ILote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRecetaById(index: number, item: IReceta) {
    return item.id;
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  changeProducto() {
    console.log(this.lote.productoId);
    this.recetaService.findAllByProducto(null, this.lote.productoId).subscribe(resp => {
      this.recetas = resp.body;
      // this.lote.litrosEstimados = this.recetas
    });
  }

  changeReceta() {
    console.log(this.lote.recetaId);
    this.recetaService.find(this.lote.recetaId).subscribe(resp => {
      this.lote.litrosEstimados = resp.body.batch;
    });
  }

  updateCalcs() {
    const fecha = this.fechaCoccionDp != null ? moment(this.fechaCoccionDp, DATE_FORMAT) : null;
    const fecha2 = moment(fecha).format('MMDDYYYY');
    if (this.lote.productoId) {
      this.lote.codigo = this.lote.productoId + fecha2;
    }
  }
}
