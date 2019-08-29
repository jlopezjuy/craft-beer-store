import { AfterViewInit, Component, ElementRef, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { PasswordResetInitService } from '../../account';
import { EMAIL_NOT_FOUND_TYPE } from '../../shared';

@Component({
  selector: 'jhi-page-forgot-password',
  templateUrl: './page-forgot-password.component.html',
  styleUrls: ['./page-forgot-password.component.css']
})
export class PageForgotPasswordComponent implements OnInit, AfterViewInit {
  error: string;
  errorEmailNotExists: string;
  resetAccount: any;
  success: string;

  constructor(
    private router: Router,
    private passwordResetInitService: PasswordResetInitService,
    private elementRef: ElementRef,
    private renderer: Renderer
  ) {}

  ngOnInit() {
    this.resetAccount = {};
  }

  onSubmit() {
    this.router.navigate(['/authentication/page-login']);
  }

  ngAfterViewInit() {
    this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#email'), 'focus', []);
  }

  requestReset() {
    this.error = null;
    this.errorEmailNotExists = null;

    this.passwordResetInitService.save(this.resetAccount.email).subscribe(
      () => {
        this.success = 'OK';
      },
      response => {
        this.success = null;
        if (response.status === 400 && response.error.type === EMAIL_NOT_FOUND_TYPE) {
          this.errorEmailNotExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      }
    );
  }

  previousState() {
    window.history.back();
  }
}
