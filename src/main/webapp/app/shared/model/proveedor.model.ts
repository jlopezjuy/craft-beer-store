import { Moment } from 'moment';

export interface IProveedor {
    id?: number;
    nombreProveedor?: string;
    razonSocial?: string;
    cuit?: string;
    telefono?: string;
    fechaAlta?: Moment;
    domicilio?: string;
    email?: string;
    notas?: any;
    empresaId?: number;
}

export class Proveedor implements IProveedor {
    constructor(
        public id?: number,
        public nombreProveedor?: string,
        public razonSocial?: string,
        public cuit?: string,
        public telefono?: string,
        public fechaAlta?: Moment,
        public domicilio?: string,
        public email?: string,
        public notas?: any,
        public empresaId?: number
    ) {}
}
