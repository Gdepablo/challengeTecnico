import { Component, OnInit } from '@angular/core';
import { NotesService } from './general/note/notes.service';
import { environment } from 'src/environments/environment';
import { AuthService } from './Authentication/auth.service';

@Component({ //TODO: Cambiar selector y nombres
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit{
  notes: any[] = []

  constructor(private noteService: NotesService, private authService: AuthService) {}
  ngOnInit(): void {
    this.showNotes(); //Me muestra las notas al iniciar y no al clickear un boton.
  }


  public showNotes() {
    //get al back
    this.noteService.getAllNotes(environment.databaseUsername,environment.databasePassword).subscribe(
    (response:any) => {
      this.notes = response; //Asignacion OK.
      this.noteService.setNotes(response);
      this.noteService.observable.emit(response);} //Le seteo las notas al server para compartirlas
      ); //incluye el emit del server.
  }

//No nos desuscribimos de un httpPost porque lo hace angular mismo
}
