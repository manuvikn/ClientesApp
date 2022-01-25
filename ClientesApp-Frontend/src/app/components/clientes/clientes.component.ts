import { Component, OnInit, ViewChild } from '@angular/core';
import { of } from 'rxjs';
import { delay, switchMap } from 'rxjs/operators';
import { Cliente } from 'src/app/models/cliente';
import { Page } from 'src/app/models/page';
import { PaginatorComponent } from 'src/app/utils/paginator/paginator.component';
import Swal from 'sweetalert2';
import { ClientesService } from './clientes.service';
import { ClientDetailComponent } from './components/clientDetail/clientDetail.component';

@Component({
    selector: 'clientes-component',
    templateUrl: './clientes.component.html',
    styleUrls: ['./clientes.component.scss']
})
export class ClientesComponent implements OnInit {

    @ViewChild('paginator') paginator: PaginatorComponent; 
    @ViewChild('clientDetailComponent') clientDetailComponent: ClientDetailComponent; 

    private readonly firsPage: number = 0;
    pageClientes: Page<Cliente>;
    listClientes: Array<Cliente>;
    pageVisibles: Array<any>;

    loading: boolean = false;

    constructor(private clientesService: ClientesService) { }

    ngOnInit(): void {
        this.getAllClientes(this.firsPage);
    }

    getAllClientes(pageNumber: number): void {

        this.loading = true; 

        this.clientesService.getAllClientesPageable(pageNumber)
            .pipe(switchMap(data => of(data)), delay(500))
            .subscribe((page: Page<Cliente>) => {

                this.loading = false;
                this.pageClientes = page;
                this.listClientes = page.content;
                this.paginator.pageClientes = page;
                this.paginator.showPageVisibles();
            });

    }

    deleteCliente(cliente: Cliente): void {

        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-success ms-3',
                cancelButton: 'btn btn-danger'
            },
            buttonsStyling: false
        });

        swalWithBootstrapButtons.fire({
            title: 'Está seguro?',
            text: `¿Seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellidos}?`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'No, cancelar!',
            reverseButtons: true
        }).then((result) => {

            if (result.isConfirmed) {

                this.clientesService.deleteCliente(cliente.id)
                    .subscribe(() => {

                        swalWithBootstrapButtons.fire(
                            'Cliente eliminado',
                            `El cliente ${cliente.nombre} ha sido eliminado correctamente`,
                            'success'
                        );

                        this.listClientes.splice(this.listClientes.indexOf(cliente), 1);

                    });
            }
        });

    }

    showUpdateModal(cliente: Cliente) {

        this.clientDetailComponent.toggleModal( cliente );

    }

    uploadImageEvent(data: Cliente) {

        const cliente: Cliente | undefined = this.listClientes.find(c => c.id == data.id);
        if (cliente) cliente.foto = data.foto;

    }

}