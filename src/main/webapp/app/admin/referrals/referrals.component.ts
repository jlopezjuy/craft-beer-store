import { Component, Input, OnInit } from '@angular/core';
import { ProductoService } from '../../entities/producto';
import { IProducto } from '../../shared/model/producto.model';
import { IEmpresa } from '../../shared/model/empresa.model';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
  selector: 'jhi-referrals',
  templateUrl: './referrals.component.html',
  styleUrls: ['./referrals.component.css']
})
export class ReferralsComponent implements OnInit {
  public facebookTransitiongoal: number = 87;
  public tweeterTransitiongoal: number = 34;
  public searchTransitiongoal: number = 54;
  public visitsTransitiongoal: number = 67;
  productos: IProducto[];
  @Input() empresa: IEmpresa;

  constructor(private productoService: ProductoService, protected dataUtils: JhiDataUtils) {}

  ngOnInit() {
    this.productoService.queryByEmpresa(null, this.empresa.id).subscribe(resp => {
      this.productos = resp.body;
    });
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
}
