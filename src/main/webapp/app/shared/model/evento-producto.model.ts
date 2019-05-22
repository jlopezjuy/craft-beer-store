export interface IEventoProducto {
    id?: number;
    productoDescripcion?: string;
    productoId?: number;
    eventoId?: number;
}

export class EventoProducto implements IEventoProducto {
    constructor(public id?: number, public productoDescripcion?: string, public productoId?: number, public eventoId?: number) {}
}
