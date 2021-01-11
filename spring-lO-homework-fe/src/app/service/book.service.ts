import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Book } from '../model/book'

@Injectable()
export class BookService {
  constructor(private http: HttpClient) {}

  fetchBooks(): Observable<Book[]>{
    return this.http.get<Book[]>('/book');
  }
}
