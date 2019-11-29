import { EstiloCerveza } from 'app/shared/model/enumerations/estilo-cerveza.model';
import { TipoProducto } from 'app/shared/model/enumerations/tipo-producto.model';

export interface IProducto {
  id?: number;
  descripcion?: string;
  tipo?: EstiloCerveza;
  nombreComercial?: string;
  tipoProducto?: TipoProducto;
  imagenContentType?: string;
  imagen?: any;
  observacion?: any;
  srmColor?: string;
  empresaId?: number;
  estilosNombreEstilo?: string;
  estilosId?: number;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public descripcion?: string,
    public tipo?: EstiloCerveza,
    public nombreComercial?: string,
    public tipoProducto?: TipoProducto,
    public imagenContentType?: string,
    public imagen?: any,
    public observacion?: any,
    public srmColor?: string,
    public empresaId?: number,
    public estilosNombreEstilo?: string,
    public estilosId?: number
  ) {}
}
