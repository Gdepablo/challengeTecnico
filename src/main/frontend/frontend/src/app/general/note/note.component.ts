import { ChangeDetectionStrategy, Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { NotesService } from './notes.service';
import { FormBuilder, FormControl, Validators} from '@angular/forms';
import { MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  changeDetection: ChangeDetectionStrategy.Default
})
export class NoteComponent implements OnInit,OnDestroy {
 protected notesArray: any[] = [] //Solo accessible en el mismo package


  //params: HttpParams  = new HttpParams().set('username',environment.databaseUsername).set('password',environment.databasePassword);
  constructor(private noteService: NotesService,
    private matDialog: MatDialog, private formBuilder: FormBuilder) {}
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
  this.noteService.deleteNoteById(id).subscribe()
}

updateNote():void {
  this.matDialog}




}
