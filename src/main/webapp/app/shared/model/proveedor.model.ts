import { Moment } from 'moment';
import { CondicionFiscal } from 'app/shared/model/enumerations/condicion-fiscal.model';
import { Provincia } from 'app/shared/model/enumerations/provincia.model';

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
