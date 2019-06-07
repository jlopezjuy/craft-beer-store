import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReceta } from 'app/shared/model/receta.model';
import { RecetaService } from './receta.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_FORMAT } from 'app/shared';
import { InsumoService } from 'app/entities/insumo';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { IInsumo, TipoInsumo } from 'app/shared/model/insumo.model';
import { IRecetaInsumo, ModoLupulo, RecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { MatTableDataSource } from '@angular/material';
import { RecetaInsumoService } from 'app/entities/receta-insumo';

@Component({
    selector: 'jhi-receta-update',
    templateUrl: './receta-update.component.html',
    styleUrls: ['receta-update.component.scss']
})
export class RecetaUpdateComponent implements OnInit {
    receta: IReceta;
    isSaving: boolean;
    fechaDp: any;
    producto: IProducto;
    maxDate = new Date();
    empresa: IEmpresa;
    maltas: IInsumo[];
    lupulos: IInsumo[];
    levaduras: IInsumo[];
    otros: IInsumo[];
    recetaInsumo: IRecetaInsumo = new RecetaInsumo();
    recetaInsumoLupulo: IRecetaInsumo = new RecetaInsumo();
    recetaInsumoLeva: IRecetaInsumo = new RecetaInsumo();
    recetaInsumoOtro: IRecetaInsumo = new RecetaInsumo();
    maltasList: IRecetaInsumo[] = [];
    lupulosList: IRecetaInsumo[] = [];
    levadurasList: IRecetaInsumo[] = [];
    otrosList: IRecetaInsumo[] = [];
    maltasListRemove: IRecetaInsumo[] = [];
    lupulosListRemove: IRecetaInsumo[] = [];
    levadurasListRemove: IRecetaInsumo[] = [];
    otrosListRemove: IRecetaInsumo[] = [];

    dataSourceMalta: any;
    displayedColumnsMalta: string[] = ['nombreMalta', 'cantidad', 'color', 'porcentaje', 'usoMalta', 'action'];
    displayedColumnsLupulo: string[] = ['nombreMalta', 'alpha', 'modoLupulo', 'gramos', 'usoLupulo', 'tiempo', 'ibu', 'action'];
    displayedColumnsLevadura: string[] = ['nombreMalta', 'gramos', 'densidadLeva', 'tamSobre', 'atenuacion', 'action'];
    displayedColumnsOtro: string[] = ['nombreMalta', 'gramos', 'tipoOtro', 'usoOtro', 'tiempoOtro', 'action'];

    dataSourceLupulo: any;

    dataSourceLeva: any;

    dataSourceOtro: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recetaService: RecetaService,
        protected productoService: ProductoService,
        protected activatedRoute: ActivatedRoute,
        protected insumoService: InsumoService,
        protected recetaInsumoService: RecetaInsumoService,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receta }) => {
            this.receta = receta;
            if (this.receta.id) {
                console.log(this.receta);
                this.fechaDp = moment(this.receta.fecha, 'dd/MM/yyy').format();
                this.loadDataEdit();
            }
        });
        this.producto = this.$localStorage.retrieve('producto');
        this.receta.productoNombreComercial = this.producto.nombreComercial;
        this.empresa = this.$localStorage.retrieve('empresa');
        console.log(this.empresa);
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.MALTA).subscribe(resp => {
            this.maltas = resp.body;
        });
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.LUPULO).subscribe(resp => {
            this.lupulos = resp.body;
        });
        this.insumoService.queryByEmpresaTipo(this.empresa.id, TipoInsumo.LEVADURA).subscribe(resp => {
            this.levaduras = resp.body;
        });
        this.insumoService
            .queryByEmpresaNotInTipo(this.empresa.id, { tipoInsumos: [TipoInsumo.MALTA, TipoInsumo.LEVADURA, TipoInsumo.LUPULO] })
            .subscribe(resp => {
                this.otros = resp.body;
            });
    }

    previousState() {
        window.history.back();
    }

    save() {
        // this.calculoIbu();
        this.calculoAlcohol();
        this.isSaving = true;
        this.receta.productoId = this.producto.id;
        this.receta.fecha = this.fechaDp != null ? moment(this.fechaDp, DATE_FORMAT) : null;
        if (this.receta.id !== undefined) {
            this.subscribeToSaveResponse(this.recetaService.update(this.receta));
        } else {
            this.subscribeToSaveResponse(this.recetaService.create(this.receta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceta>>) {
        result.subscribe((res: HttpResponse<IReceta>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(receta: IReceta) {
        this.isSaving = false;
        this.removeInsumos();
        this.saveRecetaInsumos(receta);
        this.previousState();
    }

    saveRecetaInsumos(receta: IReceta) {
        this.maltasList.forEach(malta => {
            malta.recetaId = receta.id;
            if (malta.id) {
                this.recetaInsumoService.update(malta).subscribe(res => {
                    console.log('ok');
                });
            } else {
                this.recetaInsumoService.create(malta).subscribe(res => {
                    console.log('ok');
                });
            }
        });
        this.lupulosList.forEach(lupulo => {
            lupulo.recetaId = receta.id;
            if (lupulo.id) {
                this.recetaInsumoService.update(lupulo).subscribe(res => {
                    console.log('ok');
                });
            } else {
                this.recetaInsumoService.create(lupulo).subscribe(res => {
                    console.log('ok');
                });
            }
        });
        this.levadurasList.forEach(leva => {
            leva.recetaId = receta.id;
            if (leva.id) {
                this.recetaInsumoService.update(leva).subscribe(res => {
                    console.log('ok');
                });
            } else {
                this.recetaInsumoService.create(leva).subscribe(res => {
                    console.log('ok');
                });
            }
        });
        this.otrosList.forEach(otro => {
            otro.recetaId = receta.id;
            if (otro.id) {
                this.recetaInsumoService.update(otro).subscribe(res => {
                    console.log('ok');
                });
            } else {
                this.recetaInsumoService.create(otro).subscribe(res => {
                    console.log('ok');
                });
            }
        });
    }

    protected calculoAlcohol() {
        this.receta.abv = +Number(((this.receta.og - this.receta.fg) * 131) / 1000).toFixed(2);
    }

    protected calculoIbu() {
        let totalIbu = 0;
        this.lupulosList.forEach(lupulo => {
            const fc = 1 + (this.receta.og / 1000 - 1.05) / 0.2;
            if (this.receta.og > 1050) {
                const ibu = (lupulo.gramos * lupulo.alpha * this.getPlu(lupulo)) / (this.receta.batch * fc * 10);
                lupulo.ibu = +Number(ibu).toFixed(2);
                totalIbu = totalIbu + lupulo.ibu;
            } else {
                const ibu = (lupulo.gramos * lupulo.alpha * this.getPlu(lupulo)) / (this.receta.batch * 10);
                lupulo.ibu = +Number(ibu).toFixed(2);
                totalIbu = totalIbu + lupulo.ibu;
            }
        });
        this.receta.ibu = totalIbu;
    }

    private getPlu(lupulo: IRecetaInsumo) {
        switch (true) {
            case lupulo.tiempo >= 75: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 35;
                    break;
                } else {
                    return 27;
                    break;
                }
            }
            case lupulo.tiempo >= 60 && lupulo.tiempo <= 74: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 30;
                    break;
                } else {
                    return 24;
                    break;
                }
            }
            case lupulo.tiempo >= 45 && lupulo.tiempo <= 59: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 27;
                    break;
                } else {
                    return 22;
                    break;
                }
            }
            case lupulo.tiempo >= 30 && lupulo.tiempo <= 44: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 24;
                    break;
                } else {
                    return 19;
                    break;
                }
            }
            case lupulo.tiempo >= 20 && lupulo.tiempo <= 29: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 19;
                    break;
                } else {
                    return 15;
                    break;
                }
            }
            case lupulo.tiempo >= 10 && lupulo.tiempo <= 19: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 15;
                    break;
                } else {
                    return 12;
                    break;
                }
            }
            case lupulo.tiempo >= 0 && lupulo.tiempo <= 9: {
                if (lupulo.modoLupulo === ModoLupulo.PELLET) {
                    return 6;
                    break;
                } else {
                    return 5;
                    break;
                }
            }
            default: {
                console.log('Invalid choice');
                break;
            }
        }
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }

    addMalta() {
        this.insumoService.find(this.recetaInsumo.insumoId).subscribe(resp => {
            this.recetaInsumo.insumoNombreInsumo = resp.body.nombreInsumo;
            this.recetaInsumo.cantidad = parseFloat(this.recetaInsumo.cantidad.toString());
            this.recetaInsumo.tipoInsumo = resp.body.tipo;
            this.maltasList.push(this.recetaInsumo);
            this.calculatePercetage();
            this.dataSourceMalta = new MatTableDataSource<IRecetaInsumo>(this.maltasList);
            this.changeSrmRow();
            this.recetaInsumo = new RecetaInsumo();
        });
    }

    addLupulo() {
        this.insumoService.find(this.recetaInsumoLupulo.insumoId).subscribe(resp => {
            this.recetaInsumoLupulo.insumoNombreInsumo = resp.body.nombreInsumo;
            this.recetaInsumoLupulo.tipoInsumo = resp.body.tipo;
            this.lupulosList.push(this.recetaInsumoLupulo);
            this.dataSourceLupulo = new MatTableDataSource<IRecetaInsumo>(this.lupulosList);
            this.calculoIbu();
            this.recetaInsumoLupulo = new RecetaInsumo();
        });
    }

    addLevadura() {
        this.insumoService.find(this.recetaInsumoLeva.insumoId).subscribe(resp => {
            this.recetaInsumoLeva.insumoNombreInsumo = resp.body.nombreInsumo;
            this.recetaInsumoLeva.tipoInsumo = resp.body.tipo;
            this.levadurasList.push(this.recetaInsumoLeva);
            this.dataSourceLeva = new MatTableDataSource<IRecetaInsumo>(this.levadurasList);
            this.recetaInsumoLeva = new RecetaInsumo();
        });
    }

    addOtros() {
        this.insumoService.find(this.recetaInsumoOtro.insumoId).subscribe(resp => {
            this.recetaInsumoOtro.insumoNombreInsumo = resp.body.nombreInsumo;
            this.recetaInsumoOtro.tipoInsumo = resp.body.tipo;
            this.otrosList.push(this.recetaInsumoOtro);
            this.dataSourceOtro = new MatTableDataSource<IRecetaInsumo>(this.otrosList);
            this.recetaInsumoOtro = new RecetaInsumo();
        });
    }

    loadDataEdit() {
        this.recetaInsumoService.queryByInsumo(this.receta.id, TipoInsumo.MALTA).subscribe(malta => {
            this.maltasList = malta.body;
            this.dataSourceMalta = new MatTableDataSource<IRecetaInsumo>(this.maltasList);
        });
        this.recetaInsumoService.queryByInsumo(this.receta.id, TipoInsumo.LUPULO).subscribe(lupulo => {
            this.lupulosList = lupulo.body;

            this.dataSourceLupulo = new MatTableDataSource<IRecetaInsumo>(this.lupulosList);
        });
        this.recetaInsumoService.queryByInsumo(this.receta.id, TipoInsumo.LEVADURA).subscribe(leva => {
            this.levadurasList = leva.body;
            this.dataSourceLeva = new MatTableDataSource<IRecetaInsumo>(this.levadurasList);
        });
        this.recetaInsumoService
            .queryByInsumoNotIn(this.receta.id, { tipoInsumos: [TipoInsumo.LEVADURA, TipoInsumo.MALTA, TipoInsumo.LUPULO] })
            .subscribe(leva => {
                this.otrosList = leva.body;
                this.dataSourceOtro = new MatTableDataSource<IRecetaInsumo>(this.otrosList);
            });
    }
    changeSrmRow() {
        let mcbu = 0;
        this.maltasList.forEach(malta => {
            mcbu = mcbu + malta.cantidad * malta.color;
        });
        mcbu = +Number(mcbu / this.receta.batch).toFixed(2);
        const mcu = +Number(mcbu * (2.2 / 0.26)).toFixed(2);
        const srm = +Number(1.5 * Math.pow(mcu, 0.7)).toFixed(0);
        this.receta.srm = srm;
        this.calculatePercetage();
        this.dataSourceMalta = new MatTableDataSource<IRecetaInsumo>(this.maltasList);
    }

    calculatePercetage() {
        let suma = 0;
        this.maltasList.forEach(malta => {
            suma = suma + malta.cantidad;
        });

        this.maltasList.forEach(malta => {
            malta.porcentaje = +Number((malta.cantidad * 100) / suma).toFixed(2);
        });
    }

    changeIbuRow(recetaInsumo: IRecetaInsumo) {
        this.lupulosList = this.dataSourceLupulo.data;
        this.calculoIbu();
        this.dataSourceLupulo = new MatTableDataSource<IRecetaInsumo>(this.lupulosList);
        console.log(this.lupulosList);
    }

    randomName(recetaInsumo: IRecetaInsumo, val: any) {
        if (recetaInsumo.id) {
            return recetaInsumo.id + '-' + val;
        } else {
            let result = '';
            const characters = '0123456789';
            const charactersLength = characters.length;
            for (let i = 0; i < 5; i++) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
            }
            return result;
        }
    }

    setMyStyles() {
        return {
            'background-color': this.returnColor(),
            border: '0px solid ' + this.returnColor(),
            width: '38px'
        };
    }

    private returnColor() {
        const colorMap = {
            1: '#FFE699',
            2: '#FFD878',
            3: '#FFCA5A',
            4: '#FFBF42',
            5: '#FBB123',
            6: '#F8A600',
            7: '#F39C00',
            8: '#EA8F00',
            9: '#E58500',
            10: '#DE7C00',
            11: '#D77200',
            12: '#CF6900',
            13: '#CB6200',
            14: '#C35900',
            15: '#BB5100',
            16: '#B54C00',
            17: '#B04500',
            18: '#A63E00',
            19: '#A13700',
            20: '#9B3200',
            21: '#952D00',
            22: '#8E2900',
            23: '#882300',
            24: '#821E00',
            25: '#7B1A00',
            26: '#771900',
            27: '#701400',
            28: '#6A0E00',
            29: '#660D00',
            30: '#5E0B00',
            31: '#5A0A02',
            32: '#600903',
            33: '#520907',
            34: '#4C0505',
            35: '#470606',
            36: '#440607',
            37: '#361F1B',
            38: '#120D0C',
            39: '#100B0A',
            40: '#050B0A'
        };
        return colorMap[this.receta.srm];
    }

    removeMalta(malta: IRecetaInsumo) {
        this.maltasListRemove.push(malta);
        const newList: IRecetaInsumo[] = this.maltasList.filter(malt => malt !== malta);
        this.maltasList = newList;
        this.dataSourceMalta = new MatTableDataSource<IRecetaInsumo>(this.maltasList);
        this.changeSrmRow();
    }

    removeLupulo(malta: IRecetaInsumo) {
        this.lupulosListRemove.push(malta);
        const newList: IRecetaInsumo[] = this.lupulosList.filter(malt => malt !== malta);
        this.lupulosList = newList;
        this.dataSourceLupulo = new MatTableDataSource<IRecetaInsumo>(this.lupulosList);
        this.calculoIbu();
    }

    removeLevadura(malta: IRecetaInsumo) {
        this.levadurasListRemove.push(malta);
        const newList: IRecetaInsumo[] = this.levadurasList.filter(malt => malt !== malta);
        this.levadurasList = newList;
        this.dataSourceLeva = new MatTableDataSource<IRecetaInsumo>(this.levadurasList);
    }

    removeOtro(malta: IRecetaInsumo) {
        this.otrosListRemove.push(malta);
        const newList: IRecetaInsumo[] = this.otrosList.filter(malt => malt !== malta);
        this.otrosList = newList;
        this.dataSourceOtro = new MatTableDataSource<IRecetaInsumo>(this.otrosList);
    }

    protected removeInsumos() {
        const ids: number[] = [];
        this.maltasListRemove.forEach(insumo => {
            if (insumo.id) {
                ids.push(insumo.id);
            }
        });
        this.lupulosListRemove.forEach(insumo => {
            if (insumo.id) {
                ids.push(insumo.id);
            }
        });
        this.levadurasListRemove.forEach(insumo => {
            if (insumo.id) {
                ids.push(insumo.id);
            }
        });
        this.otrosListRemove.forEach(insumo => {
            if (insumo.id) {
                ids.push(insumo.id);
            }
        });
        if (ids.length > 0) {
            this.recetaInsumoService
                .deleteAll({
                    insumos: ids
                })
                .subscribe(resp => {
                    console.log('ok');
                });
        }
    }
}
