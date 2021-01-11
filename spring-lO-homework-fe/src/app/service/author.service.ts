import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Author } from '../model/author'

@Injectable()
export class AuthorService {
  constructor(private http: HttpClient) {}

  fetchAuthors(): Observable<Author[]>{
    return this.http.get<Author[]>('/author');
  }
}
