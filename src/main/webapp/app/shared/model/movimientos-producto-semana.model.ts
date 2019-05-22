import { Moment } from 'moment';
import { TipoMovimiento } from 'app/shared/model/movimientos.model';

export interface IMovimientosProductoSemana {
    id?: number;
    tipoMovimiento?: TipoMovimiento;
    fechaMovimiento?: Moment;
    total?: number;
    cantidad?: number;
    nombreProducto?: string;
}

export class MovimientosProductoSemana implements IMovimientosProductoSemana {
    constructor(
        public id?: number,
        public tipoMovimiento?: TipoMovimiento,
        public fechaMovimiento?: Moment,
        public total?: number,
        public cantidad?: number,
        public nombreProducto?: string
    ) {}
}
