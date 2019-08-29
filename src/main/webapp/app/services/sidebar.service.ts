import { Injectable } from '@angular/core';
import { Empresa, IEmpresa } from '../shared/model/empresa.model';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  public sidebarVisible: boolean = true;
  public empresaVisible: IEmpresa = new Empresa();

  constructor() {}

  toggle() {
    this.sidebarVisible = !this.sidebarVisible;
  }

  getStatus() {
    return this.sidebarVisible;
  }

  loadEmpresa(empresa: IEmpresa) {
    this.empresaVisible = empresa;
  }
}
