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
  showingActives: boolean = true;

  constructor(private matdialog: MatDialog, private notesService : NotesService) {
  }

  openForm() {
    this.matdialog.closeAll()
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
}

  getArchivedNotes() {
    this.notesService.bringNotes.emit("archived");
    this.showingActives = false;
  }

  getActiveNotes() {
    this.notesService.bringNotes.emit("active");
    this.showingActives = true;
  }

}

