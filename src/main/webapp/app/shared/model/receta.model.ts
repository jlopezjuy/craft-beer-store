import { Moment } from 'moment';

export interface IReceta {
    id?: number;
    nombre?: string;
    brewMaster?: string;
    batch?: number;
    temperaturaDeMacerado?: number;
    og?: number;
    fg?: number;
    abv?: number;
    ibu?: number;
    srm?: number;
    empaste?: number;
    temperaturaMacerado?: number;
    fecha?: Moment;
    productoNombreComercial?: string;
    productoId?: number;
}

export class Receta implements IReceta {
    constructor(
        public id?: number,
        public nombre?: string,
        public brewMaster?: string,
        public batch?: number,
        public temperaturaDeMacerado?: number,
        public og?: number,
        public fg?: number,
        public abv?: number,
        public ibu?: number,
        public srm?: number,
        public empaste?: number,
        public temperaturaMacerado?: number,
        public fecha?: Moment,
        public productoNombreComercial?: string,
        public productoId?: number
    ) {}
}
