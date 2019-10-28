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
  public chartOptionsDensidad: any = {};
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
            const densidades = this.medicionesLote.filter(densidad => densidad.tipoMedicion === TipoMedicion.DENSIDAD);
            const temperaturas = this.medicionesLote.filter(temp => temp.tipoMedicion === TipoMedicion.TEMPERATURA);
            this.loadGraph(densidades, temperaturas, this.tanques);
          });
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadGraph(densidades: IMedicionLote[], temperaturas: IMedicionLote[], tanques: ITanque[]) {
    console.log(densidades);
    console.log(temperaturas);
    const colorsDensidades = [];
    const valorDensidades = [];
    let fechas = [];
    const series = [];
    tanques.forEach(tanque => {
      const serieDensidad = [];
      valorDensidades.push(tanque.nombre);
      colorsDensidades.push(this.random_rgba());
      densidades.forEach(densidad => {
        if (tanque.nombre === densidad.tanqueNombre) {
          serieDensidad.push(densidad.valor);
          fechas.push(moment(densidad.fechaRealizado).format('DD/MM/YYYY'));
        }
      });

      // let serie = {
      //   name: tanque.nombre,
      //   type: 'line',
      //   stack: 'counts',
      //   areaStyle: { normal: {} },
      //   data: serieDensidad
      // }
      let dataSet = {
        label: tanque.nombre,
        data: serieDensidad,
        fill: false,
        borderColor: this.random_rgba()
      };
      series.push(dataSet);
    });
    fechas = fechas.filter(function(item, pos) {
      return fechas.indexOf(item) == pos;
    });
    console.log(fechas);
    console.log(series);
    this.chartOptionsDensidad = {
      labels: fechas,
      datasets: series
    };
    // this.chartOptionsDensidad = {
    //   color: colorsDensidades,
    //   tooltip: {
    //     trigger: 'axis',
    //     axisPointer: {
    //       type: 'cross',
    //       label: {
    //         backgroundColor: '#6a7985'
    //       }
    //     }
    //   },
    //   legend: {
    //     data: valorDensidades,
    //     textStyle: {
    //       color: '#8bc28d'
    //     }
    //   },
    //   grid: {
    //     left: '3%',
    //     right: '4%',
    //     bottom: '3%',
    //     containLabel: true
    //   },
    //   xAxis: [
    //     {
    //       type: 'category',
    //       boundaryGap: false,
    //       data: fechas,
    //       axisLine: {
    //         lineStyle: {
    //           color: '#C2C2C2'
    //         }
    //       },
    //       axisLabel: {
    //         textStyle: {
    //           color: '#C2C2C2'
    //         }
    //       }
    //     }
    //   ],
    //   yAxis: [
    //     {
    //       type: 'value',
    //       splitLine: {
    //         show: false
    //       },
    //       axisLine: {
    //         lineStyle: {
    //           color: '#C2C2C2'
    //         }
    //       },
    //       axisLabel: {
    //         textStyle: {
    //           color: '#C2C2C2'
    //         }
    //       }
    //     }
    //   ],
    //   series: series
    // };
    this.chartOptionsTemperatura = {
      color: [this.random_rgba(), this.random_rgba(), this.random_rgba(), this.random_rgba(), this.random_rgba()],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: ['Email marketing', 'Alliance advertising', 'Video ad', 'Direct interview', 'Search engine'],
        textStyle: {
          color: '#C2C2C2'
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: ['Monday', 'Tuesday', 'Wednessday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
          axisLine: {
            lineStyle: {
              color: '#C2C2C2'
            }
          },
          axisLabel: {
            textStyle: {
              color: '#C2C2C2'
            }
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#C2C2C2'
            }
          },
          axisLabel: {
            textStyle: {
              color: '#C2C2C2'
            }
          }
        }
      ],
      series: [
        {
          name: 'Email marketing',
          type: 'line',
          stack: 'Total amount',
          areaStyle: {},
          data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
          name: 'Alliance advertising',
          type: 'line',
          stack: 'Total amount',
          areaStyle: {},
          data: [220, 182, 191, 234, 290, 330, 310]
        },
        {
          name: 'Video ad',
          type: 'line',
          stack: 'Total amount',
          areaStyle: {},
          data: [150, 232, 201, 154, 190, 330, 410]
        },
        {
          name: 'Direct interview',
          type: 'line',
          stack: 'Total amount',
          areaStyle: { normal: {} },
          data: [320, 332, 301, 334, 390, 330, 320]
        },
        {
          name: 'Search engine',
          type: 'line',
          stack: 'Total amount',
          label: {
            normal: {
              show: true,
              position: 'top'
            }
          },
          areaStyle: { normal: {} },
          data: [820, 932, 901, 934, 1290, 1330, 1320]
        }
      ]
    };
  }

  saveMedicion() {
    const hora = Number(this.horaRealizado.toString().substr(0, 2));
    const minutos = Number(this.horaRealizado.toString().substr(3, 2));
    const fechaNueva = this.fechaRealizado != null ? moment(this.fechaRealizado, DATE_TIME_FORMAT) : null;
    const fechaFormateada = moment(fechaNueva).toDate();
    fechaFormateada.setMinutes(minutos);
    fechaFormateada.setHours(hora);
    console.log(fechaFormateada);
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
    this.medicionLoteService.create(medicionLoteDensidad).subscribe(resp => {
      console.log('save densidad ok');
      this.medicionLoteService.create(medicionLoteTemperatura).subscribe(temp => {
        console.log('save temp ok');
        this.medicionLote = new MedicionLote();
      });
    });
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
    const o = Math.round,
      r = Math.random,
      s = 255;
    return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + r().toFixed(1) + ')';
  }
}
