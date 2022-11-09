import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { BookService } from './book.service';

describe('BooksService', () => {
  let service: BookService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    // On importe le HttpClientTestingModule pour avoir accès au HttpTestingController
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(BookService);
  });

  it('should call get on the backend api', () => {
    const book = {
      id: '12233',
      title: 'title',
      author: 'author',
      description: 'desc',
      nbPages: 1,
    };

    // On exécute la requête au service
    service.get(1, 'asc').then((r) => {
      // Ce code sera exécuté lorsque la réponse sera reçue.
      expect(r).toEqual([book]);
    });

    // On valide que l'appel HTTP est fait correctement
    const req = httpTestingController.expectOne(
      'http://127.0.0.1:8080/books?limit=1&order=asc'
    );
    expect(req.request.method).toEqual('GET');
    expect(req.request.params.get('limit')).toEqual('1');

    // On envoie la réponse.
    req.flush([book]);
  });

  it('should call post on the backend api when adding a book', () => {
    const book = {
      id: null,
      title: 'title',
      author: 'author',
      description: 'desc',
      nbPages: 1,
    };

    service.add(book).then((r) => {
      expect(r).toEqual({ ...book, id: 'id' });
    });

    const req = httpTestingController.expectOne('http://127.0.0.1:8080/books');
    expect(req.request.method).toEqual('POST');

    req.flush({ ...book, id: 'id' });
  });
});
