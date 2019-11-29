import { Unidad } from 'app/shared/model/enumerations/unidad.model';
import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';

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
