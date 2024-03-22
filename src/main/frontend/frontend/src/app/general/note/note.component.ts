import {MatDialog } from '@angular/material/dialog';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {NotesService } from './notes.service';
import {ConfirmDeleteComponent } from 'src/app/confirm-delete/confirm-delete.component';
import {AddNoteComponent } from '../add-note/add-note.component';
import {lastValueFrom, mergeMap, of, switchMap } from 'rxjs';


@Component({
  selector: 'app-note',
  templateUrl: './note.component.html'
})
export class NoteComponent implements OnInit, OnDestroy {
 protected notesArray: any[] = []
 opened = false;
 noteArrayLength = 0

  constructor(private noteService: NotesService, private matDialog: MatDialog) {}
  ngOnDestroy(): void {
    this.noteService.bringNotes.unsubscribe();
  }
 

  ngOnInit(): void { //TODO: Fix.
    this.noteService.bringNotes.subscribe((type: string) => {
      type === 'archived' ? this.getArchivedNotes() : this.showNotes()
    })
    this.showNotes();
  }

  showNotes() {
   this.noteService.getAllActive().subscribe((data: any) => {
      this.notesArray = data;
   })
  }

  deleteNote(id: Number):void {
    this.matDialog.open(ConfirmDeleteComponent, {data: {id: id}})
    this.matDialog.afterOpened.subscribe(() => this.opened = true)
  }

  async updateNote(id: Number):Promise<void>{
    let dataRecibida;
    let dialogRef;

    dataRecibida = await lastValueFrom(this.noteService.getNoteById(id));
    dialogRef = this.matDialog.open(AddNoteComponent, {data:
    {title: dataRecibida.title,
      content: dataRecibida.content,
      categories: dataRecibida.categories,
    id: dataRecibida.id}})
    dialogRef.afterOpened().subscribe(() => this.opened = true)

    dialogRef.afterClosed().pipe(
      switchMap((data: any) => {
        this.opened = false;
        data.categories = this.noteService.mapearJSON(data);
        return this.noteService.updateNote(id, data);
      }),
      mergeMap(() => {
        location.reload()
        return of(null);
      })
    ).subscribe();
  }
  getArchivedNotes() {
    this.noteService.getAllArchived().subscribe((data: any) => {
      this.notesArray = data;
   })
  }

  handleNoteStatus(id: number) {
   this.noteService.handleNoteStatus(id).subscribe();
   window.alert("Your operation is being processed. Page will refresh automatically")
   setTimeout(() => {
    location.reload();
   },3000)
  }
}
