import { Moment } from 'moment';

export const enum EstadoUsoTanque {
  EN_USO = 'EN_USO',
  VACIO = 'VACIO'
}

export interface IMovimientoTanque {
  id?: number;
  fecha?: Moment;
  estado?: EstadoUsoTanque;
  dias?: number;
  tanqueNombre?: string;
  tanqueId?: number;
  productoNombreComercial?: string;
  productoId?: number;
  loteCodigo?: string;
  loteId?: number;
}

export class MovimientoTanque implements IMovimientoTanque {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public estado?: EstadoUsoTanque,
    public dias?: number,
    public tanqueNombre?: string,
    public tanqueId?: number,
    public productoNombreComercial?: string,
    public productoId?: number,
    public loteCodigo?: string,
    public loteId?: number
  ) {}
}
