import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Cliente } from 'src/app/models/cliente';
import { Page } from 'src/app/models/page';

@Component({
    selector: 'paginator',
    templateUrl: './paginator.component.html',
    styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent{

    @Output() getClientesEvent: EventEmitter<number> = new EventEmitter(); 
    @Input() pageClientes: Page<Cliente>;
    pageVisibles: Array<number>;

    constructor() {}

    showPageVisibles() {

        const {number: currentPage, totalPages: totalPages} = this.pageClientes;
        let firstPage = currentPage < 2 ? 0 : (currentPage - 2);
        let lastPage;

        if ((currentPage + 2) >= totalPages) {
            lastPage = totalPages;
            firstPage = totalPages - 5;
        } else {
            lastPage = firstPage + 5;
        }

        this.pageVisibles = new Array(totalPages).fill(0).map((_v, i) => i ).slice(firstPage, lastPage);

    }

    getAllClientes(pageNumber: number): void {
        this.getClientesEvent.emit(pageNumber);
    }


}