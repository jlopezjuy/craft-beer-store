import { Moment } from 'moment';

export interface ICompraInsumo {
  id?: number;
  nroFactura?: string;
  fecha?: Moment;
  subtotal?: number;
  gastoDeEnvio?: number;
  impuesto?: number;
  total?: number;
  proveedorNombreProveedor?: string;
  proveedorId?: number;
}

export class CompraInsumo implements ICompraInsumo {
  constructor(
    public id?: number,
    public nroFactura?: string,
    public fecha?: Moment,
    public subtotal?: number,
    public gastoDeEnvio?: number,
    public impuesto?: number,
    public total?: number,
    public proveedorNombreProveedor?: string,
    public proveedorId?: number
  ) {}
}
