import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Book } from "../../model/book";

@Component({
  selector: "app-display-books",
  templateUrl: "./display-books.component.html",
  styleUrls: ["./display-books.component.css"],
})
export class DisplayBooksComponent implements OnInit {
  @Input()
  books: Book[] = [];

  @Output()
  deleteBook = new EventEmitter<string>();

  constructor() {}

  ngOnInit(): void {}

  delete(id: string | null) {
    if (id) {
      this.deleteBook.emit(id);
    }
  }
}
