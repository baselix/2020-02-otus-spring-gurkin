import { Component, OnInit } from '@angular/core';
import { GenreService } from '../service/genre.service';
import { Genre } from '../model/genre'

@Component({
  selector: 'authors',
  templateUrl: './genres.component.html',
  styleUrls: ['./genres.component.css']
})
export class GenresComponent implements OnInit {
  genres: Genre[];

  constructor(private genreService: GenreService){}

  ngOnInit(){
    this.genreService.fetchGenres().subscribe((genresList: Genre[]) => {
      this.genres = genresList;
    });
  }
}
