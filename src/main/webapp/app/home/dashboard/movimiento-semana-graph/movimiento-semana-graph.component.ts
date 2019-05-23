import { Component, Input, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { MovimientosService } from 'app/entities/movimientos';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { TipoMovimiento } from 'app/shared/model/movimientos.model';

@Component({
    selector: 'jhi-movimiento-semana-graph',
    templateUrl: './movimiento-semana-graph.component.html',
    styleUrls: ['movimiento-semana-graph.scss']
})
export class MovimientoSemanaGraphComponent implements OnInit {
    data: any;
    @Input() empresa: IEmpresa;
    dias: string;

    constructor(private $localStorage: LocalStorageService, private movimientoService: MovimientosService) {}

    ngOnInit() {
        this.dias = '7';
        this.loadGraph();
    }

    loadGraph() {
        console.log(this.dias);
        const label: string[] = [];
        const ventas = [];
        const presupuesto = [];
        this.movimientoService.queryBySemanaEmpresa(this.empresa.id, this.dias).subscribe(resp => {
            resp.body.forEach(sem => {
                console.log(sem);
                label.push(sem.fechaMovimiento.format('DD/MM').toString());
                if (sem.tipoMovimiento === TipoMovimiento.VENTA) {
                    ventas.push(sem.total);
                } else {
                    ventas.push('0');
                }

                if (sem.tipoMovimiento === TipoMovimiento.PRESUPUESTO) {
                    presupuesto.push(sem.total);
                } else {
                    presupuesto.push('0');
                }
            });
            this.data = null;
            this.data = {
                labels: label,
                datasets: [
                    {
                        label: 'Ventas',
                        backgroundColor: '#42A5F5',
                        borderColor: '#1E88E5',
                        data: ventas
                    }
                ]
            };
        });
    }

    diasChange(dias: string) {
        console.log(dias);
        this.dias = dias;
        this.loadGraph();
    }
}
