import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { EstadoLote, ILote } from 'app/shared/model/lote.model';
import { EtapaLoteService } from '../etapa-lote';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { EtapaLote, EtapaLoteEnum, IEtapaLote } from '../../shared/model/etapa-lote.model';
import { JhiAlertService } from 'ng-jhipster';
import { RecetaService } from '../receta';
import { IReceta } from '../../shared/model/receta.model';
import { TanqueService } from '../tanque';
import { EstadoTanque, ITanque } from '../../shared/model/tanque.model';
import { DATE_FORMAT } from '../../shared';
import { MatTableDataSource } from '@angular/material';
import { IRecetaInsumo, RecetaInsumo } from '../../shared/model/receta-insumo.model';
import { TipoInsumo } from '../../shared/model/insumo.model';
import { RecetaInsumoService } from '../receta-insumo';
import { LoteService } from './lote.service';
import { LoteDetailDialogComponent } from './lote-detail-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MovimientoTanqueService } from '../movimiento-tanque';
import { EstadoUsoTanque, MovimientoTanque } from '../../shared/model/movimiento-tanque.model';
import moment = require('moment');
import { IProducto } from '../../shared/model/producto.model';
import { ProductoService } from '../producto';

@Component({
  selector: 'jhi-lote-detail',
  templateUrl: './lote-detail.component.html'
})
export class LoteDetailComponent implements OnInit {
  lote: ILote;
  etapaLotes: IEtapaLote[];
  receta: IReceta;
  etapaLote: IEtapaLote;
  tanques: ITanque[];
  dataSource: any;
  displayedColumns: string[] = ['etapa', 'tanqueNombre', 'litros', 'inicio', 'fin', 'dias'];
  producto: IProducto;

