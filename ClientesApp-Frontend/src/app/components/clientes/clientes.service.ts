import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators'
import { Cliente } from 'src/app/models/cliente';
import { Page } from 'src/app/models/page';
import { Region } from 'src/app/models/region';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn:'root'
})
export class ClientesService {

    private readonly CLIENTE_URL: string = `${environment.API_URL}/clientes`;
    private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

    constructor(private http: HttpClient) {}

    getAllClientes(): Observable<Array<Cliente>> {

        return this.http.get<Array<Cliente>>(this.CLIENTE_URL)
                    .pipe(map<Array<Cliente>, Array<Cliente>>(data => data.reverse().slice(0, 20)));

    }

    getAllRegiones(): Observable<Array<Region>> {

        return this.http.get<Array<Region>>(`${this.CLIENTE_URL}/regiones`);

    }

    getAllClientesPageable(pageNumber: number): Observable<Page<Cliente>> {

        return this.http.get<Page<Cliente>>(`${this.CLIENTE_URL}/page/${pageNumber}`);
    
    }

    createCliente(cliente: Cliente): Observable<number> {

        return this.http.post<number>(this.CLIENTE_URL, cliente, {headers: this.httpHeaders});

    }

    getClienteById( id: string ): Observable<Cliente> {

        return this.http.get<Cliente>(`${this.CLIENTE_URL}/${id}`);

    }

    updateCliente( cliente: Cliente ): Observable<number> {

        return this.http.put<number>(`${this.CLIENTE_URL}/${cliente.id}`, cliente, {headers: this.httpHeaders});

    }

    deleteCliente( id: number ): Observable<void> {

        return this.http.delete<void>(`${this.CLIENTE_URL}/${id}`);

    }

    subirFoto( archivo: File, id: string ): Observable<HttpEvent<unknown>> {

        const formData = new FormData();
        formData.append( "archivo", archivo );
        formData.append("id", id);

        const req = new HttpRequest('POST', `${this.CLIENTE_URL}/upload`, formData, {reportProgress: true} );

        return this.http.request(req)
        
    }

}