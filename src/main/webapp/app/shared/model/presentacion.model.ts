import { Moment } from 'moment';

export const enum TipoPresentacion {
    BOTELLA_330 = 'BOTELLA_330',
    BOTELLA_355 = 'BOTELLA_355',
    BOTELLA_500 = 'BOTELLA_500',
    BOTELLA_1000 = 'BOTELLA_1000',
    BARRIL_20 = 'BARRIL_20',
    BARRIL_30 = 'BARRIL_30',
    BARRIL_50 = 'BARRIL_50'
}

export interface IPresentacion {
    id?: number;
    tipoPresentacion?: TipoPresentacion;
    cantidad?: number;
    fecha?: Moment;
    costoUnitario?: number;
    precioVentaUnitario?: number;
    precioTotal?: number;
    precioCostoTotal?: number;
    productoId?: number;
    /**
     * transient
     */
    nombreComercial?: string;
}

export class Presentacion implements IPresentacion {
    constructor(
        public id?: number,
        public tipoPresentacion?: TipoPresentacion,
        public cantidad?: number,
        public fecha?: Moment,
        public costoUnitario?: number,
        public precioVentaUnitario?: number,
        public precioTotal?: number,
        public precioCostoTotal?: number,
        public productoId?: number,
        /**
         * transient
         */
        public nombreComercial?: string
    ) {}
}
