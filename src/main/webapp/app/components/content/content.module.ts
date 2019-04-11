import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ContentComponent } from 'app/components/content/content.component';
import { FuseSharedModule } from '../../../@fuse/shared.module';

@NgModule({
    declarations: [ContentComponent],
    imports: [RouterModule, FuseSharedModule],
    exports: [ContentComponent]
})
export class ContentModule {}
