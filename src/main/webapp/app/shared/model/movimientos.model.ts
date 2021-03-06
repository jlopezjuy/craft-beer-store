import { Moment } from 'moment';

export const enum TipoMovimiento {
    PRESUPUESTO = 'PRESUPUESTO',
    VENTA = 'VENTA'
}

export const enum EstadoMovimiento {
    ACTIVO = 'ACTIVO',
    INACTIVO = 'INACTIVO',
    ENTREGADO = 'ENTREGADO'
}

export interface IMovimientos {
    id?: number;
    tipoMovimiento?: TipoMovimiento;
    fechaMovimiento?: Moment;
    precioTotal?: number;
    numeroMovimiento?: string;
    estado?: EstadoMovimiento;
    litrosTotales?: number;
    clienteNombreApellido?: string;
    clienteId?: number;
    empresaNombreEmpresa?: string;
    empresaId?: number;
    puntoDeVentaId?: number;
}

export class Movimientos implements IMovimientos {
    constructor(
        public id?: number,
        public tipoMovimiento?: TipoMovimiento,
        public fechaMovimiento?: Moment,
        public precioTotal?: number,
        public numeroMovimiento?: string,
        public estado?: EstadoMovimiento,
        public litrosTotales?: number,
        public clienteNombreApellido?: string,
        public clienteId?: number,
        public empresaNombreEmpresa?: string,
        public empresaId?: number,
        public puntoDeVentaId?: number
    ) {}
}
