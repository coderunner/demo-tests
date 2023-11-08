package com.inf5190.library.books;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.inf5190.library.books.model.Book;
import com.inf5190.library.books.repository.FirestoreBook;

/**
 * On indique à Spring d'exécuter le test dans le conteneur web sur un port
 * aléatoire.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:firebase.properties")
public class ITestBooksController {
    private static final Book TEST_BOOK = new Book("id", "title", "author", "desc", 122);

    /**
     * Spring nous injecte le nom du projet firebase, le port de l'émulateur et le
     * port du serveur.
     * 
     * Il nous injecte aussi un client http (TestRestTemplate) et un pointeur vers
     * Firestore.
     */
    @Value("${firebase.project.id}")
    private String firebaseProjectId;

    @Value("${firebase.emulator.port}")
    private String emulatorPort;

    @LocalServerPort
    private int port;

    /**
     * '@Autowired' indique à Spring d'assigner les Beans ici après la création du
     * l'objet de test.
     */
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Firestore firestore;

    /**
     * Méthode appelée avant chaque test.
     * 
     * Elle insert des données spécifique dans firestore.
     */
    @BeforeEach
    public void setup() throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> future1 = this.firestore.collection("books").document()
                .create(new FirestoreBook("test1", "test1", "test1", 111));
        future1.get();
        ApiFuture<WriteResult> future2 = this.firestore.collection("books").document()
                .create(new FirestoreBook("test2", "test2", "test2", 222));
        future2.get();
    }

    /**
     * Méthode appelée après chaque test (qu'il passe ou échoue).
     * 
     * Elle nettoie les données de tests dans l'émulateur.
     */
    @AfterEach
    public void testDown() {
        restTemplate.delete(
                "http://localhost:" + this.emulatorPort + "/emulator/v1/projects/" + this.firebaseProjectId
                        + "/databases/(default)/documents");
    }

    @Test
    public void getBooks() {
        final Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books", Book[].class);
        assertThat(books.length).isEqualTo(2);
        assertThat(books[0].getTitle()).isEqualTo("test1");
        assertThat(books[1].getTitle()).isEqualTo("test2");
    }

    @Test
    public void getBooksAsc() {
        final Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books?order=asc", Book[].class);
        assertThat(books.length).isEqualTo(2);
        assertThat(books[0].getTitle()).isEqualTo("test1");
        assertThat(books[1].getTitle()).isEqualTo("test2");
    }

    @Test
    public void getBooksDesc() {
        final Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books?order=desc", Book[].class);
        assertThat(books.length).isEqualTo(2);
        assertThat(books[0].getTitle()).isEqualTo("test2");
        assertThat(books[1].getTitle()).isEqualTo("test1");
    }

    @Test
    public void getBooksWithLimit() {
        final Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books?limit=1", Book[].class);
        assertThat(books.length).isEqualTo(1);
    }

    @Test
    public void addBook() {
        restTemplate.postForObject("http://localhost:" + port + "/books", TEST_BOOK, Book.class);

        final Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books", Book[].class);
        final List<Book> bookList = Arrays.asList(books);

        assertThat(books.length).isEqualTo(3);
        assertThat(bookList.stream().filter(b -> b.getTitle().equals(TEST_BOOK.getTitle())).toList().size())
                .isEqualTo(1);
    }
}
