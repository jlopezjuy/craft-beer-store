import { Moment } from 'moment';
import { EstadoMovimientoBarril } from 'app/shared/model/enumerations/estado-movimiento-barril.model';

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
