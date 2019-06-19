import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';
import { ThemeService } from '../../services/theme.service';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from '../../core';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    providers: [NgbDropdownConfig]
})
export class HeaderComponent implements OnInit {
    // Properties

    @Input() showNotifMenu: boolean = false;
    @Input() showToggleMenu: boolean = false;
    @Input() darkClass: string = '';
    @Output() toggleSettingDropMenuEvent = new EventEmitter();
    @Output() toggleNotificationDropMenuEvent = new EventEmitter();

    languages: any[];

    constructor(
        private config: NgbDropdownConfig,
        private themeService: ThemeService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService
    ) {
        config.placement = 'bottom-right';
    }

    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
    }

    toggleSettingDropMenu() {
        this.toggleSettingDropMenuEvent.emit();
    }

    toggleNotificationDropMenu() {
        this.toggleNotificationDropMenuEvent.emit();
    }

    toggleSideMenu() {
        this.themeService.showHideMenu();
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        // this.isNavbarCollapsed = true;
    }
}
