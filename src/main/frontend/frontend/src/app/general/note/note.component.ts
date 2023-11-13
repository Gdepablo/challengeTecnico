import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ChangeDetectionStrategy, Component, EventEmitter, Inject, OnDestroy, OnInit, Output } from '@angular/core';
import { NotesService } from './notes.service';
import {Validators, FormBuilder } from '@angular/forms';
import { ConfirmDeleteComponent } from 'src/app/confirm-delete/confirm-delete.component';
import { AddNoteComponent } from '../add-note/add-note.component';
import {lastValueFrom, mergeMap, of, switchMap } from 'rxjs';


@Component({
  selector: 'app-note',
  templateUrl: './note.component.html'
})
export class NoteComponent implements OnInit {
 protected notesArray: any[] = [] //Solo accessible en el mismo package
 opened = false;

  //params: HttpParams  = new HttpParams().set('username',environment.databaseUsername).set('password',environment.databasePassword);
  constructor(private noteService: NotesService, private matDialog: MatDialog) {}
  ngOnInit(): void {
    this.showNotes(); //Le digo que se suscriba al observable al iniciar
  }

  showNotes() {
   this.noteService.getAllNotes().subscribe((data: any) => {
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

    dataRecibida = await lastValueFrom(this.noteService.getNoteById(id)); //La llamada a la API es lentisima, no puedo hacer nada con eso
    dialogRef = this.matDialog.open(AddNoteComponent, {data:
    {title: dataRecibida.title,
      content: dataRecibida.content,
      categories: dataRecibida.categories,
    id: dataRecibida.id}})
    dialogRef.afterOpened().subscribe(() => this.opened = true)

    dialogRef.afterClosed().pipe(
      switchMap((data: any) => {
        // Procesar el cierre del diálogo y actualizar la propiedad "categories" del formulario
        this.opened = false;
        data.categories = this.noteService.mapearJSON(data);
        // Realizar la operación de PUT y esperar a que se complete
        return this.noteService.updateNote(id, data);
      }),
      mergeMap(() => { //Tenia bugeado el endpoint de update JAJA. Lo arreglé y ahora funciona
        // Realizar otras acciones después de que la operación de PUT se haya completado
        // Puedes devolver un observable vacío o realizar otras operaciones aquí
        location.reload()
        return of(null); // Puedes devolver un observable vacío o realizar otras operaciones aquí
      })
    ).subscribe();
  }}
