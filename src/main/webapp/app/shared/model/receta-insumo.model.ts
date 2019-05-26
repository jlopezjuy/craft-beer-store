import { TipoInsumo } from 'app/shared/model/insumo.model';

export const enum UsoMalta {
    MASH = 'MASH',
    RECIRCULATING = 'RECIRCULATING',
    BOIL = 'BOIL',
    FERMENTATION = 'FERMENTATION'
}

export const enum ModoLupulo {
    PELLET = 'PELLET',
    PLUG = 'PLUG',
    WHOLE_LEAF = 'WHOLE_LEAF'
}

export const enum UsoLupulo {
    BOIL = 'BOIL',
    FIRST_WORT = 'FIRST_WORT',
    DRY_HOP = 'DRY_HOP',
    AROMA = 'AROMA',
    MASH = 'MASH'
}

export const enum TipoOtro {
    FINING = 'FINING',
    WATER_AGENT = 'WATER_AGENT',
    SPICE = 'SPICE',
    HERB = 'HERB',
    FLAVOR = 'FLAVOR',
    OTHER = 'OTHER'
}

export const enum UsoOtro {
    BOIL = 'BOIL',
    MASH = 'MASH',
    LICOR = 'LICOR',
    PRIMARY = 'PRIMARY',
    SECONDARY = 'SECONDARY'
}

export interface IRecetaInsumo {
    id?: number;
    tipoInsumo?: TipoInsumo;
    cantidad?: number;
    color?: number;
    porcentaje?: number;
    usoMalta?: UsoMalta;
    alpha?: number;
    modoLupulo?: ModoLupulo;
    gramos?: number;
    usoLupulo?: UsoLupulo;
    tiempo?: number;
    ibu?: number;
    densidadLeva?: number;
    tamSobre?: number;
    atenuacion?: number;
    tipoOtro?: TipoOtro;
    usoOtro?: UsoOtro;
    tiempoOtro?: number;
    insumoNombreInsumo?: string;
    insumoId?: number;
    recetaId?: number;
}

export class RecetaInsumo implements IRecetaInsumo {
    constructor(
        public id?: number,
        public tipoInsumo?: TipoInsumo,
        public cantidad?: number,
        public color?: number,
        public porcentaje?: number,
        public usoMalta?: UsoMalta,
        public alpha?: number,
        public modoLupulo?: ModoLupulo,
        public gramos?: number,
        public usoLupulo?: UsoLupulo,
        public tiempo?: number,
        public ibu?: number,
        public densidadLeva?: number,
        public tamSobre?: number,
        public atenuacion?: number,
        public tipoOtro?: TipoOtro,
        public usoOtro?: UsoOtro,
        public tiempoOtro?: number,
        public insumoNombreInsumo?: string,
        public insumoId?: number,
        public recetaId?: number
    ) {}
}
