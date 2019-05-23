import { Provincia } from 'app/shared/model/proveedor.model';

export interface IEmpresa {
    id?: number;
    nombreEmpresa?: string;
    direccion?: string;
    localidad?: string;
    codigoPostal?: number;
    provincia?: Provincia;
    telefono?: string;
    correo?: string;
    userLogin?: string;
    userId?: number;
}

export class Empresa implements IEmpresa {
    constructor(
        public id?: number,
        public nombreEmpresa?: string,
        public direccion?: string,
        public localidad?: string,
        public codigoPostal?: number,
        public provincia?: Provincia,
        public telefono?: string,
        public correo?: string,
        public userLogin?: string,
        public userId?: number
    ) {}
}
