import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteComponent } from './note/note.component';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { CsrfInterceptor } from './CsrfInterceptor.interceptor';
@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    HttpClientXsrfModule,
  ],
  declarations: [
    NoteComponent,
   ],
  exports:[
    NoteComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true },
  ],
})
export class GeneralModule { }

//Todo componente pertenece a un modulo por ende tiene que aparecer definido en la logica de este modulo y para que pueda ser utilizado por otros modulos lo tenemos que poder exportar
//SI NO ESTA EN EXPORT NO SE PUEDE USAR
