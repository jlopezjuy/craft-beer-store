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

@Component({
  selector: 'jhi-compra-insumo-update',
  templateUrl: './compra-insumo-update.component.html'
})
export class CompraInsumoUpdateComponent implements OnInit {
  compraInsumo: ICompraInsumo;
  isSaving: boolean;

  proveedors: IProveedor[];
  fechaDp: any;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoService: CompraInsumoService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compraInsumo }) => {
      this.compraInsumo = compraInsumo;
    });
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.compraInsumo.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoService.update(this.compraInsumo));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoService.create(this.compraInsumo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumo>>) {
    result.subscribe((res: HttpResponse<ICompraInsumo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }
}
