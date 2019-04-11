import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { VerticalLayout1Component } from 'app/layouts/vertical/layout-1/layout-1.component';
import { FuseSharedModule } from '../../../../@fuse/shared.module';
import { FuseSidebarModule } from '../../../../@fuse/components';
import { ChatPanelModule } from 'app/components/chat-panel/chat-panel.module';
import { NavbarModule } from 'app/components/navbar/navbar.module';
import { FooterModule } from 'app/components/footer/footer.module';
import { ContentModule } from 'app/components/content/content.module';
import { QuickPanelModule } from 'app/components/quick-panel/quick-panel.module';
import { ToolbarModule } from 'app/components/toolbar/toolbar.module';

// import { FuseSidebarModule } from '@fuse/components';
// import { FuseSharedModule } from '@fuse/shared.module';
//
// import { ChatPanelModule } from 'app/layout/components/chat-panel/chat-panel.module';
// import { ContentModule } from 'app/layout/components/content/content.module';
// import { FooterModule } from 'app/layout/components/footer/footer.module';
// import { NavbarModule } from 'app/layout/components/navbar/navbar.module';
// import { QuickPanelModule } from 'app/layout/components/quick-panel/quick-panel.module';
// import { ToolbarModule } from 'app/layout/components/toolbar/toolbar.module';
//
// import { VerticalLayout1Component } from 'app/layout/vertical/layout-1/layout-1.components';

@NgModule({
    declarations: [VerticalLayout1Component],
    imports: [
        RouterModule,

        FuseSharedModule,
        FuseSidebarModule,

        ChatPanelModule,
        ContentModule,
        FooterModule,
        NavbarModule,
        QuickPanelModule,
        ToolbarModule
    ],
    exports: [VerticalLayout1Component]
})
export class VerticalLayout1Module {}
