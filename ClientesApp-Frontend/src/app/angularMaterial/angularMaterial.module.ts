import { NgModule } from '@angular/core';

import { MatMomentDateModule } from '@angular/material-moment-adapter';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSelectModule} from '@angular/material/select';

@NgModule({
    exports: [
        MatMomentDateModule, 
        MatFormFieldModule,
        MatDatepickerModule,
        MatInputModule,
        MatProgressSpinnerModule,
        MatSelectModule
    ]
})
export class AngularMaterialModule {

}