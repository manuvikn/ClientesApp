import { HttpEventType } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { fromEvent, Subscription } from 'rxjs';
import {  filter, map, pluck, tap } from 'rxjs/operators';
import { Cliente } from 'src/app/models/cliente';
import Swal from 'sweetalert2';
import { ClientesService } from '../../clientes.service';

declare let $: any;

@Component({
    selector: 'client-detail',
    templateUrl: './clientDetail.component.html',
    styleUrls: ['./clientDetail.component.scss']
})
export class ClientDetailComponent implements OnInit, AfterViewInit, OnDestroy {

    @Output() uploadImageEvent: EventEmitter<Cliente> = new EventEmitter();

    @ViewChild('inputFile') inputFile: ElementRef; 
    @ViewChild('updateModal') updateModal: ElementRef;

    cliente: Cliente;
    file: File | null;

    private inputFileSource: Subscription; 
    progreso: number = 0;

    constructor(private clientesService: ClientesService) {}

    
    ngOnInit(): void {
        // this.initComponent();    // Este método se usaba cuando el componente no era un modal, y recibia el id del cliente mediante un parametro de la url 
    }

    ngAfterViewInit(): void {
        this.initInputObservable();
    }

    ngOnDestroy(): void {
        this.inputFileSource.unsubscribe();
    }

    // initComponent(): void {
    
    //     this.route.params.subscribe(params => {
    //         const {id} = params; 
    //         if (id) this.getCliente(id);
    //     });

    // }

    initInputObservable(): void {
        this.inputFileSource = fromEvent<Event>(this.inputFile.nativeElement, 'change')
        .pipe(
            tap(() => this.progreso = 0),
            pluck<Event,FileList>('target', 'files'),
            map<FileList, File | null>(fileList => fileList.item(0)),
            filter(file => file?.type.indexOf('image') != -1)
        ).subscribe(file => this.file = file);
    
    }

    // getCliente(id: string): void {

    //     this.clientesService.getClienteById(id)
    //         .subscribe(cliente => this.cliente = cliente);

    // }

    subirImagen() {

        if (this.file) this.clientesService.subirFoto(this.file, this.cliente.id.toString())
            .subscribe(event => {

                if (event.type === HttpEventType.UploadProgress && event.total) {
                    this.progreso = Math.round((event.loaded/event.total)*100);
                } else if (event.type === HttpEventType.Response && event.body) {
                    const body: any = event.body;
                    this.cliente = body.cliente as Cliente;
                    this.uploadImageEvent.emit(this.cliente);
                    // this.cliente = cliente;
                    Swal.fire(
                        "Foto actualizada",
                        `La foto se ha subido correctamente`,
                        'success'
                    );
                }

            });

    }

    toggleModal( cliente: Cliente ) {

        this.cliente = cliente;
        this.progreso = 0;
        this.file = null;
        $(this.updateModal.nativeElement).modal('show');
        
    }


}