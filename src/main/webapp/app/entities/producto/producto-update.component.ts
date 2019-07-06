import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map, startWith } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/account/settings/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { IEstilos } from 'app/shared/model/estilos.model';
import { EstilosService } from 'app/entities/estilos';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html',
  styleUrls: ['producto-update.component.scss']
})
export class ProductoUpdateComponent implements OnInit {
  producto: IProducto;
  isSaving: boolean;

  empresa: IEmpresa;

  estilos: IEstilos[];
  estilosAutocomplete: IEstilos[];

  myControl = new FormControl();
  filteredOptions: Observable<IEstilos[]>;

  initialValues = '';

  colores: any;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected empresaService: EmpresaService,
    protected estilosService: EstilosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private $localStorage: LocalStorageService
  ) {}

  ngOnInit() {
    this.isSaving = false;

    this.empresa = this.$localStorage.retrieve('empresa');
    this.estilosService
      .queryAll()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstilos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstilos[]>) => response.body)
      )
      .subscribe(
        (res: IEstilos[]) => {
          this.estilos = res;
          this.loadChange();
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.producto = producto;
      if (this.producto.id) {
        this.loadEstilo(this.producto);
      } else {
        this.initialValues = '';
      }
    });
    this.loadColores();
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
    this.dataUtils.setFileData(event, entity, field, isImage);
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.producto.empresaId = this.empresa.id;
    this.producto.estilosId = this.estilosAutocomplete.pop().id;
    this.isSaving = true;
    if (this.producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(this.producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(this.producto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackEstilosById(index: number, item: IEstilos) {
    return item.id;
  }

  private filter(value: string): IEstilos[] {
    const filterValue = value.toLowerCase();
    const estilo = this.estilos.filter(option => {
      const selec = option.nombreEstilo.toLowerCase().includes(filterValue);
      return selec;
    });
    this.estilosAutocomplete = [];
    this.estilosAutocomplete = estilo;
    return estilo;
  }

  private loadEstilo(producto: IProducto) {
    this.estilosService.find(producto.estilosId).subscribe(estilo => {
      this.initialValues = estilo.body.nombreEstilo;
      this.myControl.reset(estilo.body.nombreEstilo);
      // this.loadChange();
    });
  }

  loadChange() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(this.initialValues),
      map(value => {
        return this.filter(value);
      })
    );
  }

  setMyStyles(color: string) {
    return {
      'background-color': color,
      border: '0px solid ' + color,
      width: '20px',
      height: '36px'
    };
  }

  loadColores() {
    const colorMap = [
      { id: 1, name: '#FFE699' },
      { id: 2, name: '#FFD878' },
      { id: 3, name: '#FFCA5A' },
      { id: 4, name: '#FFBF42' },
      { id: 5, name: '#FBB123' },
      { id: 6, name: '#F8A600' },
      { id: 7, name: '#F39C00' },
      { id: 8, name: '#EA8F00' },
      { id: 9, name: '#E58500' },
      { id: 10, name: '#DE7C00' },
      { id: 11, name: '#D77200' },
      { id: 12, name: '#CF6900' },
      { id: 13, name: '#CB6200' },
      { id: 14, name: '#C35900' },
      { id: 15, name: '#BB5100' },
      { id: 16, name: '#B54C00' },
      { id: 17, name: '#B04500' },
      { id: 18, name: '#A63E00' },
      { id: 19, name: '#A13700' },
      { id: 20, name: '#9B3200' },
      { id: 21, name: '#952D00' },
      { id: 22, name: '#8E2900' },
      { id: 23, name: '#882300' },
      { id: 24, name: '#821E00' },
      { id: 25, name: '#7B1A00' },
      { id: 26, name: '#771900' },
      { id: 27, name: '#701400' },
      { id: 28, name: '#6A0E00' },
      { id: 29, name: '#660D00' },
      { id: 30, name: '#5E0B00' },
      { id: 31, name: '#5A0A02' },
      { id: 32, name: '#600903' },
      { id: 33, name: '#520907' },
      { id: 34, name: '#4C0505' },
      { id: 35, name: '#470606' },
      { id: 36, name: '#440607' },
      { id: 37, name: '#361F1B' },
      { id: 38, name: '#120D0C' },
      { id: 39, name: '#100B0A' },
      { id: 40, name: '#050B0A' }
    ];

    this.colores = colorMap;
  }
}
