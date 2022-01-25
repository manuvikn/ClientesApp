import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { ClientesComponent } from './components/clientes/clientes.component';
import { MainComponent } from './components/main/main.component';
import { NavComponent } from './components/nav/nav.component';
import { FormComponent } from './components/clientes/components/form/form.component';
import { ResponseErrorIntercept } from './interceptors/responseError.intercept';
import { PaginatorComponent } from './utils/paginator/paginator.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './angularMaterial/angularMaterial.module';
import { ClientDetailComponent } from './components/clientes/components/clientDetail/clientDetail.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    NavComponent,
    ClientesComponent,
    FormComponent,
    PaginatorComponent,
    ClientDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AngularMaterialModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ResponseErrorIntercept, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
