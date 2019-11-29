import { LitrosBarril } from 'app/shared/model/enumerations/litros-barril.model';
import { ConectorBarril } from 'app/shared/model/enumerations/conector-barril.model';
import { EstadoBarril } from 'app/shared/model/enumerations/estado-barril.model';

export interface IBarril {
  id?: number;
  codigo?: string;
  litros?: LitrosBarril;
  conector?: ConectorBarril;
  estado?: EstadoBarril;
  imagenContentType?: string;
  imagen?: any;
  empresaNombreEmpresa?: string;
  empresaId?: number;
  loteCodigo?: string;
  loteId?: number;
  clienteNombreApellido?: string;
  clienteId?: number;
}

export class Barril implements IBarril {
  constructor(
    public id?: number,
    public codigo?: string,
    public litros?: LitrosBarril,
    public conector?: ConectorBarril,
    public estado?: EstadoBarril,
    public imagenContentType?: string,
    public imagen?: any,
    public empresaNombreEmpresa?: string,
    public empresaId?: number,
    public loteCodigo?: string,
    public loteId?: number,
    public clienteNombreApellido?: string,
    public clienteId?: number
  ) {}
}
