import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppRouters } from './app.routes';
import { AppComponent } from './app.component';
import { AuthorsComponent } from './authors/authors.component';
import { AuthorComponent } from './author/author.component';
import { AuthorEditDialogComponent } from './author-edit-dialog/author-edit-dialog.component';
import { BooksComponent } from './books/books.component';
import { BookComponent } from './book/book.component';
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
    AppRouters
  ],
  providers: [
    AuthorService,
    BookService,
    GenreService
  ],
  entryComponents: [
    GenreEditDialogComponent,
    AuthorEditDialogComponent,
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
