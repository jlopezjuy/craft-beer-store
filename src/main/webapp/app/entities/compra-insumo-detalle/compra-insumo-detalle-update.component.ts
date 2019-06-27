import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompraInsumoDetalle } from 'app/shared/model/compra-insumo-detalle.model';
import { CompraInsumoDetalleService } from './compra-insumo-detalle.service';
import { ICompraInsumo } from 'app/shared/model/compra-insumo.model';
import { CompraInsumoService } from 'app/entities/compra-insumo';
import { IInsumoRecomendado } from 'app/shared/model/insumo-recomendado.model';
import { InsumoRecomendadoService } from 'app/entities/insumo-recomendado';

@Component({
  selector: 'jhi-compra-insumo-detalle-update',
  templateUrl: './compra-insumo-detalle-update.component.html'
})
export class CompraInsumoDetalleUpdateComponent implements OnInit {
  compraInsumoDetalle: ICompraInsumoDetalle;
  isSaving: boolean;

  comprainsumos: ICompraInsumo[];

  insumorecomendados: IInsumoRecomendado[];

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected compraInsumoDetalleService: CompraInsumoDetalleService,
    protected compraInsumoService: CompraInsumoService,
    protected insumoRecomendadoService: InsumoRecomendadoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compraInsumoDetalle }) => {
      this.compraInsumoDetalle = compraInsumoDetalle;
    });
    this.compraInsumoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompraInsumo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompraInsumo[]>) => response.body)
      )
      .subscribe((res: ICompraInsumo[]) => (this.comprainsumos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.insumoRecomendadoService
      .query()
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
    if (this.compraInsumoDetalle.id !== undefined) {
      this.subscribeToSaveResponse(this.compraInsumoDetalleService.update(this.compraInsumoDetalle));
    } else {
      this.subscribeToSaveResponse(this.compraInsumoDetalleService.create(this.compraInsumoDetalle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumoDetalle>>) {
    result.subscribe((res: HttpResponse<ICompraInsumoDetalle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompraInsumoById(index: number, item: ICompraInsumo) {
    return item.id;
  }

  trackInsumoRecomendadoById(index: number, item: IInsumoRecomendado) {
    return item.id;
  }
}
