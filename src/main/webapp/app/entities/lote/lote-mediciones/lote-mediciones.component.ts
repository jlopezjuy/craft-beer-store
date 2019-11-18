import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ILote } from '../../../shared/model/lote.model';
import { ActivatedRoute } from '@angular/router';
import { SidebarService } from '../../../services/sidebar.service';
import { MedicionLoteService } from '../../medicion-lote';
import { IMedicionLote, MedicionLote, TipoMedicion } from '../../../shared/model/medicion-lote.model';
import { ITanque } from '../../../shared/model/tanque.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { TanqueService } from '../../tanque';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto } from '../../../shared/model/producto.model';
import { ProductoService } from '../../producto';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from '../../../shared';

@Component({
  selector: 'jhi-lote-mediciones',
  templateUrl: './lote-mediciones.component.html',
  styles: []
})
export class LoteMedicionesComponent implements OnInit {
  public sidebarVisible = true;
  medicionesLote: IMedicionLote[] = [];
  medicionLote: IMedicionLote;
  lote: ILote;
  fechaRealizado: any;
  horaRealizado: any;
  tanques: ITanque[];
  producto: IProducto;
  public chartOptionsDensidad: any;
  public chartOptionsTemperatura: any = {};

  constructor(
    protected activatedRoute: ActivatedRoute,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef,
    private medicionLoteService: MedicionLoteService,
    protected tanqueService: TanqueService,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService
  ) {}

  ngOnInit() {
    this.fechaRealizado = moment(moment(), 'dd/MM/yyy').format();
    this.medicionLote = new MedicionLote();
    this.activatedRoute.data.subscribe(({ lote }) => {
      this.lote = lote;
      console.log(this.lote);
      if (this.lote.id) {
        this.loadAll();
        this.productoService.find(this.lote.productoId).subscribe(prod => {
          this.producto = prod.body;
        });
      }
    });
  }

  loadAll() {
    this.tanqueService
      .queryByEmpresaLote(null, this.lote.empresaId, this.lote.id)
      .pipe(
        filter((mayBeOk: HttpResponse<ITanque[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITanque[]>) => response.body)
      )
      .subscribe(
        (res: ITanque[]) => {
          this.tanques = res;
          this.medicionLoteService.queryLote(null, this.lote.id).subscribe(resp => {
            this.medicionesLote = resp.body;
            this.medicionLoteService.queryLoteTipo(null, this.lote.id, TipoMedicion.TEMPERATURA).subscribe(resp => {
              console.log(resp);
              this.loadGraphTemperatura(resp.body, res);
            });
            this.medicionLoteService.queryLoteTipo(null, this.lote.id, TipoMedicion.DENSIDAD).subscribe(response => {
              console.log(response);
              this.loadGraphDensidad(response.body, res);
            });
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadGraphDensidad(densidades: IMedicionLote[], tanques: ITanque[]) {
    let fechas = [];
    const series = [];
    densidades.forEach(dens => {
      fechas.push(moment.utc(dens.fechaRealizado).format('DD/MM/YYYY'));
    });
    fechas = fechas.filter(function(item, pos) {
      return fechas.indexOf(item) == pos;
    });
    tanques.forEach(tanque => {
      const data = [];
      densidades.forEach(densidad => {
        if (tanque.nombre === densidad.tanqueNombre) {
          data.push(densidad.valor);
        }
      });
      series.push({
        label: tanque.nombre,
        data: data,
        fill: false,
        borderColor: this.random_rgba()
      });
    });

    this.chartOptionsDensidad = {
      labels: fechas,
      datasets: series
    };
  }

  loadGraphTemperatura(temperaturas: IMedicionLote[], tanques: ITanque[]) {
    console.log(temperaturas);
    let fechas = [];
    const series = [];
    temperaturas.forEach(temp => {
      fechas.push(moment.utc(temp.fechaRealizado).format('DD/MM/YYYY'));
    });
    fechas = fechas.filter(function(item, pos) {
      return fechas.indexOf(item) == pos;
    });
    tanques.forEach(tanque => {
      const data = [];
      temperaturas.forEach(tempe => {
        if (tanque.nombre === tempe.tanqueNombre) {
          console.log(tempe.valor);
          data.push(tempe.valor);
        }
      });
      console.log(data);
      series.push({
        label: tanque.nombre,
        data: data,
        fill: false,
        borderColor: this.random_rgba()
      });
    });

    console.log(series);
    this.chartOptionsTemperatura = {
      labels: fechas,
      datasets: series
    };
  }

  saveMedicion() {
    const hora = Number(this.horaRealizado.toString().substr(0, 2));
    const minutos = Number(this.horaRealizado.toString().substr(3, 2));
    const fechaNueva = this.fechaRealizado != null ? moment(this.fechaRealizado, DATE_TIME_FORMAT) : null;
    const fechaFormateada = moment(fechaNueva).toDate();
    fechaFormateada.setMinutes(minutos);
    fechaFormateada.setHours(hora);
    const medicionLoteDensidad: IMedicionLote = new MedicionLote();
    const medicionLoteTemperatura: IMedicionLote = new MedicionLote();
    medicionLoteDensidad.fechaRealizado = moment(fechaFormateada, DATE_TIME_FORMAT);
    medicionLoteTemperatura.fechaRealizado = moment(fechaFormateada, DATE_TIME_FORMAT);
    medicionLoteDensidad.tanqueId = this.medicionLote.tanqueId;
    medicionLoteTemperatura.tanqueId = this.medicionLote.tanqueId;
    medicionLoteDensidad.valor = this.medicionLote.densidad;
    medicionLoteTemperatura.valor = this.medicionLote.temperatura;
    medicionLoteDensidad.observacion = this.medicionLote.observacion;
    medicionLoteTemperatura.observacion = this.medicionLote.observacion;
    medicionLoteDensidad.tipoMedicion = TipoMedicion.DENSIDAD;
    medicionLoteTemperatura.tipoMedicion = TipoMedicion.TEMPERATURA;
    medicionLoteDensidad.loteId = this.lote.id;
    medicionLoteTemperatura.loteId = this.lote.id;
    console.log(this.validateMedicion(this.medicionLote, fechaFormateada));
    if (this.validateMedicion(this.medicionLote, fechaFormateada) === true) {
      this.medicionLoteService.create(medicionLoteDensidad).subscribe(resp => {
        this.medicionLoteService.create(medicionLoteTemperatura).subscribe(temp => {
          this.medicionLote = new MedicionLote();
          this.loadAll();
        });
      });
    } else {
      this.jhiAlertService.warning('craftBeerStoreApp.medicionLote.exist');
    }
  }

  validateMedicion(medicionLote: IMedicionLote, fechaFormateada: any): boolean {
    let validate = true;
    this.medicionesLote.forEach(medicion => {
      const fechaSaved = moment(medicion.fechaRealizado).format('DD/MM/YYYY');
      const fechaToSave = moment(fechaFormateada).format('DD/MM/YYYY');
      if (fechaSaved === fechaToSave && medicion.tanqueId === medicionLote.tanqueId) {
        validate = false;
      }
    });
    return validate;
  }

  toggleFullWidth() {
    this.sidebarService.toggle();
    this.sidebarVisible = this.sidebarService.getStatus();
    this.cdr.detectChanges();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTanqueById(index: number, item: ITanque) {
    return item.id;
  }

  previousState() {
    window.history.back();
  }

  private random_rgba() {
    let length = 6;
    const chars = '0123456789ABCDEF';
    let hex = '#';
    while (length--) hex += chars[(Math.random() * 16) | 0];
    return hex;
  }
}
