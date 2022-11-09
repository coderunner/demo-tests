package com.inf5190.library.books;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.inf5190.library.books.model.Book;
import com.inf5190.library.books.repository.BooksRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Annotation pour exécuter les tests avec la configuration Spring.
 */
@SpringBootTest
public class TestBooksController {
    private static final Book TEST_BOOK = new Book("id", "title", "author", "desc", 122);

    /**
     * Autowired indique à Spring d'assigner l'attribut automatiquement selon la
     * définition du bean demandé.
     */
    @Autowired
    private BooksController controller;

    /**
     * Fourni un mock pour le bean demandé. Nous pourrons ensuite définir le
     * comportement du mock de façon programmatique.
     */
    @MockBean
    private BooksRepository repository;

    /**
     * Annotation qui indique que la méthode est un test à exécuter.
     */
    @Test
    public void getBooks() throws InterruptedException, ExecutionException {
        // Définition de ce qui est attendu.
        final List<Book> expectedBooks = new ArrayList<Book>();
        expectedBooks.add(TEST_BOOK);

        // On définit le comportement du mock.
        when(repository.getBooks(20, Order.asc)).thenReturn(expectedBooks);

        // On exécute le test.
        final List<Book> actualBooks = controller.get(20, Order.asc);

        // On valide le résultat.
        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    public void addBook() throws InterruptedException, ExecutionException {
        final Book book = new Book(null, TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getDescription(),
                TEST_BOOK.getNbPages());

        when(repository.addBook(book)).thenReturn(TEST_BOOK);

        final Book actualBook = controller.add(book);

        assertThat(actualBook).isEqualTo(TEST_BOOK);
    }
}
