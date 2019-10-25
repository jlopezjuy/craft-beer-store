import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ILote } from '../../../shared/model/lote.model';
import { ActivatedRoute } from '@angular/router';
import { SidebarService } from '../../../services/sidebar.service';
import { MedicionLoteService } from '../../medicion-lote';
import {IMedicionLote, MedicionLote} from '../../../shared/model/medicion-lote.model';
import {ITanque} from "../../../shared/model/tanque.model";
import {filter, map} from "rxjs/operators";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {TanqueService} from "../../tanque";
import {JhiAlertService} from "ng-jhipster";

@Component({
  selector: 'jhi-lote-mediciones',
  templateUrl: './lote-mediciones.component.html',
  styles: []
})
export class LoteMedicionesComponent implements OnInit {
  public sidebarVisible = true;
  medicionesLote: IMedicionLote[] = [];
  medicionLote: IMedicionLote;
  lote: ILote;
  fechaRealizado: any;
  horaRealizado: any;
  tanques: ITanque[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef,
    private medicionLoteService: MedicionLoteService,
    protected tanqueService: TanqueService,
    protected jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.medicionLote = new MedicionLote();
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      console.log(this.lote);
      if (this.lote.id) {
        this.loadAll();
      }
    });
  }

  loadAll() {
    this.medicionLoteService.queryLote(null, this.lote.id).subscribe(resp => {
      this.medicionesLote = resp.body;
    });
    this.tanqueService
      .queryByEmpresaLote(null, this.lote.empresaId, this.lote.id)
      .pipe(
        filter((mayBeOk: HttpResponse<ITanque[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITanque[]>) => response.body)
      )
      .subscribe((res: ITanque[]) => (this.tanques = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  previousState() {
    window.history.back();
  }
}
