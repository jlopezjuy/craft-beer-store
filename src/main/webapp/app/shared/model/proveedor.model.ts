import { Moment } from 'moment';

export const enum CondicionFiscal {
    RESPONSABLE_INSCRIPTO = 'RESPONSABLE_INSCRIPTO',
    MONOTRIBUTISTA = 'MONOTRIBUTISTA'
}

export const enum Provincia {
    MISIONES = 'MISIONES',
    SAN_LUIS = 'SAN_LUIS',
    SAN_JUAN = 'SAN_JUAN',
    ENTRE_RIOS = 'ENTRE_RIOS',
    SANTA_CRUZ = 'SANTA_CRUZ',
    RIO_NEGRO = 'RIO_NEGRO',
    CHUBUT = 'CHUBUT',
    CORDOBA = 'CORDOBA',
    MENDOZA = 'MENDOZA',
    LA_RIOJA = 'LA_RIOJA',
    CATAMARCA = 'CATAMARCA',
    LA_PAMPA = 'LA_PAMPA',
    SANTIAGO_DEL_ESTERO = 'SANTIAGO_DEL_ESTERO',
    CORRIENTES = 'CORRIENTES',
    SANTA_FE = 'SANTA_FE',
    TUCUMAN = 'TUCUMAN',
    NEUQUEN = 'NEUQUEN',
    SALTA = 'SALTA',
    CHACO = 'CHACO',
    FORMOSA = 'FORMOSA',
    JUJUY = 'JUJUY',
    CIUDAD_AUTONOMA_DE_BUENOS_AIRES = 'CIUDAD_AUTONOMA_DE_BUENOS_AIRES',
    BUENOS_AIRES = 'BUENOS_AIRES',
    TIERRA_DEL_FUEGO_ANTARTIDA_E_ISLAS_DEL_ATLANTICO_SUR = 'TIERRA_DEL_FUEGO_ANTARTIDA_E_ISLAS_DEL_ATLANTICO_SUR'
}

export interface IProveedor {
    id?: number;
    nombreProveedor?: string;
    razonSocial?: string;
    cuit?: string;
    telefono?: string;
    fechaAlta?: Moment;
    domicilio?: string;
    correo?: string;
    notas?: any;
    condicionFiscal?: CondicionFiscal;
    localidad?: string;
    codigoPostal?: number;
    provincia?: Provincia;
    contacto?: string;
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
        public correo?: string,
        public notas?: any,
        public condicionFiscal?: CondicionFiscal,
        public localidad?: string,
        public codigoPostal?: number,
        public provincia?: Provincia,
        public contacto?: string,
        public empresaId?: number
    ) {}
}
