import { Moment } from 'moment';
import { TipoMovimiento } from 'app/shared/model/movimientos.model';

export interface IMovimientoLitroModel {
  fechaMovimiento?: Moment;
  litroTotal?: number;
}

export class MovimientoLitroModel implements IMovimientoLitroModel {
  constructor(public fechaMovimiento?: Moment, public litroTotal?: number) {}
}
