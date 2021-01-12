import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { AuthorEditDialogComponent } from '../author-edit-dialog/author-edit-dialog.component'
import { AuthorService } from '../service/author.service';
import { Author } from '../model/author';
import {
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements AfterViewInit, OnInit {
  constructor(
    private authorService: AuthorService,
    public dialog: MatDialog
  ){}

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

  deleteAuthor(data: Author){
    this.authorService.deleteAuthor(data.id).subscribe(() => {
      const index = this.dataSource.data.indexOf(data);
      this.dataSource.data.splice(index, 1);
      this.dataSource._updateChangeSubscription();
    });
  }

  openDialog(author: Author): void {
    let dialogRef = this.dialog.open(AuthorEditDialogComponent, {
      width: '600px',
      data: author
    }, );
    dialogRef.componentInstance.event.subscribe((result) => {
      if(result.data.id == null){
        this.authorService.createAuthor(result.data).subscribe(g => {
          const dataSet = this.dataSource.data;
          dataSet.push(g);
          this.dataSource.data = dataSet;
        });
      }else{
        this.authorService.updateAuthor(result.data).subscribe(g => {
          const index = this.dataSource.data.indexOf(g);
          this.dataSource.data.splice(index, 1, g);
        });
      }
    });
  }
}
