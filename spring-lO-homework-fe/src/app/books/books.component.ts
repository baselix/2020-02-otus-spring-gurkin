import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { BookService } from '../service/book.service';
import {
         PageEvent,
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements AfterViewInit, OnInit {
  constructor(private bookService: BookService){}

  displayedColumns = ['id', 'title', 'edit', 'delete'];

  @ViewChild('paginator') paginator: MatPaginator;
  public dataSource = new MatTableDataSource();

  ngOnInit(){
    this.bookService.fetchBooks().subscribe(res => {
      this.dataSource.data = res;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}
