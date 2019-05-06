import { Moment } from 'moment';
import { TipoMovimiento } from 'app/shared/model/movimientos.model';

export interface IMovimientosSemana {
    id?: number;
    tipoMovimiento?: TipoMovimiento;
    fechaMovimiento?: Moment;
    total?: number;
}

export class MovimientosSemana implements IMovimientosSemana {
    constructor(public id?: number, public tipoMovimiento?: TipoMovimiento, public fechaMovimiento?: Moment, public total?: number) {}
}
