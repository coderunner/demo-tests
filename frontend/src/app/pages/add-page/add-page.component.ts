import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-add-page',
  templateUrl: './add-page.component.html',
  styleUrls: ['./add-page.component.css'],
})
export class AddPageComponent implements OnInit {
  form = this.fb.group({
    title: ['', Validators.required],
    author: ['', Validators.required],
    description: ['', Validators.required],
    nbPages: ['', Validators.required],
  });

  constructor(private fb: FormBuilder, private booksService: BookService) {}

  ngOnInit(): void {}

  onAdd() {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.booksService.add({
        id: null,
        title: this.form.value.title ?? '',
        author: this.form.value.author ?? '',
        description: this.form.value.description ?? '',
        nbPages: +(this.form.value.nbPages ?? 0),
      });
      this.form.reset();
    }
  }
}
