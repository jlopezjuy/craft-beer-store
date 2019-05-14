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
    descripcion?: string;
    tipo?: EstiloCerveza;
    nombreComercial?: string;
    precioLitro?: number;
    tipoProducto?: TipoProducto;
    imagenContentType?: string;
    imagen?: any;
    observacion?: any;
    empresaId?: number;
    estilosNombreEstilo?: string;
    estilosId?: number;
    /**
     * transient
     */
    presentacionId?: number;
    cantidadPresentacion?: number;
    precioUnitario?: number;
    movimientoId?: number;
    eventoId?: number;
}

export class Producto implements IProducto {
    constructor(
        public id?: number,
        public descripcion?: string,
        public tipo?: EstiloCerveza,
        public nombreComercial?: string,
        public precioLitro?: number,
        public tipoProducto?: TipoProducto,
        public imagenContentType?: string,
        public imagen?: any,
        public observacion?: any,
        public empresaId?: number,
        public estilosNombreEstilo?: string,
        public estilosId?: number,
        /**
         * transient
         */
        public presentacionId?: number,
        public cantidadPresentacion?: number,
        public precioUnitario?: number,
        public movimientoId?: number,
        public eventoId?: number
    ) {}
}
