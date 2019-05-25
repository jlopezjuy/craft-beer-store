export const enum TipoInsumo {
    MALTA = 'MALTA',
    LUPULO = 'LUPULO',
    LEVADURA = 'LEVADURA',
    ACIDO = 'ACIDO',
    SAL = 'SAL',
    CLARIFICANTE = 'CLARIFICANTE',
    OTROS = 'OTROS'
}

export interface IRecetaInsumo {
    id?: number;
    tipoInsumo?: TipoInsumo;
    cantidad?: number;
    insumoId?: number;
    recetaId?: number;
    /**
     * transient
     */
    nombreInsumo?: string;
}

export class RecetaInsumo implements IRecetaInsumo {
    constructor(
        public id?: number,
        public tipoInsumo?: TipoInsumo,
        public cantidad?: number,
        public insumoId?: number,
        public recetaId?: number,
        /**
         * transient
         */
        public nombreInsumo?: string
    ) {}
}
