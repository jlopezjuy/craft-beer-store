import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { MovimientosService } from 'app/entities/movimientos';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { TipoMovimiento } from 'app/shared/model/movimientos.model';

@Component({
    selector: 'jhi-movimiento-semana-graph',
    templateUrl: './movimiento-semana-graph.component.html',
    styles: []
})
export class MovimientoSemanaGraphComponent implements OnInit {
    data: any;

    constructor(
        private $localStorage: LocalStorageService,
        private movimientoService: MovimientosService,
        private ngxLoader: NgxUiLoaderService
    ) {}

    ngOnInit() {
        this.loadGraph();
    }

    loadGraph() {
        const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
        let label: string[] = [];
        let ventas = [];
        let presupuesto = [];
        this.movimientoService.queryBySemanaEmpresa(null, empresa.id).subscribe(resp => {
            console.log(resp);
            resp.body.forEach(sem => {
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
}
