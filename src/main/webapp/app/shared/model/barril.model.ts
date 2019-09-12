export const enum LitrosBarril {
  CINCO = 'CINCO',
  DIEZ = 'DIEZ',
  QUINCE = 'QUINCE',
  DIECINUEVE = 'DIECINUEVE',
  VEINTE = 'VEINTE',
  VEINTICINCO = 'VEINTICINCO',
  TREINTA = 'TREINTA',
  CINCUENTA = 'CINCUENTA'
}

export const enum ConectorBarril {
  G = 'G',
  A = 'A',
  S = 'S',
  D = 'D',
  U = 'U',
  M = 'M',
  PIN_LOCK = 'PIN_LOCK',
  BALL_LOCK = 'BALL_LOCK'
}

export const enum EstadoBarril {
  VACIO = 'VACIO',
  LLENO = 'LLENO',
  ENTREGADO = 'ENTREGADO',
  RETIRADO = 'RETIRADO',
  LIMPIEZA = 'LIMPIEZA'
}

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
