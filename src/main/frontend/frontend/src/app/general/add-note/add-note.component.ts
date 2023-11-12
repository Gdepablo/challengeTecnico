import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html',
  styleUrls: ['./add-note.component.css']
})
export class AddNoteComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<AddNoteComponent>, @Inject(MAT_DIALOG_DATA) private data: any, private formBuilder: FormBuilder, private dialog: MatDialog) {
  }
  name= 'Add'
  categories: any = []

  ngOnInit(): void {
      if(this.data.id !== undefined) {
        this.name= 'Update'
      }
      this.categories = this.data.categories.map((category: any) => category.tag).join(',') //Formato para mostrar
      console.log(this.data.categories.map((category:any) => ({tag: category.tag.trim()}))) //Formato para enviar al back
      console.log(this.categories)
      //console.log(this.formData.value.categories) Devuelve lo que está adentro del formBuilder de categories
      this.formData.patchValue({
        title: this.data.title,
        content: this.data.content,
        //categories: {tag: this.categories} //Forma correcta de patchear un nested form group
      })
  }

  formData = this.formBuilder.group({
    id: [''],
    title: ['', Validators.required],
    content: ['', Validators.required],
    categories: this.formBuilder.group({
      tag: ['', Validators.required]
    })
  })


  onSubmit():void {
    this.dialogRef.close(this.formData.value);
    this.dialog.closeAll();
    console.log(this.formData.value)
  }

}
