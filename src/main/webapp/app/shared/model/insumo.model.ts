export const enum Unidad {
    KILOGRAMO = 'KILOGRAMO',
    GRAMO = 'GRAMO',
    VEINTICINCO_KILOS = 'VEINTICINCO_KILOS',
    DIES_KILOS = 'DIES_KILOS',
    LITRO = 'LITRO',
    CM3 = 'CM3',
    ML = 'ML',
    UNIDAD = 'UNIDAD'
}

export const enum TipoInsumo {
    MALTA = 'MALTA',
    LUPULO = 'LUPULO',
    LEVADURA = 'LEVADURA',
    ACIDO = 'ACIDO',
    CLARIFICANTE = 'CLARIFICANTE',
    SAL = 'SAL',
    OTROS = 'OTROS'
}

export interface IInsumo {
    id?: number;
    nombreInsumo?: string;
    marca?: string;
    stock?: number;
    unidad?: Unidad;
    tipo?: TipoInsumo;
    imagenContentType?: string;
    imagen?: any;
    empresaId?: number;
    insumoRecomendadoNombre?: string;
    insumoRecomendadoId?: number;
}

export class Insumo implements IInsumo {
    constructor(
        public id?: number,
        public nombreInsumo?: string,
        public marca?: string,
        public stock?: number,
        public unidad?: Unidad,
        public tipo?: TipoInsumo,
        public imagenContentType?: string,
        public imagen?: any,
        public empresaId?: number,
        public insumoRecomendadoNombre?: string,
        public insumoRecomendadoId?: number
    ) {}
}
