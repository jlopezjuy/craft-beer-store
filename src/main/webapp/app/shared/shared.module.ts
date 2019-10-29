import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { CraftBeerStoreSharedLibsModule, CraftBeerStoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

import { JhMaterialModule } from 'app/shared/jh-material.module';
import { LinechartComponent } from '../dashboard/linechart';
import { ChartModule } from 'primeng/chart';
@NgModule({
  imports: [JhMaterialModule, CraftBeerStoreSharedLibsModule, CraftBeerStoreSharedCommonModule, ChartModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, LinechartComponent],
  providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhMaterialModule, CraftBeerStoreSharedCommonModule, ChartModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreSharedModule {
  static forRoot() {
    return {
      ngModule: CraftBeerStoreSharedModule
    };
  }
}
