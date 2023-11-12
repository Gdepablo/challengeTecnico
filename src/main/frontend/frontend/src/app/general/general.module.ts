import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteComponent } from './note/note.component';
import {HttpClientModule} from '@angular/common/http';
import { AddNoteComponent } from './add-note/add-note.component';
import { MatDialogActions } from '@angular/material/dialog';
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    MatDialogModule,
    ReactiveFormsModule

  ],
  declarations: [
    NoteComponent,
    AddNoteComponent,

   ],
  exports:[
    NoteComponent
  ],
  providers: [
  ],
})
export class GeneralModule {}
