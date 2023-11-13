import {Component} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddNoteComponent } from './general/add-note/add-note.component';
import { NotesService } from './general/note/notes.service';
import { mergeMap, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent{
  notes: any[] = []
  opened: boolean = false;

  constructor(private matdialog: MatDialog, private notesService : NotesService) {
  }

  openForm() {
    this.matdialog.closeAll() //Esto creo que no va
    let dialogRef = this.matdialog.open(AddNoteComponent)
    this.opened = true;
    dialogRef.afterOpened().subscribe(() => this.opened = true)
    dialogRef.afterClosed().pipe(
      switchMap((data: any) => {
      this.opened = false;
      data.categories = this.notesService.mapearJSON(data);
      return this.notesService.newNote(data);
    }),
    mergeMap(() => {
      location.reload()
      return of(null);
    })).subscribe()
}}
//No nos desuscribimos de un httpPost porque lo hace angular mismo

