import { Component, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Account, AccountService, LoginService } from '../../core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { EmpresaService } from '../../entities/empresa';
import { Empresa, IEmpresa } from '../../shared/model/empresa.model';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnDestroy {
  @Input() sidebarVisible: boolean = true;
  @Input() navTab: string = 'menu';
  @Input() currentActiveMenu;
  @Input() currentActiveSubMenu;
  @Output() changeNavTabEvent = new EventEmitter();
  @Output() activeInactiveMenuEvent = new EventEmitter();
  public themeClass: string = 'theme-cyan';
  public darkClass: string = '';
  private ngUnsubscribe = new Subject();
  @Input() empresa: IEmpresa = new Empresa();
  account: any;
  empresaActiva: boolean;

  constructor(
    private themeService: ThemeService,
    private loginService: LoginService,
    private router: Router,
    protected $localStorage: LocalStorageService,
    protected empresaService: EmpresaService,
    private accountService: AccountService
  ) {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.themeService.themeClassChange.pipe(takeUntil(this.ngUnsubscribe)).subscribe(themeClass => {
      this.themeClass = themeClass;
    });
    this.themeService.darkClassChange.pipe(takeUntil(this.ngUnsubscribe)).subscribe(darkClass => {
      this.darkClass = darkClass;
    });
    this.empresa = this.$localStorage.retrieve('empresa');
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  changeNavTab(tab: string) {
    this.navTab = tab;
  }

  activeInactiveMenu(menuItem: string) {
    this.activeInactiveMenuEvent.emit({ item: menuItem });
  }

  changeTheme(theme: string) {
    this.themeService.themeChange(theme);
  }

  changeDarkMode(darkClass: string) {
    this.themeService.changeDarkMode(darkClass);
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['/authentication/page-login']);
  }

  isAuthenticated() {
    this.empresaActiva = this.$localStorage.retrieve('empresaActiva');
    return this.accountService.isAuthenticated();
  }
}
