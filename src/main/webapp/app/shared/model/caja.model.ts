import { Moment } from 'moment';
import { TipoMovimientoCaja } from 'app/shared/model/enumerations/tipo-movimiento-caja.model';
import { TipoPago } from 'app/shared/model/enumerations/tipo-pago.model';

export interface ICaja {
  id?: number;
  tipoMovimiento?: TipoMovimientoCaja;
  tipoPago?: TipoPago;
  descripcion?: any;
  saldoCtaCte?: number;
  importe?: number;
  fecha?: Moment;
  proveedorNombreProveedor?: string;
  proveedorId?: number;
  clienteNombreApellido?: string;
  clienteId?: number;
  empresaId?: number;
}

export class Caja implements ICaja {
  constructor(
    public id?: number,
    public tipoMovimiento?: TipoMovimientoCaja,
    public tipoPago?: TipoPago,
    public descripcion?: any,
    public saldoCtaCte?: number,
    public importe?: number,
    public fecha?: Moment,
    public proveedorNombreProveedor?: string,
    public proveedorId?: number,
    public clienteNombreApellido?: string,
    public clienteId?: number,
    public empresaId?: number
  ) {}
}
