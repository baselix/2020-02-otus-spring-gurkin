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

  fetchGenre(id: number): Observable<Genre>{
    return this.http.get<Genre>('/genre/' + id);
  }

  createGenre(genre: Genre): Observable<Genre>{
    return this.http.post<Genre>('/genre', genre, {});
  }

  updateGenre(genre: Genre): Observable<Genre> {
    return this.http.put<Genre>('/genre/' + genre.id, genre, {});
  }

  deleteGenre(id: number): Observable<any> {
  const url = `/genre/${id}`
    console.log("delete genre " + id)
    return this.http.delete(url, {})
  }
}
