import { Moment } from 'moment';
import { TipoEquipamiento } from 'app/shared/model/enumerations/tipo-equipamiento.model';

export interface IEquipamiento {
  id?: number;
  nombreEquipamiento?: string;
  tipoEquipamiento?: TipoEquipamiento;
  precio?: number;
  costoEnvio?: number;
  fechaCompra?: Moment;
  imagenContentType?: string;
  imagen?: any;
  empresaNombreEmpresa?: string;
  empresaId?: number;
}

export class Equipamiento implements IEquipamiento {
  constructor(
    public id?: number,
    public nombreEquipamiento?: string,
    public tipoEquipamiento?: TipoEquipamiento,
    public precio?: number,
    public costoEnvio?: number,
    public fechaCompra?: Moment,
    public imagenContentType?: string,
    public imagen?: any,
    public empresaNombreEmpresa?: string,
    public empresaId?: number
  ) {}
}
