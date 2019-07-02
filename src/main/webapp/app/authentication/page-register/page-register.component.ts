import { Component, ElementRef, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse } from '@angular/common/http';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '../../shared';
import { JhiLanguageService } from 'ng-jhipster';
import { LoginModalService } from '../../core';
import { Register } from '../../account';

@Component({
  selector: 'jhi-page-register',
  templateUrl: './page-register.component.html',
  styleUrls: ['./page-register.component.css']
})
export class PageRegisterComponent implements OnInit {
  confirmPassword: string;
  doNotMatch: string;
  error: string;
  errorEmailExists: string;
  errorUserExists: string;
  registerAccount: any;
  success: boolean;
  modalRef: NgbModalRef;

  constructor(
    private router: Router,
    private languageService: JhiLanguageService,
    private loginModalService: LoginModalService,
    private registerService: Register,
    private elementRef: ElementRef,
    private renderer: Renderer
  ) {}

  ngOnInit() {
    this.success = false;
    this.registerAccount = {};
  }

  onSubmit() {
    this.router.navigate(['/authentication/page-login']);
  }

  backLogin() {
    this.router.navigate(['/authentication/page-login']);
  }

  register() {
    if (this.registerAccount.password !== this.confirmPassword) {
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.error = null;
      this.errorUserExists = null;
      this.errorEmailExists = null;
      this.languageService.getCurrent().then(key => {
        this.registerAccount.langKey = key;
        this.registerService.save(this.registerAccount).subscribe(
          () => {
            this.success = true;
          },
          response => this.processError(response)
        );
      });
    }
  }

  openLogin() {
    this.modalRef = this.loginModalService.open();
  }

  private processError(response: HttpErrorResponse) {
    this.success = null;
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = 'ERROR';
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
  }
}
