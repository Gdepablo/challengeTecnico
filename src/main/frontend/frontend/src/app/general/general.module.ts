import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteComponent } from './note/note.component';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
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
export class GeneralModule {}
