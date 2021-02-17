import { Component, EventEmitter, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { GenreService } from '../service/genre.service';

@Component({
  selector: 'genre-edit-dialog',
  templateUrl: './genre-edit-dialog.component.html',
  styleUrls: ['./genre-edit-dialog.component.css']
})
export class GenreEditDialogComponent {
  constructor(
    private genreService: GenreService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<GenreEditDialogComponent>
  ){}

  private genre = this.data;
  private isAdd = this.genre.title == null;

  public event: EventEmitter<any> = new EventEmitter();

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    console.log(this.genre);
    console.log(this.genre.id);
    console.log(this.genre.title);
    this.event.emit({data: this.genre});
    this.dialogRef.close();
  }
}
