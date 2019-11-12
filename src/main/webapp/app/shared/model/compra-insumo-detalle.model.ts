export const enum Unidad {
  KILOGRAMO = 'KILOGRAMO',
  GRAMO = 'GRAMO',
  VEINTICINCO_KILOS = 'VEINTICINCO_KILOS',
  DIES_KILOS = 'DIES_KILOS',
  LITRO = 'LITRO',
  CM3 = 'CM3',
  ML = 'ML',
  UNIDAD = 'UNIDAD'
}

export const enum TipoInsumo {
  MALTA = 'MALTA',
  LUPULO = 'LUPULO',
  LEVADURA = 'LEVADURA',
  ACIDO = 'ACIDO',
  CLARIFICANTE = 'CLARIFICANTE',
  SAL = 'SAL',
  OTROS = 'OTROS'
}

export interface ICompraInsumoDetalle {
  id?: number;
  unidad?: Unidad;
  codigoReferencia?: string;
  stock?: number;
  precio?: number;
  tipo?: TipoInsumo;
  compraInsumoNroFactura?: string;
  compraInsumoId?: number;
  insumoRecomendadoNombre?: string;
  insumoRecomendadoId?: number;
  precioUnitario?: number;
}

export class CompraInsumoDetalle implements ICompraInsumoDetalle {
  constructor(
    public id?: number,
    public unidad?: Unidad,
    public codigoReferencia?: string,
    public stock?: number,
    public precio?: number,
    public tipo?: TipoInsumo,
    public compraInsumoNroFactura?: string,
    public compraInsumoId?: number,
    public insumoRecomendadoNombre?: string,
    public insumoRecomendadoId?: number,
    public precioUnitario?: number
  ) {}
}
