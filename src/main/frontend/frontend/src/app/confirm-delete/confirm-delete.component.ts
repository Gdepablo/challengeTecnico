import { Component, Inject } from "@angular/core";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { NotesService } from "../general/note/notes.service";

@Component({
  selector: "app-confirm-delete",
  templateUrl: "./confirm-delete.component.html",
})
export class ConfirmDeleteComponent { /** el MatDialog es un modal. */
  constructor(
    private dialogRef: MatDialog,
    private notesService: NotesService,
    @Inject(MAT_DIALOG_DATA) private data: any, /** Acá recibe la data ADENTRO del modal, porque sí, le podés pasar data
     adentro del modal*/
  ) {}

  onDeleteClick(): void {
    let id = this.data;
    this.notesService.deleteNoteById(id).subscribe();
    this.dialogRef.closeAll(); //Ojo que esto cierra TODOS los modals. Si hay más de uno abierto, los cierra a todos.
    location.reload(); //Recarga la página
  }
}
