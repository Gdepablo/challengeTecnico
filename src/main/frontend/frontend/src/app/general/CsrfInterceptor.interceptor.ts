import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpXsrfTokenExtractor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
@Injectable()
export class CsrfInterceptor implements HttpInterceptor {
    constructor(private tokenExtractor: HttpXsrfTokenExtractor) {
    }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const headerName = 'XSRF-TOKEN';
      const respHeaderName = 'X-XSRF-TOKEN';
        const token = this.tokenExtractor.getToken() as string;
        if (token !== null && !req.headers.has(headerName)) {
            req = req.clone({ setHeaders: { respHeaderName, token } });
        }
        return next.handle(req);
    }
}
