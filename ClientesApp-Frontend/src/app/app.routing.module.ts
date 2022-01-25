import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientesComponent } from './components/clientes/clientes.component';
import { ClientDetailComponent } from './components/clientes/components/clientDetail/clientDetail.component';
import { FormComponent } from './components/clientes/components/form/form.component';
import { MainComponent } from './components/main/main.component';

const ROUTES: Routes = [
    {path: 'home', component: MainComponent,  },
    {path: 'clientes', component: ClientesComponent, },
    {path: 'clientes/create', component: FormComponent, },
    {path: 'clientes/update/:id', component: FormComponent, },
    {path: '**', pathMatch:'full', redirectTo: 'clientes'}
];

@NgModule({
    imports: [RouterModule.forRoot(ROUTES)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}