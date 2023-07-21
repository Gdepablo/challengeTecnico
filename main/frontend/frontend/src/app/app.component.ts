import { Component } from '@angular/core';
import { NotesService } from './general/note/notes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  constructor(private noteService: NotesService) {}

  showNotes() {
    // Ahora puedes llamar a la función getAllNotes() del NoteService
    this.noteService.getAllNotes().subscribe((data) => console.log(data));
  }
}
