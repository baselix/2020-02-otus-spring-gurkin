import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Author } from '../model/author'

@Injectable()
export class AuthorService {
  constructor(private http: HttpClient) {}

  fetchAuthors(): Observable<Author[]>{
    return this.http.get<Author[]>('/authors');
  }

  fetchAuthor(id: number): Observable<Author>{
      return this.http.get<Author>('/authors/' + id);
  }

  createAuthor(author: Author): Observable<Author>{
    return this.http.post<Author>('/authors', author, {});
  }

  updateAuthor(author: Author): Observable<Author> {
    return this.http.put<Author>('/authors/' + author.id, author, {});
  }

  deleteAuthor(id: number): Observable<any> {
  const url = `/authors/${id}`
    console.log("delete author " + id)
    return this.http.delete(url, {})
  }
}
