import { TipoInsumo } from 'app/shared/model/enumerations/tipo-insumo.model';
import { UsoMalta } from 'app/shared/model/enumerations/uso-malta.model';
import { ModoLupulo } from 'app/shared/model/enumerations/modo-lupulo.model';
import { UsoLupulo } from 'app/shared/model/enumerations/uso-lupulo.model';
import { TipoOtro } from 'app/shared/model/enumerations/tipo-otro.model';
import { UsoOtro } from 'app/shared/model/enumerations/uso-otro.model';

export interface IRecetaInsumo {
  id?: number;
  tipoInsumo?: TipoInsumo;
  cantidad?: number;
  color?: number;
  porcentaje?: number;
  usoMalta?: UsoMalta;
  alpha?: number;
  modoLupulo?: ModoLupulo;
  gramos?: number;
  usoLupulo?: UsoLupulo;
  tiempo?: number;
  ibu?: number;
  densidadLeva?: number;
  tamSobre?: number;
  atenuacion?: number;
  tipoOtro?: TipoOtro;
  usoOtro?: UsoOtro;
  tiempoOtro?: number;
  insumoRecomendadoNombre?: string;
  insumoRecomendadoId?: number;
  recetaId?: number;
}

export class RecetaInsumo implements IRecetaInsumo {
  constructor(
    public id?: number,
    public tipoInsumo?: TipoInsumo,
    public cantidad?: number,
    public color?: number,
    public porcentaje?: number,
    public usoMalta?: UsoMalta,
    public alpha?: number,
    public modoLupulo?: ModoLupulo,
    public gramos?: number,
    public usoLupulo?: UsoLupulo,
    public tiempo?: number,
    public ibu?: number,
    public densidadLeva?: number,
    public tamSobre?: number,
    public atenuacion?: number,
    public tipoOtro?: TipoOtro,
    public usoOtro?: UsoOtro,
    public tiempoOtro?: number,
    public insumoRecomendadoNombre?: string,
    public insumoRecomendadoId?: number,
    public recetaId?: number
  ) {}
}
