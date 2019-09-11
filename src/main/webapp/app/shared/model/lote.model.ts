import { Moment } from 'moment';

export const enum EstadoLote {
  PLANIFICADO = 'PLANIFICADO',
  FINALIZADO = 'FINALIZADO',
  ANULADO = 'ANULADO',
  FERMENTACION = 'FERMENTACION',
  MADURACION = 'MADURACION'
}

export interface ILote {
  id?: number;
  codigo?: string;
  fechaCoccion?: Moment;
  coccion?: number;
  descripcion?: string;
  descuentaStock?: boolean;
  estado?: EstadoLote;
  litrosEstimados?: number;
  litrosEnTanque?: number;
  litrosEnvasados?: number;
  recetaNombre?: string;
  recetaId?: number;
  empresaNombreEmpresa?: string;
  empresaId?: number;
}

export class Lote implements ILote {
  constructor(
    public id?: number,
    public codigo?: string,
    public fechaCoccion?: Moment,
    public coccion?: number,
    public descripcion?: string,
    public descuentaStock?: boolean,
    public estado?: EstadoLote,
    public litrosEstimados?: number,
    public litrosEnTanque?: number,
    public litrosEnvasados?: number,
    public recetaNombre?: string,
    public recetaId?: number,
    public empresaNombreEmpresa?: string,
    public empresaId?: number
  ) {
    this.descuentaStock = this.descuentaStock || false;
  }
}
