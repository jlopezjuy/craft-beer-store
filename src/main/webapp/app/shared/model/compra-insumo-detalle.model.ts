export const enum Unidad {
  KILOGRAMO = 'KILOGRAMO',
  GRAMO = 'GRAMO',
  VEINTICINCO_KILOS = 'VEINTICINCO_KILOS',
  DIES_KILOS = 'DIES_KILOS',
  LITRO = 'LITRO',
  CM3 = 'CM3',
  ML = 'ML',
  UNIDAD = 'UNIDAD',
  SOBRE_11G = 'SOBRE_11G'
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
  precioUnitario?: number;
  precioTotal?: number;
  tipo?: TipoInsumo;
  compraInsumoNroFactura?: string;
  compraInsumoId?: number;
  insumoRecomendadoNombre?: string;
  insumoRecomendadoId?: number;
}

export class CompraInsumoDetalle implements ICompraInsumoDetalle {
  constructor(
    public id?: number,
    public unidad?: Unidad,
    public codigoReferencia?: string,
    public stock?: number,
    public precioUnitario?: number,
    public precioTotal?: number,
    public tipo?: TipoInsumo,
    public compraInsumoNroFactura?: string,
    public compraInsumoId?: number,
    public insumoRecomendadoNombre?: string,
    public insumoRecomendadoId?: number
  ) {}
}
