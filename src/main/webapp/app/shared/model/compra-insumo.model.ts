import { Moment } from 'moment';

export const enum EstadoCompra {
  PEDIDO_REALIZADO = 'PEDIDO_REALIZADO',
  PEDIDO_RECIBIDO = 'PEDIDO_RECIBIDO'
}

export interface ICompraInsumo {
  id?: number;
  nroFactura?: string;
  fecha?: Moment;
  subtotal?: number;
  gastoDeEnvio?: number;
  impuesto?: number;
  total?: number;
  estadoCompra?: EstadoCompra;
  proveedorNombreProveedor?: string;
  proveedorId?: number;
  empresaNombreEmpresa?: string;
  empresaId?: number;
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
    public estadoCompra?: EstadoCompra,
    public proveedorNombreProveedor?: string,
    public proveedorId?: number,
    public empresaNombreEmpresa?: string,
    public empresaId?: number
  ) {}
}
