import { Moment } from 'moment';

export const enum TipoMedicion {
  DENSIDAD = 'DENSIDAD',
  TEMPERATURA = 'TEMPERATURA'
}

export interface IMedicionLote {
  id?: number;
  dia?: number;
  tipoMedicion?: TipoMedicion;
  estado?: string;
  fechaRealizado?: Moment;
  valor?: number;
  observacion?: string;
  loteCodigo?: string;
  loteId?: number;
  tanqueNombre?: string;
  tanqueId?: number;
}

export class MedicionLote implements IMedicionLote {
  constructor(
    public id?: number,
    public dia?: number,
    public tipoMedicion?: TipoMedicion,
    public estado?: string,
    public fechaRealizado?: Moment,
    public valor?: number,
    public observacion?: string,
    public loteCodigo?: string,
    public loteId?: number,
    public tanqueNombre?: string,
    public tanqueId?: number
  ) {}
}
