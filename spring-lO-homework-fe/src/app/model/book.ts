import { Author } from './author';
import { Genre } from './genre';

export interface Book {
  id: number
  title: string
  authors: Author[]
  genres: Genre[]
}
