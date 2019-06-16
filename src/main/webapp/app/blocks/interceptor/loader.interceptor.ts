import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { finalize, tap } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';

@Injectable()
export class LoaderInterceptor implements HttpInterceptor {
    constructor(private $localStorage: LocalStorageService, private ngxLoader: NgxUiLoaderService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // start our loader here
        console.log('entro a loader interceptor');
        this.ngxLoader.start();

        return next.handle(req).pipe(
            tap(
                (event: HttpEvent<any>) => {
                    if (event instanceof HttpResponse) {
                        this.ngxLoader.stop();
                    }
                },
                (err: any) => {
                    this.ngxLoader.stop();
                }
            )
        );
    }
}
