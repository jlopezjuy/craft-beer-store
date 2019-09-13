import { Moment } from 'moment';

export const enum TipoTanque {
  UNITANK_INOX = 'UNITANK_INOX',
  FERMENTADOR = 'FERMENTADOR',
  MADURADOR = 'MADURADOR',
  BARRICA = 'BARRICA'
}

export const enum EstadoTanque {
  VACIO = 'VACIO',
  EN_USO = 'EN_USO'
}

export interface ITanque {
  id?: number;
  nombre?: string;
  litros?: number;
  tipo?: TipoTanque;
  estado?: EstadoTanque;
  listrosDisponible?: number;
  fechaIngreso?: Moment;
  loteCodigo?: string;
  loteId?: number;
  productoNombreComercial?: string;
  productoId?: number;
  empresaNombreEmpresa?: string;
  empresaId?: number;
}

export class Tanque implements ITanque {
  constructor(
    public id?: number,
    public nombre?: string,
    public litros?: number,
    public tipo?: TipoTanque,
    public estado?: EstadoTanque,
    public listrosDisponible?: number,
    public fechaIngreso?: Moment,
    public loteCodigo?: string,
    public loteId?: number,
    public productoNombreComercial?: string,
    public productoId?: number,
    public empresaNombreEmpresa?: string,
    public empresaId?: number
  ) {}
}
