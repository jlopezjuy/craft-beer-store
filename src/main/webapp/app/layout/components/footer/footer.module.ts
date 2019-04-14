import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule, MatIconModule, MatToolbarModule } from '@angular/material';

import { FooterFuseComponent } from 'app/layout/components/footer/footer.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

@NgModule({
    declarations: [FooterFuseComponent],
    imports: [RouterModule, MatButtonModule, MatIconModule, MatToolbarModule, FuseSharedModule],
    exports: [FooterFuseComponent]
})
export class FooterModule {}
