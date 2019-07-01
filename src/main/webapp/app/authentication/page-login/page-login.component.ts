import { Component, ElementRef, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService, StateStorageService } from '../../core';
import { ToastrService } from 'ngx-toastr';
import { EmpresaService } from '../../account/settings/empresa';
import { IEmpresa } from '../../shared/model/empresa.model';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-page-login',
  templateUrl: './page-login.component.html',
  styleUrls: ['./page-login.component.css']
})
export class PageLoginComponent implements OnInit {
  authenticationError: boolean;
  password: string;
  rememberMe: boolean;
  username: string;
  credentials: any;
  empresa: IEmpresa;
  noEmpresa: boolean;

  constructor(
    private eventManager: JhiEventManager,
    private loginService: LoginService,
    private stateStorageService: StateStorageService,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private router: Router,
    private toastr: ToastrService,
    private empresaService: EmpresaService,
    private $localStorage: LocalStorageService
  ) {}

  ngOnInit() {}

  onSubmit() {
    this.login();
  }

  login() {
    this.loginService
      .login({
        username: this.username,
        password: this.password,
        rememberMe: this.rememberMe
      })
      .then(() => {
        this.authenticationError = false;
        // this.activeModal.dismiss('login success');
        console.log(this.router.url);
        this.empresaService.findEmpresa().subscribe(
          resp => {
            this.empresa = resp.body;
            console.log(this.empresa);
            this.$localStorage.store('empresa', resp.body);
            this.$localStorage.store('empresaActiva', true);
            this.noEmpresa = this.$localStorage.retrieve('empresaActiva');
          },
          error => {
            console.log(error);
            console.log('error');
            this.$localStorage.store('empresaActiva', false);
            this.noEmpresa = this.$localStorage.retrieve('empresaActiva');
          }
        );
        if (
          this.router.url === '/authentication/page-login' ||
          this.router.url === '/register' ||
          /^\/activate\//.test(this.router.url) ||
          /^\/reset\//.test(this.router.url)
        ) {
          this.router.navigate(['/admin/dashboard/index']);
        }

        this.eventManager.broadcast({
          name: 'authenticationSuccess',
          content: 'Sending Authentication Success'
        });

        // previousState was set in the authExpiredInterceptor before being redirected to login modal.
        // since login is successful, go to stored previousState and clear previousState
        const redirect = this.stateStorageService.getUrl();
        if (redirect) {
          this.stateStorageService.storeUrl(null);
          this.router.navigate([redirect]);
        }
      })
      .catch(() => {
        this.authenticationError = true;
        this.toastr.error('Error en llamado a servicio de login!');
      });
  }

  cancel() {
    this.credentials = {
      username: null,
      password: null,
      rememberMe: true
    };
    this.authenticationError = false;
  }
}
