import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html'
})
export class AddNoteComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<AddNoteComponent>, @Inject(MAT_DIALOG_DATA) private data: any, private formBuilder: FormBuilder) {
  }
  name= 'Add'
  categories: any = []

  ngOnInit(): void {
      if(this.data.id !== undefined) {
        this.name= 'Update'
      }
      this.categories = this.data.categories.map((category: any) => category.tag).join(',')
      this.formData.patchValue({
        title: this.data.title,
        content: this.data.content,
        categories: {tag: this.categories}
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
  }

}
