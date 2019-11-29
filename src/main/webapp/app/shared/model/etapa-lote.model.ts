import { Moment } from 'moment';
import { EtapaLoteEnum } from 'app/shared/model/enumerations/etapa-lote-enum.model';

export interface IEtapaLote {
  id?: number;
  etapa?: EtapaLoteEnum;
  litros?: number;
  inicio?: Moment;
  fin?: Moment;
  dias?: number;
  tanqueNombre?: string;
  tanqueId?: number;
  loteCodigo?: string;
  loteId?: number;
}

export class EtapaLote implements IEtapaLote {
  constructor(
    public id?: number,
    public etapa?: EtapaLoteEnum,
    public litros?: number,
    public inicio?: Moment,
    public fin?: Moment,
    public dias?: number,
    public tanqueNombre?: string,
    public tanqueId?: number,
    public loteCodigo?: string,
    public loteId?: number
  ) {}
}
