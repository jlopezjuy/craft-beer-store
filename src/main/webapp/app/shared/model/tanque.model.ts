import { Moment } from 'moment';
import { TipoTanque } from 'app/shared/model/enumerations/tipo-tanque.model';
import { EstadoTanque } from 'app/shared/model/enumerations/estado-tanque.model';

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
