export const enum TipoCliente {
    CONSUMIDOR_FINAL = 'CONSUMIDOR_FINAL',
    RESPONSABLE_INSCRIPTO = 'RESPONSABLE_INSCRIPTO',
    OTRO = 'OTRO'
}

export interface ICliente {
    id?: number;
    nombeApellido?: string;
    domicilio?: string;
    tipoCliente?: TipoCliente;
    telefono?: string;
    correo?: string;
    empresaId?: number;
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public nombeApellido?: string,
        public domicilio?: string,
        public tipoCliente?: TipoCliente,
        public telefono?: string,
        public correo?: string,
        public empresaId?: number
    ) {}
}
