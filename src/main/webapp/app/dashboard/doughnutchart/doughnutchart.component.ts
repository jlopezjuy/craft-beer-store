import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { CajaService } from 'app/entities/caja';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-piechart',
    templateUrl: './doughnutchart.component.html',
    styles: []
})
export class DoughnutchartComponent implements OnInit {
    data: any;

    constructor(protected cajaService: CajaService, private $localStorage: LocalStorageService) {}

    ngOnInit() {
        this.loadChart();
    }

    loadChart() {
        console.log('entro a cargar el grafico !!!!!');
        const empresa: IEmpresa = this.$localStorage.retrieve('empresa');
        this.cajaService.findIngresoEgreso(empresa.id).subscribe(resp => {
            console.log(resp);
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
