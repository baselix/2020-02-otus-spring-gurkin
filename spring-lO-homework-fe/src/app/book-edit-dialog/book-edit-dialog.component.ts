import { Component, EventEmitter, Inject, ElementRef, ViewChild} from '@angular/core';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { BookService } from '../service/book.service';
import { AuthorService } from '../service/author.service';
import { GenreService } from '../service/genre.service';
import { Author } from '../model/author';
import { Genre } from '../model/genre';

@Component({
  selector: 'book-edit-dialog',
  templateUrl: './book-edit-dialog.component.html',
  styleUrls: ['./book-edit-dialog.component.css']
})
export class BookEditDialogComponent {

  private book = this.data;
  private isAdd = this.book.title == null;

  public event: EventEmitter<any> = new EventEmitter();

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  authorCtrl = new FormControl();
  filteredAuthors: Observable<Author[]>;
  allAuthors: Author[] = [];

  genreCtrl = new FormControl();
  filteredGenres: Observable<Genre[]>;
  allGenres: Genre[] = [];

  @ViewChild('authorInput') authorInput: ElementRef;
  @ViewChild('autoAuthor') authorAutocomplete: MatAutocomplete;

  @ViewChild('genreInput') genreInput: ElementRef;
  @ViewChild('autoGenre') genreAutocomplete: MatAutocomplete;

  constructor(
    private bookService: BookService,
    private authorService: AuthorService,
    private genreService: GenreService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<BookEditDialogComponent>
  ){
    this.authorService.fetchAuthors().subscribe(res => {
              this.allAuthors = res;
            });

    this.genreService.fetchGenres().subscribe(res => {
              this.allGenres = res;
            });

    this.filteredAuthors = this.authorCtrl.valueChanges.pipe(
          startWith(null),
          map((authorName: string | null) => authorName ? this._filterAuthors(authorName) : this.getAllAuthors()));

    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
          startWith(null),
          map((genreTitle: string | null) => genreTitle ? this._filterGenres(genreTitle) : this.getAllGenres()));
  }

  getAllAuthors(): Author[]{
    return this.allAuthors.filter(value => {
      var isFound = true;
      for(var i = 0; i < this.book.authors.length; i++){
        var author = this.book.authors[i];
        if(author.id == value.id && author.name == value.name){
          isFound = false;
        }
      }
      return isFound;
    }).slice();
  }

  getAllGenres(): Genre[]{
    return this.allGenres.filter(value => {
      var isFound = true;
      for(var i = 0; i < this.book.genres.length; i++){
        var genre = this.book.genres[i];
        if(genre.id == value.id && genre.title == value.title){
          isFound = false;
        }
      }
      return isFound;
    }).slice();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    console.log(this.book);
    console.log(this.book.id);
    console.log(this.book.title);
    console.log(this.book.authors);
    this.event.emit({data: this.book});
    this.dialogRef.close();
  }

  addAuthor(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our author
    if (typeof value !== 'string') {
      this.book.authors.push(value);
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.authorCtrl.setValue(null);
  }

  addGenre(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our author
    if (typeof value !== 'string') {
      this.book.genres.push(value);
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.genreCtrl.setValue(null);
  }

  removeAuthor(author: Author): void {
    const index = this.book.authors.indexOf(author);

    if (index >= 0) {
      this.book.authors.splice(index, 1);
    }
  }

  removeGenre(genre: Genre): void {
    const index = this.book.genres.indexOf(genre);

    if (index >= 0) {
      this.book.genres.splice(index, 1);
    }
  }

  selectedAuthor(event: MatAutocompleteSelectedEvent): void {
    this.book.authors.push(event.option.value);
    this.authorInput.nativeElement.value = '';
    this.authorCtrl.setValue(null);
  }

  selectedGenre(event: MatAutocompleteSelectedEvent): void {
    this.book.genres.push(event.option.value);
    this.genreInput.nativeElement.value = '';
    this.genreCtrl.setValue(null);
  }

  private _filterAuthors(value: string | Author): Author[] {
    var filterValue;
    if(typeof value === 'string'){
      filterValue = value.toLowerCase();
    }else{
      filterValue = value.name.toLowerCase();
    }

    return this.getAllAuthors().filter(author => author.name.toLowerCase().indexOf(filterValue) === 0);
  }

  private _filterGenres(value: string | Genre): Genre[] {
    var filterValue;
    if(typeof value === 'string'){
      filterValue = value.toLowerCase();
    }else{
      filterValue = value.title.toLowerCase();
    }

    return this.getAllGenres().filter(genre => genre.title.toLowerCase().indexOf(filterValue) === 0);
  }
}
