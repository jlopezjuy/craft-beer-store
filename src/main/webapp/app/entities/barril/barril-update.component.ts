import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IBarril } from 'app/shared/model/barril.model';
import { BarrilService } from './barril.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-barril-update',
  templateUrl: './barril-update.component.html'
})
export class BarrilUpdateComponent implements OnInit {
  barril: IBarril;
  isSaving: boolean;
  empresa: IEmpresa;
  empresas: IEmpresa[];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected barrilService: BarrilService,
    protected empresaService: EmpresaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ barril }) => {
      this.barril = barril;
    });
    this.empresa = this.$localStorage.retrieve('empresa');
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.barril, this.elementRef, field, fieldContentType, idInput);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.barril.empresaId = this.empresa.id;
    if (this.barril.id !== undefined) {
      this.subscribeToSaveResponse(this.barrilService.update(this.barril));
    } else {
      this.subscribeToSaveResponse(this.barrilService.create(this.barril));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarril>>) {
    result.subscribe((res: HttpResponse<IBarril>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }
}
