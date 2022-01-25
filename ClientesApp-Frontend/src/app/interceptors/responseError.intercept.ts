import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { from, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import Swal from 'sweetalert2';

@Injectable()
export class ResponseErrorIntercept implements HttpInterceptor {

    constructor(private router: Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        return next.handle(req).pipe(
            catchError(error => {

                let errrorMessage: string = error.error.mensaje;

                if (error.error['errores']) from<Observable<Array<string>>>(error.error['errores']).pipe(map(m => errrorMessage.concat(`<br>${m}`)))
                                                .subscribe(m => errrorMessage = m);
                
                Swal.fire(
                    'Error',
                    errrorMessage,
                    'error'
                );

                this.router.navigateByUrl('/clientes');
                return throwError(error);
            })
        );

    }
}