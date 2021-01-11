import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { GenreService } from '../service/genre.service';
import {
         PageEvent,
         MatPaginator,
         MatTableDataSource,
       } from '@angular/material';

@Component({
  selector: 'genress',
  templateUrl: './genres.component.html',
  styleUrls: ['./genres.component.css']
})
export class GenresComponent implements AfterViewInit, OnInit {
  constructor(private genreService: GenreService){}

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
}
