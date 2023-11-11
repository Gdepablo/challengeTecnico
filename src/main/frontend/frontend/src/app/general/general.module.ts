import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteComponent } from './note/note.component';
import {HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    HttpClientXsrfModule,
    MatDialogModule
  ],
  declarations: [
    NoteComponent,
   ],
  exports:[
    NoteComponent
  ],
  providers: [
    // Agrega MAT_DIALOG_DATA como proveedor
    { provide: MAT_DIALOG_DATA, useValue: {} }, // Puedes usar un valor predeterminado vacío o configurar los valores iniciales según tus necesidades
  ],
})
export class GeneralModule { }

//Todo componente pertenece a un modulo por ende tiene que aparecer definido en la logica de este modulo y para que pueda ser utilizado por otros modulos lo tenemos que poder exportar
//SI NO ESTA EN EXPORT NO SE PUEDE USAR
