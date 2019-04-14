import { Component, ViewEncapsulation } from '@angular/core';
import { fuseAnimations } from '../../../../fuse/animations';
import { FuseConfigService } from '../../../../fuse/services/config.service';

@Component({
    selector: 'jhi-maintenance',
    templateUrl: './maintenance.component.html',
    styleUrls: ['./maintenance.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations
})
export class MaintenanceComponent {
    /**
     * Constructor
     *
     * @param {FuseConfigService} _fuseConfigService
     */
    constructor(private _fuseConfigService: FuseConfigService) {
        // Configure the layout
        this._fuseConfigService.config = {
            layout: {
                navbar: {
                    hidden: true
                },
                toolbar: {
                    hidden: true
                },
                footer: {
                    hidden: true
                },
                sidepanel: {
                    hidden: true
                }
            }
        };
    }
}
