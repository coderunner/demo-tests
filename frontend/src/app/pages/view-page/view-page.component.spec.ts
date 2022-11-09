import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPageComponent } from './view-page.component';
import { BookService } from 'src/app/services/book.service';
import { TestHelper } from 'src/app/tests/test-helper';

// Nom de la série de test.
describe('ViewPageComponent', () => {
  let component: ViewPageComponent;
  let fixture: ComponentFixture<ViewPageComponent>;
  let testHelper: TestHelper<ViewPageComponent>;

  // On se défini un mock pour simuler le BookService.
  // Nous avons seulement besoin de définir les méthodes qui seront utiliser dans le tests.
  // Le comportement sera définit dans le test.
  const bookServiceMock = {
    get: async (limit: number, order: 'asc' | 'desc') => {
      return null;
    },
  };

  beforeEach(async () => {
    // Toujours valider les imports et les providers.
    await TestBed.configureTestingModule({
      declarations: [ViewPageComponent],
      imports: [],
      // On défini un provider pour le test pour qu'Angular nous donne notre stub et non le vrai service.
      providers: [{ provide: BookService, useValue: bookServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewPageComponent);
    component = fixture.componentInstance;
    testHelper = new TestHelper(fixture);
    fixture.detectChanges();
  });

  it('should call the BookService with the limit', () => {
    // On crée un spy sur la méthode get de notre stub.
    const getSpy = spyOn(bookServiceMock, 'get');

    // On simule le clique sur le bouton.
    const button = testHelper.getButton('more-button');
    button.click();

    // On peut valider les appels au stub.
    expect(getSpy).toHaveBeenCalledWith(2, 'asc');

    button.click();
    expect(getSpy).toHaveBeenCalledWith(3, 'asc');
  });

  it('should call the BookService with the order', () => {
    const getSpy = spyOn(bookServiceMock, 'get');

    const button = testHelper.getButton('order-button');
    button.click();

    expect(getSpy).toHaveBeenCalledWith(1, 'desc');

    button.click();
    expect(getSpy).toHaveBeenCalledWith(1, 'asc');
  });
});
