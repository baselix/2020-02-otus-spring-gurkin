import { Component, Input, OnInit } from '@angular/core';
import { Author } from '../model/author'

@Component({
  selector: 'author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent {
  @Input() author: Author
}
