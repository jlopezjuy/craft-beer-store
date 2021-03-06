import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    selector: 'jhi-barchart',
    templateUrl: './barchart.component.html',
    styles: []
})
export class BarchartComponent implements OnInit {
    data: any;

    constructor() {
        this.data = {
            labels: ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'May', 'June', 'July'],
            datasets: [
                {
                    label: 'My First dataset',
                    backgroundColor: '#42A5F5',
                    borderColor: '#1E88E5',
                    data: [65, 59, 80, 81, 56, 55, 40]
                },
                {
                    label: 'My Second dataset',
                    backgroundColor: '#9CCC65',
                    borderColor: '#7CB342',
                    data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };
    }

    ngOnInit() {}
}
