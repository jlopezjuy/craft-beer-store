import { NgModule } from '@angular/core';
import { MatDividerModule, MatListModule, MatSlideToggleModule } from '@angular/material';

import { QuickPanelComponent } from 'app/layout/components/quick-panel/quick-panel.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

@NgModule({
    declarations: [QuickPanelComponent],
    imports: [MatDividerModule, MatListModule, MatSlideToggleModule, FuseSharedModule],
    exports: [QuickPanelComponent]
})
export class QuickPanelModule {}
