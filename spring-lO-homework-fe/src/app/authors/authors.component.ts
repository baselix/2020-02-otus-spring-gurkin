import { Component } from '@angular/core';
import { DataSource } from '@angular/cdk/table';
import {Observable} from 'rxjs/Observable';
import { AuthorService } from '../service/author.service';
import { Author } from '../model/author'

@Component({
  selector: 'authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent {
  constructor(private authorService: AuthorService){}
  displayedColumns = ['id', 'name', 'edit', 'delete'];
  dataSource = new AuthorsDataSource(this.authorService);
}

export class AuthorsDataSource extends DataSource<any> {
  constructor(private authorService: AuthorService) {
    super();
  }

  connect(): Observable<Author[]> {
    return this.authorService.fetchAuthors();
  }

  disconnect() {
  }
}
