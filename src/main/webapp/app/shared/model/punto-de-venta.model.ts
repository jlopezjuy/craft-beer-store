export interface IPuntoDeVenta {
  id?: number;
  nombre?: string;
  direccionDeEntrega?: string;
  localidad?: string;
  notas?: any;
  clienteNombreApellido?: string;
  clienteId?: number;
}

export class PuntoDeVenta implements IPuntoDeVenta {
  constructor(
    public id?: number,
    public nombre?: string,
    public direccionDeEntrega?: string,
    public localidad?: string,
    public notas?: any,
    public clienteNombreApellido?: string,
    public clienteId?: number
  ) {}
}
