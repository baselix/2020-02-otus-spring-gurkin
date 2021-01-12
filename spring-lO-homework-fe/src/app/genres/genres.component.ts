import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { GenreEditDialogComponent } from '../genre-edit-dialog/genre-edit-dialog.component'
import { GenreService } from '../service/genre.service';
import { Genre } from '../model/genre';
import {
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'genress',
  templateUrl: './genres.component.html',
  styleUrls: ['./genres.component.css']
})
export class GenresComponent implements AfterViewInit, OnInit {
  constructor(
    private genreService: GenreService,
    public dialog: MatDialog
  ){}

  displayedColumns = ['id', 'title', 'edit', 'delete'];

  @ViewChild('paginator') paginator: MatPaginator;
  public dataSource = new MatTableDataSource();

  ngOnInit(){
    this.genreService.fetchGenres().subscribe(res => {
      this.dataSource.data = res;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  deleteGenre(data: Genre){
    this.genreService.deleteGenre(data.id).subscribe(() => {
      const index = this.dataSource.data.indexOf(data);
        this.dataSource.data.splice(index, 1);
        this.dataSource._updateChangeSubscription();
    });
  }

  openDialog(data: Genre): void {
    let dialogRef = this.dialog.open(GenreEditDialogComponent, {
      width: '600px',
      data: data
    }, );
    dialogRef.componentInstance.event.subscribe((result) => {
      if(result.data.id == null){
        this.genreService.createGenre(result.data).subscribe(g => {
          const dataSet = this.dataSource.data;
          dataSet.push(g);
          this.dataSource.data = dataSet;
        });
      }else{
        this.genreService.updateGenre(result.data).subscribe(g => {
          const index = this.dataSource.data.indexOf(g);
          this.dataSource.data.splice(index, 1, g);
        });
      }
    });
  }
}
