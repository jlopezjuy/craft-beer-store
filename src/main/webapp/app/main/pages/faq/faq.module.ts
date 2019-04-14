import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MatExpansionModule, MatIconModule } from '@angular/material';

import { FaqService } from 'app/main/pages/faq/faq.service';
import { FaqComponent } from 'app/main/pages/faq/faq.component';
import { FuseSharedModule } from '../../../../fuse/shared.module';

const routes = [
    {
        path: 'faq',
        component: FaqComponent,
        resolve: {
            faq: FaqService
        }
    }
];

@NgModule({
    declarations: [FaqComponent],
    imports: [RouterModule.forChild(routes), MatExpansionModule, MatIconModule, FuseSharedModule],
    providers: [FaqService]
})
export class FaqModule {}
