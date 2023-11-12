import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html',
  styleUrls: ['./add-note.component.css']
})
export class AddNoteComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<AddNoteComponent>, @Inject(MAT_DIALOG_DATA) private data: any, private formBuilder: FormBuilder) {
  }
  name= 'Add'

  ngOnInit(): void {
      if(this.data.id !== undefined) {
        this.name= 'Update'
      }

      this.formData.patchValue({
        title: this.data.title,
        content: this.data.content,
        categories: this.data.categories.map((category:any) => category.tag) //Como es una lista, mapeo para mostrar el tag q equivale al nombre
      })
  }

  formData = this.formBuilder.group({
    id: [''],
    title: ['', Validators.required],
    content: ['', Validators.required],
    categories: ['', Validators.required]
  })


  onSubmit():void {
    this.dialogRef.close(this.formData.value);
    console.log(this.formData.value)
  }

}
