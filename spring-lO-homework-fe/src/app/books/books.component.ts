import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BookEditDialogComponent } from '../book-edit-dialog/book-edit-dialog.component'
import { BookService } from '../service/book.service';
import { Book } from '../model/book';
import {
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements AfterViewInit, OnInit {
  constructor(
    private bookService: BookService,
    public dialog: MatDialog
  ){}

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

  deleteBook(data: Book){
    this.bookService.deleteBook(data.id).subscribe(() => {
      const index = this.dataSource.data.indexOf(data);
      this.dataSource.data.splice(index, 1);
      this.dataSource._updateChangeSubscription();
    });
  }

  openDialog(book: Book): void {
    let dialogRef = this.dialog.open(BookEditDialogComponent, {
      width: '600px',
      data: book
    }, );
    dialogRef.componentInstance.event.subscribe((result) => {
      if(result.data.id == null){
        this.bookService.createBook(result.data).subscribe(g => {
          const dataSet = this.dataSource.data;
          dataSet.push(g);
          this.dataSource.data = dataSet;
        });
      }else{
        this.bookService.updateBook(result.data).subscribe(g => {
          const index = this.dataSource.data.indexOf(g);
          this.dataSource.data.splice(index, 1, g);
        });
      }
    });
  }
}
