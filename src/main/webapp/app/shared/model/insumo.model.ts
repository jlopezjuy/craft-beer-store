import { Unidad } from 'app/shared/model/enumerations/unidad.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

export interface IInsumo {
  id?: number;
  nombreInsumo?: string;
  marca?: string;
  stock?: number;
  unidad?: Unidad;
  tipo?: TipoInsumo;
  imagenContentType?: string;
  imagen?: any;
  precioUnitario?: number;
  precioTotal?: number;
  empresaId?: number;
  insumoRecomendadoNombre?: string;
  insumoRecomendadoId?: number;
}

export class Insumo implements IInsumo {
  constructor(
    public id?: number,
    public nombreInsumo?: string,
    public marca?: string,
    public stock?: number,
    public unidad?: Unidad,
    public tipo?: TipoInsumo,
    public imagenContentType?: string,
    public imagen?: any,
    public precioUnitario?: number,
    public precioTotal?: number,
    public empresaId?: number,
    public insumoRecomendadoNombre?: string,
    public insumoRecomendadoId?: number
  ) {}
}
