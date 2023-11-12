import { Component, OnInit } from '@angular/core';
import { NotesService } from './general/note/notes.service';
import { environment } from 'src/environments/environment';
import { AuthService } from './Authentication/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { AddNoteComponent } from './general/add-note/add-note.component';

@Component({ //TODO: Cambiar selector y nombres
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent{
  notes: any[] = []
  opened: boolean = false;

  constructor(private matdialog: MatDialog) {
  }

  openForm() {
    this.matdialog.closeAll()
    let dialogRef = this.matdialog.open(AddNoteComponent)
    this.opened = true;
    dialogRef.afterOpened().subscribe(() => this.opened = true)
    dialogRef.afterClosed().subscribe( () => {
      this.opened = false;
    }
    )
  }




//No nos desuscribimos de un httpPost porque lo hace angular mismo
}
