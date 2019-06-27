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

export interface ICompraInsumoDetalle {
  id?: number;
  unidad?: Unidad;
  codigoReferencia?: string;
  stock?: number;
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
    public compraInsumoNroFactura?: string,
    public compraInsumoId?: number,
    public insumoRecomendadoNombre?: string,
    public insumoRecomendadoId?: number
  ) {}
}
