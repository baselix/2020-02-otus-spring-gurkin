import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { MatChipsModule } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatAutocompleteModule } from '@angular/material/autocomplete';

import { AppRouters } from './app.routes';
import { AppComponent } from './app.component';
import { AuthorsComponent } from './authors/authors.component';
import { AuthorComponent } from './author/author.component';
import { AuthorEditDialogComponent } from './author-edit-dialog/author-edit-dialog.component';
import { BooksComponent } from './books/books.component';
import { BookComponent } from './book/book.component';
import { BookEditDialogComponent } from './book-edit-dialog/book-edit-dialog.component';
import { GenresComponent } from './genres/genres.component';
import { GenreComponent } from './genre/genre.component';
import { GenreEditDialogComponent } from './genre-edit-dialog/genre-edit-dialog.component';
import { CommentComponent } from './comment/comment.component';

import { WelcomeComponent } from './welcome/welcome.component'
import { AboutComponent } from './about.component'
import { NotFoundComponent } from './not-found.component'


import { AuthorService } from './service/author.service';
import { BookService } from './service/book.service';
import { GenreService } from './service/genre.service';

@NgModule({
  declarations: [
    AppComponent,
    AuthorsComponent,
    AuthorComponent,
    AuthorEditDialogComponent,
    BooksComponent,
    BookComponent,
    BookEditDialogComponent,
    GenresComponent,
    GenreComponent,
    GenreEditDialogComponent,
    CommentComponent,
    WelcomeComponent,
    AboutComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRouters,
    MatChipsModule,
    MatAutocompleteModule,
  ],
  providers: [
    AuthorService,
    BookService,
    GenreService,
  ],
  entryComponents: [
    GenreEditDialogComponent,
    AuthorEditDialogComponent,
    BookEditDialogComponent,
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
