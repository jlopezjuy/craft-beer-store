import { Moment } from 'moment';
import { TipoPresentacion } from 'app/shared/model/enumerations/tipo-presentacion.model';

export interface IPresentacion {
  id?: number;
  tipoPresentacion?: TipoPresentacion;
  cantidad?: number;
  fecha?: Moment;
  costoUnitario?: number;
  precioVentaUnitario?: number;
  precioVentaTotal?: number;
  precioCostoTotal?: number;
  productoNombreComercial?: string;
  productoId?: number;
  loteCodigo?: string;
  loteId?: number;
}

export class Presentacion implements IPresentacion {
  constructor(
    public id?: number,
    public tipoPresentacion?: TipoPresentacion,
    public cantidad?: number,
    public fecha?: Moment,
    public costoUnitario?: number,
    public precioVentaUnitario?: number,
    public precioVentaTotal?: number,
    public precioCostoTotal?: number,
    public productoNombreComercial?: string,
    public productoId?: number,
    public loteCodigo?: string,
    public loteId?: number
  ) {}
}
