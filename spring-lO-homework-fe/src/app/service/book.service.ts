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

  fetchBook(id: number): Observable<Book>{
    return this.http.get<Book>('/book/' + id);
  }

  createBook(book: Book): Observable<Book>{
    return this.http.post<Book>('/book', book, {});
  }

  updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>('/book/' + book.id, book, {});
  }

  deleteBook(id: number): Observable<any> {
  const url = `/book/${id}`
    console.log("delete book " + id)
    return this.http.delete(url, {})
  }
}
