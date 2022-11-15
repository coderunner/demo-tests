import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Book } from 'src/app/model/book';
import { BookService } from 'src/app/services/book.service';
import { TestHelper } from 'src/app/tests/test-helper';

import { AddPageComponent } from './add-page.component';

describe('AddPageComponent', () => {
  let component: AddPageComponent;
  let fixture: ComponentFixture<AddPageComponent>;
  let testHelper: TestHelper<AddPageComponent>;

  const booksServiceMock = {
    add: (book: Book) => {
      return book;
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddPageComponent],
      imports: [ReactiveFormsModule],
      providers: [{ provide: BookService, useValue: booksServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(AddPageComponent);
    component = fixture.componentInstance;
    testHelper = new TestHelper(fixture);
    fixture.detectChanges();
  });

  it('should call the BookService to add a new book', () => {
    // Spy sur la méthode add du service.
    const addSpy = spyOn(booksServiceMock, 'add');

    // On rempli le formulaire
    const titleInput = testHelper.getInput('title-input');
    testHelper.writeInInput(titleInput, 'title');
    const authorInput = testHelper.getInput('author-input');
    testHelper.writeInInput(authorInput, 'author');
    const descriptionInput = testHelper.getInput('description-input');
    testHelper.writeInInput(descriptionInput, 'desc');
    const nbPagesInput = testHelper.getInput('nb-pages-input');
    testHelper.writeInInput(nbPagesInput, '1');

    // On force la détection de changement.
    fixture.detectChanges();

    // On simule un clique sur le bouton.
    const button = testHelper.getButton('add-button');
    button.click();

    // On valide l'appel au service.
    expect(addSpy).toHaveBeenCalledWith({
      id: null,
      title: 'title',
      author: 'author',
      description: 'desc',
      nbPages: 1,
    });

    // On valide le reset du formulaire.
    expect(component.form.pristine).toBe(true);
  });

  it('should not allow empty values', () => {
    const addSpy = spyOn(booksServiceMock, 'add');

    const authorInput = testHelper.getInput('author-input');
    testHelper.writeInInput(authorInput, 'author');

    const button = testHelper.getButton('add-button');
    button.click();

    expect(addSpy).not.toHaveBeenCalled();
  });
});
