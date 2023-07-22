import { Component, EventEmitter } from '@angular/core';
import { NotesService } from './general/note/notes.service';
import { environment } from 'src/environments/environment';

@Component({ //TODO: Cambiar selector y nombres
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  notes: any[] = []



  constructor(private noteService: NotesService) {}


  public showNotes() {
    //get al back
    this.noteService.getAllNotes(environment.databaseUsername,environment.databasePassword).subscribe(
    (response:any) => {
      this.notes = response; //Asignacion OK.
      this.noteService.setNotes(response);
      this.noteService.observable.emit(response);
    console.log("Envie la nota" + response)} //Le seteo las notas al server para compartirlas
      ); //incluye el emit del server.
  }
  //notes va a ser hijo de app

//No nos desuscribimos de un httpPost porque lo hace angular mismo
}
