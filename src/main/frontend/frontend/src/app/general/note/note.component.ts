import { MatDialog } from '@angular/material/dialog';
import { ChangeDetectionStrategy, Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { NotesService } from './notes.service';
import {Validators, FormBuilder } from '@angular/forms';
import { ConfirmDeleteComponent } from 'src/app/confirm-delete/confirm-delete.component';


@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  changeDetection: ChangeDetectionStrategy.Default
})
export class NoteComponent implements OnInit,OnDestroy {
 protected notesArray: any[] = [] //Solo accessible en el mismo package


  //params: HttpParams  = new HttpParams().set('username',environment.databaseUsername).set('password',environment.databasePassword);
  constructor(private noteService: NotesService,
    private formBuilder: FormBuilder, private matDialog: MatDialog) {}
  ngOnInit(): void {
    this.showNotes(); //Le digo que se suscriba al observable al iniciar
    //this.metodoBoludo();
  }

  showNotes() {
    this.noteService.observable.subscribe(() =>
    this.notesArray = this.noteService.getNotes(),
    )
  }

  ngOnDestroy():void {
    this.noteService.observable.unsubscribe();
  }


 metodoBoludo():void {
    this.formBuilder.group({
      id: [''],
      title: ['', Validators.required],
      content: ['', Validators.required]
    })

    //this.formBuilder.patchValue({})

    console.log(JSON.stringify(this.formBuilder)) //EJEMPLO DE STRINGIFICAR EL JSON SIN VOLVERTE LOCO

  }

  deleteNote(id: Number):void {
    this.matDialog.open(ConfirmDeleteComponent, {})
  }

  updateNote(id: Number):void {

  }





}
