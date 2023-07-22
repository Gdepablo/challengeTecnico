import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteComponent } from './note/note.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule
  ],
  declarations: [
    NoteComponent
  ],
  exports:[
    NoteComponent
  ]
})
export class GeneralModule { }

//Todo componente pertenece a un modulo por ende tiene que aparecer definido en la logica de este modulo y para que pueda ser utilizado por otros modulos lo tenemos que poder exportar
//SI NO ESTA EN EXPORT NO SE PUEDE USAR
