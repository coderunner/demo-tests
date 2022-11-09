import { Component, Input, OnInit } from '@angular/core';
import { Book } from '../../model/book';

@Component({
  selector: 'app-display-books',
  templateUrl: './display-books.component.html',
  styleUrls: ['./display-books.component.css'],
})
export class DisplayBooksComponent implements OnInit {
  @Input()
  books: Book[] = [];

  constructor() {}

  ngOnInit(): void {}
}
