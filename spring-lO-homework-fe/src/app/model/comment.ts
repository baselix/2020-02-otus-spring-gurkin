import { Book } from './book'

export interface Comment {
  id: number
  book: Book
  message: string
}
