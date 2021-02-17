import { Component, Input, OnInit } from '@angular/core';
import { Genre } from '../model/genre'

@Component({
  selector: 'genre',
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.css']
})
export class GenreComponent {
  @Input() genre: Genre
}
