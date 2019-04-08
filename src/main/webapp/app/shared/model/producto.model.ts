export const enum EstiloCerveza {
    ALE = 'ALE',
    LAGER = 'LAGER'
}

export const enum TipoProducto {
    FIJO = 'FIJO',
    ROTATIVO = 'ROTATIVO',
    ESPECIAL = 'ESPECIAL'
}

export interface IProducto {
    id?: number;
    nombreProducto?: string;
    estilo?: EstiloCerveza;
    nombreComercial?: string;
    precioLitro?: number;
    tipoProducto?: TipoProducto;
    imagenContentType?: string;
    imagen?: any;
    empresaId?: number;
}

export class Producto implements IProducto {
    constructor(
        public id?: number,
        public nombreProducto?: string,
        public estilo?: EstiloCerveza,
        public nombreComercial?: string,
        public precioLitro?: number,
        public tipoProducto?: TipoProducto,
        public imagenContentType?: string,
        public imagen?: any,
        public empresaId?: number
    ) {}
}
