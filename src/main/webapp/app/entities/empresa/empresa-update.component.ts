import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { Account, AccountService, IUser, UserService } from 'app/core';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-empresa-update',
    templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
    empresa: IEmpresa;
    isSaving: boolean;
    account: Account;
    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected empresaService: EmpresaService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute,
        protected $localStorage: LocalStorageService,
        private eventManager: JhiEventManager,
        private accountService: AccountService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empresa }) => {
            this.empresa = empresa;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.empresa.userId = this.account.id;
        this.isSaving = true;
        if (this.empresa.id !== undefined) {
            this.subscribeToSaveResponse(this.empresaService.update(this.empresa));
        } else {
            this.subscribeToSaveResponse(this.empresaService.create(this.empresa));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
        result.subscribe((res: HttpResponse<IEmpresa>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res: HttpResponse<IEmpresa>) {
        this.$localStorage.store('empresa', res.body);
        this.$localStorage.store('empresaActiva', true);
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    onBlurEmail(email: string) {
        this.empresaService.findByEmail(email).subscribe(
            resp => {
                this.jhiAlertService.error('craftBeerStoreApp.empresa.mailError', null, null);
                this.empresa.correo = null;
            },
            error => {
                console.log(error);
            }
        );
    }
}
