import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { AuthorService } from '../service/author.service';
import { Author } from '../model/author'
import {
         PageEvent,
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements AfterViewInit, OnInit {
  constructor(private authorService: AuthorService){}

  displayedColumns = ['id', 'name', 'edit', 'delete'];

  @ViewChild('paginator') paginator: MatPaginator;
  public dataSource = new MatTableDataSource();

  ngOnInit(){
    this.authorService.fetchAuthors().subscribe(res => {
      this.dataSource.data = res;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  getNext(event: PageEvent) {
    let offset = event.pageSize * event.pageIndex;
  }
}
