import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TestHelper } from 'src/app/tests/test-helper';

import { DisplayBooksComponent } from './display-books.component';

describe('DisplayBooksComponent', () => {
  let component: DisplayBooksComponent;
  let fixture: ComponentFixture<DisplayBooksComponent>;
  let testHelper: TestHelper<DisplayBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisplayBooksComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DisplayBooksComponent);
    component = fixture.componentInstance;
    testHelper = new TestHelper(fixture);
    fixture.detectChanges();
  });

  it('should not display books if empty', () => {
    fixture.detectChanges();
    const title = testHelper.getElement('title');
    expect(title).toBeUndefined();
  });

  it('should display books', () => {
    // On assigne une valeur au Input du composant.
    component.books = [
      {
        id: '123',
        title: 'title',
        author: 'author',
        description: 'desc',
        nbPages: 12,
      },
      {
        id: '1232',
        title: 'title2',
        author: 'author2',
        description: 'desc2',
        nbPages: 122,
      },
    ];

    // On force la détection de changement.
    fixture.detectChanges();

    // On valide l'affichage
    const title = testHelper.getElements('title');
    expect(title).toBeDefined();
    expect(title.length).toBe(2);
    expect(title[0].innerText).toEqual('Titre: title');
    expect(title[1].innerText).toEqual('Titre: title2');
  });
});
