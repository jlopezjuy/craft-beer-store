import { Moment } from 'moment';

export interface IEvento {
    id?: number;
    nombreEvento?: string;
    fechaEvento?: Moment;
    cantidadBarriles?: number;
    precioPinta?: number;
    empresaId?: number;
}

export class Evento implements IEvento {
    constructor(
        public id?: number,
        public nombreEvento?: string,
        public fechaEvento?: Moment,
        public cantidadBarriles?: number,
        public precioPinta?: number,
        public empresaId?: number
    ) {}
}
