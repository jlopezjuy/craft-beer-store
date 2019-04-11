import { NgModule } from '@angular/core';
import {
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatRippleModule,
    MatTabsModule,
    MatTooltipModule
} from '@angular/material';
import { ChatPanelComponent } from 'app/components/chat-panel/chat-panel.component';
import { ChatPanelService } from 'app/components/chat-panel/chat-panel.service';
import { FuseSharedModule } from '../../../@fuse/shared.module';

@NgModule({
    declarations: [ChatPanelComponent],
    providers: [ChatPanelService],
    imports: [
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatTabsModule,
        MatTooltipModule,
        MatRippleModule,

        FuseSharedModule
    ],
    exports: [ChatPanelComponent]
})
export class ChatPanelModule {}
