import { Component, Input, OnInit } from '@angular/core';
import { MovimientosService } from 'app/entities/movimientos';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Component({
  selector: 'jhi-movimiento-productos-semana',
  templateUrl: './movimiento-productos-semana.component.html',
  styleUrls: ['movimiento-productos-semana.scss']
})
export class MovimientoProductosSemanaComponent implements OnInit {
  data: any;
  @Input() empresa: IEmpresa;
  dias: string;
  constructor(protected movimientosService: MovimientosService) {}

  ngOnInit() {
    this.dias = '7';
    this.loadGraph();
  }

  private loadGraph() {
    const label = [];
    const cantidades = [];
    const colores = [];
    this.data = null;
    this.movimientosService.queryProductoBySemanaEmpresa(this.empresa.id, this.dias).subscribe(resp => {
      resp.body.forEach(mov => {
        label.push(mov.nombreProducto);
        cantidades.push(mov.cantidad);
        colores.push(mov.color);
        console.log(mov);
      });
      this.data = {
        datasets: [
          {
            data: cantidades,
            backgroundColor: colores,
            label: 'Productos Vendidos en los ultimos 7 días'
          }
        ],
        labels: label
      };
    });
  }

  diasChange(dias: string) {
    this.dias = dias;
    this.loadGraph();
  }
}
