import { Component, EventEmitter, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { AuthorService } from '../service/author.service';

@Component({
  selector: 'author-edit-dialog',
  templateUrl: './author-edit-dialog.component.html',
  styleUrls: ['./author-edit-dialog.component.css']
})
export class AuthorEditDialogComponent {
  constructor(
    private authorService: AuthorService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<AuthorEditDialogComponent>
  ){}

  private author = this.data;
  private isAdd = this.author.name == null;

  public event: EventEmitter<any> = new EventEmitter();

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    console.log(this.author);
    console.log(this.author.id);
    console.log(this.author.name);
    this.event.emit({data: this.author});
    this.dialogRef.close();
  }
}
