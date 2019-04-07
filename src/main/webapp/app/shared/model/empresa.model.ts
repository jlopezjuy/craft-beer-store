export interface IEmpresa {
    id?: number;
    nombreEmpresa?: string;
    direccion?: string;
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
        public telefono?: string,
        public correo?: string,
        public userLogin?: string,
        public userId?: number
    ) {}
}
