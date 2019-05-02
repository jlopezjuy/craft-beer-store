import { Component, OnInit } from '@angular/core';
import { CajaService } from 'app/entities/caja';
import { LocalStorageService } from 'ngx-webstorage';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
    selector: 'jhi-ingreso-egreso-graph',
    templateUrl: './ingreso-egreso-graph.component.html',
    styleUrls: ['ingreso-egreso-graph.scss']
})
export class IngresoEgresoGraphComponent implements OnInit {
    public data: any;
    constructor(protected cajaService: CajaService, private $localStorage: LocalStorageService, private ngxLoader: NgxUiLoaderService) {}

    ngOnInit() {
        this.loadChart();
    }

    loadChart() {
        this.ngxLoader.start();
        const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
        this.cajaService.findIngresoEgreso(empresa.id).subscribe(resp => {
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
            this.ngxLoader.stop();
        });
    }
}
