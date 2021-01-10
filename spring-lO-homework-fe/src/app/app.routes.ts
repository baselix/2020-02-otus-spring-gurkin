import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import { WelcomeComponent } from './welcome/welcome.component'
import { AuthorsComponent } from './authors/authors.component'
import { BooksComponent } from './books/books.component'
import { GenresComponent } from './genres/genres.component'
import { AboutComponent } from './about.component'
import { NotFoundComponent } from './not-found.component'

// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: WelcomeComponent},
    { path: 'authors', component: AuthorsComponent},
    { path: 'books', component: BooksComponent},
    { path: 'genres', component: GenresComponent},
    { path: 'about', component: AboutComponent},
    { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRouters {}
