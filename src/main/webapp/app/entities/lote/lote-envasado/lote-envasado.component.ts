import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SidebarService } from '../../../services/sidebar.service';
import { ActivatedRoute } from '@angular/router';
import { ILote } from '../../../shared/model/lote.model';

@Component({
  selector: 'jhi-lote-envasado',
  templateUrl: './lote-envasado.component.html',
  styles: []
})
export class LoteEnvasadoComponent implements OnInit {
  public sidebarVisible = true;
  lote: ILote;
  constructor(protected activatedRoute: ActivatedRoute, private sidebarService: SidebarService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      console.log(this.lote);
      if (this.lote.id) {
        this.loadAll();
      }
    });
  }

  loadAll() {}

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  previousState() {
    window.history.back();
  }
}
