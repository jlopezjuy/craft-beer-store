import { Component, Input, OnInit } from '@angular/core';
import { CajaService } from 'app/entities/caja';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Component({
  selector: 'jhi-ingreso-egreso-graph',
  templateUrl: './ingreso-egreso-graph.component.html',
  styleUrls: ['ingreso-egreso-graph.scss']
})
export class IngresoEgresoGraphComponent implements OnInit {
  public data: any;
  @Input() empresa: IEmpresa;

  constructor(protected cajaService: CajaService) {}

  ngOnInit() {
    this.loadChart();
  }

  loadChart() {
    this.cajaService.findIngresoEgreso(this.empresa.id).subscribe(resp => {
      this.data = {
        labels: ['Ingresos', 'Egresos'],
        datasets: [
          {
            data: [resp.body.ingreso, resp.body.egreso],
            backgroundColor: ['#77DD77', '#FF6961'],
            hoverBackgroundColor: ['#5aa65a', '#df5b54']
          }
        ]
      };
    });
  }
}
