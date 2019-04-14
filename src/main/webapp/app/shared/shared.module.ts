import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { CraftBeerStoreSharedLibsModule, CraftBeerStoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

import { JhMaterialModule } from 'app/shared/jh-material.module';

@NgModule({
    imports: [JhMaterialModule, CraftBeerStoreSharedLibsModule, CraftBeerStoreSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [JhMaterialModule, CraftBeerStoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftBeerStoreSharedModule {
    static forRoot() {
        return {
            ngModule: CraftBeerStoreSharedModule
        };
    }
}
