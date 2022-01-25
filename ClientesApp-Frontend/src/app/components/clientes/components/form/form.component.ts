import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSelectChange } from '@angular/material/select';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from 'src/app/models/cliente';
import { Region } from 'src/app/models/region';
import Swal from 'sweetalert2';
import { ClientesService } from '../../clientes.service';

@Component({
    selector: 'form-component',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit{

    cliente: Cliente;
    regiones: Array<Region>;
    clienteForm: FormGroup;
    regionSeleccionada: Region;

    constructor(private fb: FormBuilder,
                private clientesService: ClientesService,
                private router: Router,
                private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.initForm();
    }

    initForm(): void {
        this.clienteForm = this.fb.group({
            'nombre': this.fb.control('', [Validators.required, Validators.maxLength(25), Validators.minLength(4)]),
            'apellidos': this.fb.control('', [Validators.required, Validators.maxLength(50), Validators.minLength(3)]),
            'email': this.fb.control('', [Validators.required, Validators.email, Validators.maxLength(100)]),
            'fecha': this.fb.control(null, Validators.required),
            'region': this.fb.control(null, Validators.required)
        });

        this.loadRegiones();
        this.loadCliente();

    }

    createCliente(): void {
        this.cliente = {...this.cliente ,...this.clienteForm.value};

        const idRegion: number = this.clienteForm.get('region')?.value
        this.cliente.region = this.regiones.find(r => r.id == idRegion )  || new Region();

        if (this.cliente.id) {
            
            this.clientesService.updateCliente(this.cliente)
            .subscribe(() => {
                this.router.navigateByUrl('/clientes');
                Swal.fire(
                    'Cliente editado',
                    'Cliente actualizado correctamente',
                    'success'
                );
            });

        } else {
            
            this.clientesService.createCliente(this.cliente)
                .subscribe(() => {
                    this.router.navigateByUrl('/clientes');
                    Swal.fire(
                        'Nuevo cliente',
                        'Cliente creado correctamente',
                        'success'
                    );
                });

        }
        
    }

    loadCliente(): void {

        const id = this.route.snapshot.params['id'];
        if (!id) return;
        
        this.clientesService.getClienteById(id)
            .subscribe(cliente => this.convertDtoToForm(cliente));

    }

    loadRegiones() {

        this.clientesService.getAllRegiones()
            .subscribe(regiones => this.regiones = regiones);
        
    }

    convertDtoToForm(cliente: Cliente): void {
        if (!cliente) return;
        
        this.cliente = cliente;
        this.clienteForm.patchValue({...cliente, region: cliente.region.id});
    }

}