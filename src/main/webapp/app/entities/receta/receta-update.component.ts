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
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { DATE_FORMAT } from 'app/shared';
import { InsumoService } from 'app/entities/insumo';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { IInsumo, TipoInsumo } from 'app/shared/model/insumo.model';
import { IRecetaInsumo, ModoLupulo, RecetaInsumo } from 'app/shared/model/receta-insumo.model';
import { MatTableDataSource } from '@angular/material';
import { RecetaInsumoService } from 'app/entities/receta-insumo';

@Component({
    selector: 'jhi-receta-update',
    templateUrl: './receta-update.component.html'
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

    dataSourceMalta: any;
    displayedColumnsMalta: string[] = ['nombreMalta', 'cantidad', 'color', 'porcentaje', 'usoMalta'];
    displayedColumnsLupulo: string[] = ['nombreMalta', 'alpha', 'modoLupulo', 'gramos', 'usoLupulo', 'tiempo', 'ibu'];
    displayedColumnsLevadura: string[] = ['nombreMalta', 'gramos', 'densidadLeva', 'tamSobre', 'atenuacion'];
    displayedColumnsOtro: string[] = ['nombreMalta', 'gramos', 'tipoOtro', 'usoOtro', 'tiempoOtro'];

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
        private $localStorage: LocalStorageService,
        private ngxLoader: NgxUiLoaderService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receta }) => {
            this.receta = receta;
            if (this.receta.id) {
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
        this.ngxLoader.start();
        this.calculoIbu();
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
        this.saveRecetaInsumos(receta);
        this.ngxLoader.stop();
        this.previousState();
    }

    saveRecetaInsumos(receta: IReceta) {
        this.maltasList.forEach(malta => {
            malta.recetaId = receta.id;
            if (malta.id) {
                this.recetaInsumoService.update(malta).subscribe(res => {
                    console.log('');
                });
            } else {
                this.recetaInsumoService.create(malta).subscribe(res => {
                    console.log('');
                });
            }
        });
        this.lupulosList.forEach(lupulo => {
            lupulo.recetaId = receta.id;
            if (lupulo.id) {
                this.recetaInsumoService.update(lupulo).subscribe(res => {
                    console.log('');
                });
            } else {
                this.recetaInsumoService.create(lupulo).subscribe(res => {
                    console.log('');
                });
            }
        });
        this.levadurasList.forEach(leva => {
            leva.recetaId = receta.id;
            if (leva.id) {
                this.recetaInsumoService.update(leva).subscribe(res => {
                    console.log('');
                });
            } else {
                this.recetaInsumoService.create(leva).subscribe(res => {
                    console.log('');
                });
            }
        });
        this.otrosList.forEach(otro => {
            otro.recetaId = receta.id;
            if (otro.id) {
                this.recetaInsumoService.update(otro).subscribe(res => {
                    console.log('');
                });
            } else {
                this.recetaInsumoService.create(otro).subscribe(res => {
                    console.log('');
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
            console.log(lupulo);
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
            this.dataSourceMalta = new MatTableDataSource<IRecetaInsumo>(this.maltasList);
            this.recetaInsumo = new RecetaInsumo();
        });
    }

    addLupulo() {
        this.insumoService.find(this.recetaInsumoLupulo.insumoId).subscribe(resp => {
            this.recetaInsumoLupulo.insumoNombreInsumo = resp.body.nombreInsumo;
            // this.recetaInsumoLupulo.cantidad = parseFloat(this.recetaInsumoLupulo.cantidad.toString());
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
            // this.recetaInsumoLeva.cantidad = parseFloat(this.recetaInsumoLeva.cantidad.toString());
            this.recetaInsumoLeva.tipoInsumo = resp.body.tipo;
            this.levadurasList.push(this.recetaInsumoLeva);
            this.dataSourceLeva = new MatTableDataSource<IRecetaInsumo>(this.levadurasList);
            this.recetaInsumoLeva = new RecetaInsumo();
        });
    }

    addOtros() {
        this.insumoService.find(this.recetaInsumoOtro.insumoId).subscribe(resp => {
            this.recetaInsumoOtro.insumoNombreInsumo = resp.body.nombreInsumo;
            // this.recetaInsumoOtro.cantidad = parseFloat(this.recetaInsumoOtro.cantidad.toString());
            this.recetaInsumoOtro.tipoInsumo = resp.body.tipo;
            this.otrosList.push(this.recetaInsumoOtro);
            this.dataSourceOtro = new MatTableDataSource<IRecetaInsumo>(this.otrosList);
            this.recetaInsumoOtro = new RecetaInsumo();
        });
    }

    loadDataEdit() {
        console.log('entro a cargar insumos');
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
}
