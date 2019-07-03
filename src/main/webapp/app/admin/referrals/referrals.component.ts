import { Component, Input, OnInit } from '@angular/core';
import { ProductoService } from '../../entities/producto';
import { IProducto } from '../../shared/model/producto.model';
import { IEmpresa } from '../../shared/model/empresa.model';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-referrals',
  templateUrl: './referrals.component.html',
  styleUrls: ['./referrals.component.css']
})
export class ReferralsComponent implements OnInit {
  productos: IProducto[];
  @Input() empresa: IEmpresa;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  previousPage: any;
  reverse: any;
  predicate: any;

  constructor(private productoService: ProductoService, protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.productoService
      .queryByEmpresa(
        {
          page: 0,
          size: 10
        },
        this.empresa.id
      )
      .subscribe(resp => {
        this.productos = resp.body;
      });
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
}
