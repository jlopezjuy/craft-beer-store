export interface IDetalleMovimiento {
    id?: number;
    cantidad?: number;
    precioTotal?: number;
    presentacionId?: number;
    movimientosId?: number;
}

export class DetalleMovimiento implements IDetalleMovimiento {
    constructor(
        public id?: number,
        public cantidad?: number,
        public precioTotal?: number,
        public presentacionId?: number,
        public movimientosId?: number
    ) {}
}
