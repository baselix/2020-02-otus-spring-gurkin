import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Genre } from '../model/genre'

@Injectable()
export class GenreService {
  constructor(private http: HttpClient) {}

  fetchGenres(): Observable<Genre[]>{
    return this.http.get<Genre[]>('/genres');
  }

  fetchGenre(id: number): Observable<Genre>{
    return this.http.get<Genre>('/genres/' + id);
  }

  createGenre(genre: Genre): Observable<Genre>{
    return this.http.post<Genre>('/genres', genre, {});
  }

  updateGenre(genre: Genre): Observable<Genre> {
    return this.http.put<Genre>('/genres/' + genre.id, genre, {});
  }

  deleteGenre(id: number): Observable<any> {
  const url = `/genres/${id}`
    console.log("delete genre " + id)
    return this.http.delete(url, {})
  }
}
