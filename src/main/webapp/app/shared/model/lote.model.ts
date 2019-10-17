import { Moment } from 'moment';

export const enum EstadoLote {
  EN_PROCESO = 'EN_PROCESO',
  PLANIFICADO = 'PLANIFICADO',
  COCCION = 'COCCION',
  FINALIZADO = 'FINALIZADO',
  ANULADO = 'ANULADO',
  FERMENTACION = 'FERMENTACION',
  MADURACION = 'MADURACION',
  ENVASADO = 'ENVASADO'
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
  litrosDisponible?: number;
  recetaNombre?: string;
  recetaId?: number;
  empresaNombreEmpresa?: string;
  empresaId?: number;
  productoNombreComercial?: string;
  productoId?: number;
  tanqueNombre?: string;
  tanqueId?: number;
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
    public litrosDisponible?: number,
    public recetaNombre?: string,
    public recetaId?: number,
    public empresaNombreEmpresa?: string,
    public empresaId?: number,
    public productoNombreComercial?: string,
    public productoId?: number,
    public tanqueNombre?: string,
    public tanqueId?: number
  ) {
    this.descuentaStock = this.descuentaStock || false;
  }
}
