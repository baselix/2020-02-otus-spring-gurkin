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
import { BooksComponent } from './books/books.component';
import { BookComponent } from './book/book.component';
import { GenresComponent } from './genres/genres.component';
import { GenreComponent } from './genre/genre.component';
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
    BooksComponent,
    BookComponent,
    GenresComponent,
    GenreComponent,
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
  bootstrap: [ AppComponent ]
})
export class AppModule { }
