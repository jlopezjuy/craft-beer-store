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
    insumoId?: number;
    recetaId?: number;
}

export class RecetaInsumo implements IRecetaInsumo {
    constructor(public id?: number, public tipoInsumo?: TipoInsumo, public insumoId?: number, public recetaId?: number) {}
}
