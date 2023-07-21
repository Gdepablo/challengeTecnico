import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { NotesService } from './notes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NoteComponent implements OnInit {
  constructor(private noteService: NotesService,
    router: Router) {}
  ngOnInit(): void {
  }

  showNotes() {
    this.noteService.getAllNotes().subscribe((data) => console.log(data));
  }

}
