import { Moment } from 'moment';

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

export interface IEmpresa {
  id?: number;
  nombreEmpresa?: string;
  direccion?: string;
  localidad?: string;
  codigoPostal?: number;
  provincia?: Provincia;
  telefono?: string;
  correo?: string;
  logoPrincipalContentType?: string;
  logoPrincipal?: any;
  fechaInicioActividad?: Moment;
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
    public logoPrincipalContentType?: string,
    public logoPrincipal?: any,
    public fechaInicioActividad?: Moment,
    public userLogin?: string,
    public userId?: number
  ) {}
}
