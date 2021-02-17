import { Component, Input, OnInit } from '@angular/core';
import { Comment } from '../model/comment'

@Component({
  selector: 'comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() comment: Comment
}