  recetaInsumo: IRecetaInsumo = new RecetaInsumo();
  recetaInsumoLupulo: IRecetaInsumo = new RecetaInsumo();
  recetaInsumoLeva: IRecetaInsumo = new RecetaInsumo();
  recetaInsumoOtro: IRecetaInsumo = new RecetaInsumo();
  maltasList: IRecetaInsumo[] = [];
  lupulosList: IRecetaInsumo[] = [];
  levadurasList: IRecetaInsumo[] = [];
  otrosList: IRecetaInsumo[] = [];
  displayedColumnsMalta: string[] = ['nombreMalta', 'cantidad', 'color', 'porcentaje', 'usoMalta'];
  displayedColumnsLupulo: string[] = ['nombreMalta', 'alpha', 'modoLupulo', 'gramos', 'usoLupulo', 'tiempo', 'ibu'];
  displayedColumnsLevadura: string[] = ['nombreMalta', 'gramos', 'densidadLeva', 'tamSobre', 'atenuacion'];
  displayedColumnsOtro: string[] = ['nombreMalta', 'gramos', 'tipoOtro', 'usoOtro', 'tiempoOtro'];
  dataSourceMalta: any;
  dataSourceLupulo: any;
  dataSourceLeva: any;
  dataSourceOtro: any;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected etapaLoteService: EtapaLoteService,
    protected recetaService: RecetaService,
    protected jhiAlertService: JhiAlertService,
    protected tanqueService: TanqueService,
    protected recetaInsumoService: RecetaInsumoService,
    protected loteService: LoteService,
    public dialog: MatDialog,
    protected movimientoTanqueService: MovimientoTanqueService,
    protected productoService: ProductoService
  ) {}

  ngOnInit() {
    this.etapaLote = new EtapaLote();
    this.etapaLotes = [];
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      if (this.lote.id) {
        this.loadAll();
        this.productoService.find(this.lote.productoId).subscribe(prod => {
          this.producto = prod.body;
        });
      }
    });
  }

  loadAll() {
    console.log(this.lote);
    this.loadEtapa();
    this.loadTanque();
  }

  loadEtapa() {
    this.etapaLoteService
      .queryByLote(null, this.lote.id)
      .subscribe(
        (res: HttpResponse<IEtapaLote[]>) => this.paginateEtapaLotes(res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.recetaService.find(this.lote.recetaId).subscribe(response => {
      this.receta = response.body;
      this.loadDataEdit();
    });
  }

  loadTanque() {
    if (this.lote.estado !== EstadoLote.COCCION && this.lote.estado !== EstadoLote.PLANIFICADO) {
      this.tanqueService.queryByEmpresaLote(null, this.lote.empresaId, this.lote.id).subscribe(respo => {
        this.tanques = respo.body;
        console.log(this.tanques);
      });
    } else {
      this.tanqueService.queryByEmpresaEstadoVacio(null, this.lote.empresaId, EstadoTanque.VACIO).subscribe(resp => {
        this.tanques = resp.body;
        console.log(this.tanques);
      });
    }
  }

  protected paginateEtapaLotes(data: IEtapaLote[]) {
    this.etapaLotes = data;
    this.dataSource = new MatTableDataSource<IEtapaLote>(this.etapaLotes);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  saveEtapa() {
    this.etapaLote.inicio = moment(new Date(), DATE_FORMAT);
    this.etapaLote.loteId = this.lote.id;

    this.etapaLoteService.findTop(this.lote.id).subscribe(etapaTop => {
      const etapa: IEtapaLote = etapaTop.body;
      etapa.fin = moment(new Date(), DATE_FORMAT);
      etapa.dias = etapa.fin.diff(etapa.inicio, 'days');
      this.etapaLoteService.update(etapa).subscribe(upd => {
        console.log('etapa fecha actualizada');
      });
    });

    if (this.etapaLote.etapa === EtapaLoteEnum.FERMENTACION) {
      this.lote.estado = EstadoLote.FERMENTACION;
    }
    if (this.etapaLote.etapa === EtapaLoteEnum.MADURACION) {
      this.lote.estado = EstadoLote.MADURACION;
    }
    if (this.etapaLote.etapa === EtapaLoteEnum.ENVASADO) {
      this.lote.estado = EstadoLote.ENVASADO;
    }

    this.lote.litrosEnTanque = this.etapaLote.litros;

    this.loteService.update(this.lote).subscribe(lot => {
      console.log('lote actualizado');
    });

    const movimiento = new MovimientoTanque();
    movimiento.estado = EstadoUsoTanque.EN_USO;
    movimiento.loteId = this.lote.id;
    movimiento.tanqueId = this.etapaLote.tanqueId;
    movimiento.productoId = this.lote.productoId;
    movimiento.fecha = moment(new Date(), DATE_FORMAT);
    this.movimientoTanqueService.create(movimiento).subscribe(mov => {
      console.log('movimiento tanque creado');
    });

    this.etapaLoteService.create(this.etapaLote).subscribe(resp => {
      console.log('etapa lote creado ok');
      this.tanqueService.find(this.etapaLote.tanqueId).subscribe(tanque => {
        resp.body.tanqueNombre = tanque.body.nombre;
        this.etapaLotes.push(resp.body);
        this.etapaLote = new EtapaLote();
        this.dataSource = new MatTableDataSource<IEtapaLote>(this.etapaLotes);
        tanque.body.estado = EstadoTanque.EN_USO;
        tanque.body.loteId = this.lote.id;
        tanque.body.productoId = this.lote.productoId;
        this.tanqueService.update(tanque.body).subscribe(tan => {
          console.log('tanque actualizado');
          // console.log(tan);
          // this.loadAll();
          this.loadEtapa();
        });
      });
    });
    console.log(this.etapaLotes);
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

  save() {
    this.loteService.update(this.lote).subscribe(resp => {
      console.log();
    });
  }

  previousState() {
    window.history.back();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(LoteDetailDialogComponent, {
      width: '450px',
      data: this.lote
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      if (result !== undefined) {
        console.log('Confirmo accion');
        this.lote.estado = EstadoLote.COCCION;
        this.save();
      }
      // this.animal = result;
    });
  }

  finalizar() {
    this.etapaLoteService.findTop(this.lote.id).subscribe(etapaTop => {
      const etapa: IEtapaLote = etapaTop.body;
      etapa.fin = moment(new Date(), DATE_FORMAT);
      etapa.dias = etapa.fin.diff(etapa.inicio, 'days');
      this.etapaLoteService.update(etapa).subscribe(upd => {
        console.log('etapa fecha actualizada');
        const movimiento = new MovimientoTanque();
        movimiento.estado = EstadoUsoTanque.VACIO;
        movimiento.loteId = this.lote.id;
        movimiento.tanqueId = upd.body.tanqueId;
        movimiento.productoId = this.lote.productoId;
        movimiento.fecha = moment(new Date(), DATE_FORMAT);
        this.movimientoTanqueService.create(movimiento).subscribe(mov => {
          console.log('movimiento tanque creado');
        });

        this.lote.estado = EstadoLote.FINALIZADO;
        this.loteService.update(this.lote).subscribe(lot => {
          console.log('lote actualizado');
        });

        this.tanqueService.queryByEmpresaLote(null, this.lote.empresaId, this.lote.id).subscribe(resp => {
          resp.body.forEach(tanque => {
            tanque.productoId = null;
            tanque.loteId = null;
            tanque.estado = EstadoTanque.VACIO;
            this.tanqueService.update(tanque).subscribe(updt => {
              console.log('tanque actualizado');
            });
          });
        });
      });
    });
  }
}
