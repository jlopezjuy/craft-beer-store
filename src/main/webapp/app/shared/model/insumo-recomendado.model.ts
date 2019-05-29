export interface IInsumoRecomendado {
    id?: number;
    nombre?: string;
    marca?: string;
}

export class InsumoRecomendado implements IInsumoRecomendado {
    constructor(public id?: number, public nombre?: string, public marca?: string) {}
}
