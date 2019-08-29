import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEquipamiento } from 'app/shared/model/equipamiento.model';
import { EquipamientoService } from './equipamiento.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/account/settings/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from 'app/shared';

@Component({
  selector: 'jhi-equipamiento-update',
  templateUrl: './equipamiento-update.component.html'
})
export class EquipamientoUpdateComponent implements OnInit {
  equipamiento: IEquipamiento;
  isSaving: boolean;
  empresa: IEmpresa;
  fechaCompraDp: any;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected equipamientoService: EquipamientoService,
    protected empresaService: EmpresaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipamiento }) => {
      this.equipamiento = equipamiento;
      if (this.equipamiento.id) {
        this.fechaCompraDp = moment(this.equipamiento.fechaCompra, 'dd/MM/yyy').format();
      }
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
    this.dataUtils.clearInputImage(this.equipamiento, this.elementRef, field, fieldContentType, idInput);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.equipamiento.empresaId = this.empresa.id;
    this.isSaving = true;
    this.equipamiento.fechaCompra = this.fechaCompraDp != null ? moment(this.fechaCompraDp, DATE_FORMAT) : null;
    if (this.equipamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.equipamientoService.update(this.equipamiento));
    } else {
      this.subscribeToSaveResponse(this.equipamientoService.create(this.equipamiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipamiento>>) {
    result.subscribe((res: HttpResponse<IEquipamiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
