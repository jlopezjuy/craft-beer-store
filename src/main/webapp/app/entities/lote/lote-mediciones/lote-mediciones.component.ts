import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ILote } from '../../../shared/model/lote.model';
import { ActivatedRoute } from '@angular/router';
import { SidebarService } from '../../../services/sidebar.service';
import { MedicionLoteService } from '../../medicion-lote';
import { IMedicionLote } from '../../../shared/model/medicion-lote.model';

@Component({
  selector: 'jhi-lote-mediciones',
  templateUrl: './lote-mediciones.component.html',
  styles: []
})
export class LoteMedicionesComponent implements OnInit {
  public sidebarVisible = true;
  medicionesLote: IMedicionLote[];
  lote: ILote;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef,
    private medicionLoteService: MedicionLoteService
  ) {}

  ngOnInit() {
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
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  previousState() {
    window.history.back();
  }
}
