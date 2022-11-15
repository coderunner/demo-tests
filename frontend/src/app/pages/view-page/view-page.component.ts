import { Component, OnInit } from '@angular/core';
import { Book } from '../../model/book';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-view-page',
  templateUrl: './view-page.component.html',
  styleUrls: ['./view-page.component.css'],
})
export class ViewPageComponent implements OnInit {
  limit = 1;
  order: 'asc' | 'desc' = 'asc';

  results: Book[] = [];

  constructor(private booksService: BookService) {}

  async ngOnInit() {
    this.results = await this.fetch();
  }

  async onMore() {
    this.limit = this.limit + 1;
    this.results = await this.fetch();
  }

  async onToggle() {
    this.order = this.order === 'asc' ? 'desc' : 'asc';
    this.results = await this.fetch();
  }

  private fetch() {
    return this.booksService.get(this.limit, this.order);
  }
}
