import { lastValueFrom } from 'rxjs';
import { Component, Inject } from "@angular/core";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { NotesService } from "../general/note/notes.service";

@Component({
  selector: "app-confirm-delete",
  templateUrl: "./confirm-delete.component.html",
})
export class ConfirmDeleteComponent {
  clicked = false;
  constructor(
    private dialogRef: MatDialog,
    private notesService: NotesService,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  async onDeleteClick(): Promise<void> {
    this.clicked = true;
    let id = this.data.id;
    await lastValueFrom(this.notesService.deleteNoteById(id))
    this.dialogRef.closeAll();
    location.reload();
  }
}
