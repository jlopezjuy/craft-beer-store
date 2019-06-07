import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account, UserService } from 'app/core';
import { EmpresaService } from 'app/entities/empresa';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';
import { IEmpresa } from 'app/shared/model/empresa.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    noEmpresa: boolean;
    empresa: IEmpresa;
    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private empresaService: EmpresaService,
        private $localStorage: LocalStorageService,
        private router: Router
    ) {}

    ngOnInit() {
        this.noEmpresa = false;
        this.accountService.identity().then((account: Account) => {
            this.account = account;
            this.loadBaseData();
        });
        this.registerAuthenticationSuccess();
        this.noEmpresa = this.$localStorage.retrieve('empresaActiva');
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
                this.loadBaseData();
            });
        });
    }

    loadBaseData() {
        this.$localStorage.store('account', this.account);
        this.empresaService.findEmpresa().subscribe(
            resp => {
                this.empresa = resp.body;

                this.$localStorage.store('empresa', resp.body);
                this.$localStorage.store('empresaActiva', true);
                this.noEmpresa = this.$localStorage.retrieve('empresaActiva');
            },
            error => {
                console.log('error');
                this.$localStorage.store('empresaActiva', false);
                this.noEmpresa = this.$localStorage.retrieve('empresaActiva');
            }
        );
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    goNewEmpresa() {
        this.router.navigate(['/empresa/new']);
    }
}
