import { ChangeDetectionStrategy, Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { NotesService } from './notes.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class NoteComponent implements OnInit,OnDestroy {
 protected notesArray: any[] = [] //Solo accessible en el mismo package


  //params: HttpParams  = new HttpParams().set('username',environment.databaseUsername).set('password',environment.databasePassword);
  constructor(private noteService: NotesService,
    router: Router) {}
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
  let formData = new FormGroup( {
      sarasa: new FormControl()
  })
  formData.patchValue({
    sarasa: "hola"
  });

  console.log(JSON.stringify(formData.getRawValue())) //EJEMPLO DE STRINGIFICAR EL JSON SIN VOLVERTE LOCO

}




}
