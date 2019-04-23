import { Moment } from 'moment';

export const enum TipoMovimientoCaja {
    INGRESO = 'INGRESO',
    EGRESO = 'EGRESO'
}

export const enum TipoPago {
    EFECTIVO = 'EFECTIVO',
    TARJETA_CREDITO = 'TARJETA_CREDITO',
    TARJETA_DEBITO = 'TARJETA_DEBITO',
    CHEQUE = 'CHEQUE'
}

export interface ICaja {
    id?: number;
    tipoMovimiento?: TipoMovimientoCaja;
    tipoPago?: TipoPago;
    descripcion?: any;
    saldoCtaCte?: number;
    importe?: number;
    fecha?: Moment;
    proveedorNombreProveedor?: string;
    proveedorId?: number;
    clienteNombeApellido?: string;
    clienteId?: number;
    empresaId?: number;
}

export class Caja implements ICaja {
    constructor(
        public id?: number,
        public tipoMovimiento?: TipoMovimientoCaja,
        public tipoPago?: TipoPago,
        public descripcion?: any,
        public saldoCtaCte?: number,
        public importe?: number,
        public fecha?: Moment,
        public proveedorNombreProveedor?: string,
        public proveedorId?: number,
        public clienteNombeApellido?: string,
        public clienteId?: number,
        public empresaId?: number
    ) {}
}
