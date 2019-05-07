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

    constructor(protected movimientosService: MovimientosService) {}

    ngOnInit() {
        console.log(this.empresa);
        const label = [];
        const cantidades = [];
        this.movimientosService.queryProductoBySemanaEmpresa(this.empresa.id).subscribe(resp => {
            console.log(resp.body);
            resp.body.forEach(mov => {
                label.push(mov.nombreProducto);
                cantidades.push(mov.cantidad);
            });
        });
        this.data = {
            datasets: [
                {
                    data: cantidades,
                    backgroundColor: ['#ff6961', '#282828', '#FFCE56'],
                    label: 'Productos Vendidos en los ultimos 7 días'
                }
            ],
            labels: label
        };
    }
}
