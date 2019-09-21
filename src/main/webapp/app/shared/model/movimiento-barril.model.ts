import { Moment } from 'moment';

export const enum EstadoMovimientoBarril {
  VACIO = 'VACIO',
  LLENO = 'LLENO',
  ENTREGADO = 'ENTREGADO',
  RETIRADO = 'RETIRADO',
  ROTO = 'ROTO'
}

export interface IMovimientoBarril {
  id?: number;
  fechaMovimiento?: Moment;
  estado?: EstadoMovimientoBarril;
  dias?: number;
  barrilCodigo?: string;
  barrilId?: number;
  productoNombreComercial?: string;
  productoId?: number;
  loteCodigo?: string;
  loteId?: number;
  clienteNombreApellido?: string;
  clienteId?: number;
}

export class MovimientoBarril implements IMovimientoBarril {
  constructor(
    public id?: number,
    public fechaMovimiento?: Moment,
    public estado?: EstadoMovimientoBarril,
    public dias?: number,
    public barrilCodigo?: string,
    public barrilId?: number,
    public productoNombreComercial?: string,
    public productoId?: number,
    public loteCodigo?: string,
    public loteId?: number,
    public clienteNombreApellido?: string,
    public clienteId?: number
  ) {}
}
