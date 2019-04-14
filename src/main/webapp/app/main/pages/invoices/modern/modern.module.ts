import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InvoiceService } from 'app/main/pages/invoices/invoice.service';
import { InvoiceModernComponent } from 'app/main/pages/invoices/modern/modern.component';
import { FuseSharedModule } from '../../../../../fuse/shared.module';

const routes = [
    {
        path: 'invoices/modern',
        component: InvoiceModernComponent,
        resolve: {
            search: InvoiceService
        }
    }
];

@NgModule({
    declarations: [InvoiceModernComponent],
    imports: [RouterModule.forChild(routes), FuseSharedModule],
    providers: [InvoiceService]
})
export class InvoiceModernModule {}
