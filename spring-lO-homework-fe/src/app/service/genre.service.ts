import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Genre } from '../model/genre'

@Injectable()
export class GenreService {
  constructor(private http: HttpClient) {}

  fetchGenres(): Observable<Genre[]>{
    return this.http.get<Genre[]>('/genre');
  }
}
