import { TipoInsumo } from 'app/shared/model/insumo.model';

export interface IRecetaInsumo {
    id?: number;
    tipoInsumo?: TipoInsumo;
    cantidad?: number;
    insumoNombreInsumo?: string;
    insumoId?: number;
    recetaId?: number;
}

export class RecetaInsumo implements IRecetaInsumo {
    constructor(
        public id?: number,
        public tipoInsumo?: TipoInsumo,
        public cantidad?: number,
        public insumoNombreInsumo?: string,
        public insumoId?: number,
        public recetaId?: number
    ) {}
}
