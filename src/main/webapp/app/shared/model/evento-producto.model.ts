export interface IEventoProducto {
    id?: number;
    productoNombreProducto?: string;
    productoId?: number;
    eventoId?: number;
}

export class EventoProducto implements IEventoProducto {
    constructor(public id?: number, public productoNombreProducto?: string, public productoId?: number, public eventoId?: number) {}
}
