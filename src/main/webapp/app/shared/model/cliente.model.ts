import { Provincia } from 'app/shared/model/enumerations/provincia.model';
import { TipoCliente } from 'app/shared/model/enumerations/tipo-cliente.model';

export interface ICliente {
  id?: number;
  nombreApellido?: string;
  domicilio?: string;
  localidad?: string;
  codigoPostal?: number;
  provincia?: Provincia;
  tipoCliente?: TipoCliente;
  telefono?: string;
  correo?: string;
  empresaId?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombreApellido?: string,
    public domicilio?: string,
    public localidad?: string,
    public codigoPostal?: number,
    public provincia?: Provincia,
    public tipoCliente?: TipoCliente,
    public telefono?: string,
    public correo?: string,
    public empresaId?: number
  ) {}
}
