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
  ],
})
export class GeneralModule {}
