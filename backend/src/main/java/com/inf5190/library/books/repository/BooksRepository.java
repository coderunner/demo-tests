package com.inf5190.library.books.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;
import com.inf5190.library.books.Order;
import com.inf5190.library.books.model.Book;

@Repository
public class BooksRepository {
    // Pour illustrer un leak
    private List<Book> leak = new ArrayList<>();
    private Firestore firestore;

    public BooksRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // Ajout du mot clé synchronised pour illustrer
    synchronized public List<Book> getBooks(Integer requestedLimit, Order order)
            throws InterruptedException, ExecutionException {

        final Query query = this.firestore.collection("books");
        final Integer limit = requestedLimit != null ? requestedLimit : 20;
        final Direction direction = order == Order.asc ? Direction.ASCENDING : Direction.DESCENDING;
        final QuerySnapshot snapshot = query.orderBy("title", direction).limit(limit).get().get();

        List<Book> books = snapshot.getDocuments().stream().map(d -> {
            FirestoreBook book = d.toObject(FirestoreBook.class);
            return new Book(d.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getNbPages());
        }).toList();

        this.leak.addAll(books);
        return books;
    }

    public Book addBook(Book book) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentReference> future = this.firestore.collection("books")
                .add(new FirestoreBook(book.getTitle(), book.getAuthor(), book.getDescription(), book.getNbPages()));

        DocumentReference doc = future.get();
        return new Book(doc.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getNbPages());
    }

}
