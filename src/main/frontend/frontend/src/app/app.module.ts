import { GeneralModule } from './general/general.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DialogModule } from '@angular/cdk/dialog';
import { ConfirmDeleteComponent } from './confirm-delete/confirm-delete.component';

@NgModule({
  declarations: [
    AppComponent,
    ConfirmDeleteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GeneralModule,
    MatDialogModule,
    DialogModule
  ],
  providers: [
    { provide: MAT_DIALOG_DATA, useValue: {} }, // Puedes usar un valor predeterminado vacío o configurar los valores iniciales según tus necesidades
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
