export interface IEstilos {
  id?: number;
  number?: string;
  nombreEstilo?: string;
  categoriaEstilo?: string;
  abvMin?: number;
  abvMax?: number;
  abvMed?: number;
  ibuMin?: number;
  ibuMax?: number;
  ibuMed?: number;
  srmMin?: number;
  srmMax?: number;
  srmMed?: number;
  descripcion?: string;
  ejemploNombreComercial?: string;
  ejemploImagenComercial?: string;
}

export class Estilos implements IEstilos {
  constructor(
    public id?: number,
    public number?: string,
    public nombreEstilo?: string,
    public categoriaEstilo?: string,
    public abvMin?: number,
    public abvMax?: number,
    public abvMed?: number,
    public ibuMin?: number,
    public ibuMax?: number,
    public ibuMed?: number,
    public srmMin?: number,
    public srmMax?: number,
    public srmMed?: number,
    public descripcion?: string,
    public ejemploNombreComercial?: string,
    public ejemploImagenComercial?: string
  ) {}
}
