import { Moment } from 'moment';

export const enum TipoEquipamiento {
    MEDICION = 'MEDICION',
    MOLIENDA = 'MOLIENDA',
    ENFIADOR = 'ENFIADOR',
    FERMENTACION = 'FERMENTACION',
    EMBOTELLADO = 'EMBOTELLADO',
    CHOPERA = 'CHOPERA',
    CANILLA = 'CANILLA',
    MACERACION = 'MACERACION',
    HERVIDO = 'HERVIDO',
    TRASVASE = 'TRASVASE',
    EMBARRILADO = 'EMBARRILADO',
    LIMPIEZA = 'LIMPIEZA',
    ACCESORIOS_VARIOS = 'ACCESORIOS_VARIOS',
    FILTRADO = 'FILTRADO'
}

export interface IEquipamiento {
    id?: number;
    nombreEquipamiento?: string;
    tipoEquipamiento?: TipoEquipamiento;
    precio?: number;
    costoEnvio?: number;
    fechaCompra?: Moment;
    imagenContentType?: string;
    imagen?: any;
    empresaNombreEmpresa?: string;
    empresaId?: number;
}

export class Equipamiento implements IEquipamiento {
    constructor(
        public id?: number,
        public nombreEquipamiento?: string,
        public tipoEquipamiento?: TipoEquipamiento,
        public precio?: number,
        public costoEnvio?: number,
        public fechaCompra?: Moment,
        public imagenContentType?: string,
        public imagen?: any,
        public empresaNombreEmpresa?: string,
        public empresaId?: number
    ) {}
}
