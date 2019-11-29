import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

export interface IInsumoRecomendado {
  id?: number;
  nombre?: string;
  marca?: string;
  tipo?: TipoInsumo;
}

export class InsumoRecomendado implements IInsumoRecomendado {
  constructor(public id?: number, public nombre?: string, public marca?: string, public tipo?: TipoInsumo) {}
}
