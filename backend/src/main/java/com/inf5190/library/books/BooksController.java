package com.inf5190.library.books;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inf5190.library.books.model.Book;
import com.inf5190.library.books.repository.BooksRepository;

import io.micrometer.core.annotation.Timed;

@RestController
public class BooksController {

    private final BooksRepository booksRepository;

    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Timed("com.inf5190.library.books.BooksController.get")
    @GetMapping("/books")
    public List<Book> get(
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "order", defaultValue = "asc") Order order)
            throws InterruptedException, ExecutionException {
        return this.booksRepository.getBooks(limit, order);
    }

    @Timed("com.inf5190.library.books.BooksController.add")
    @PostMapping("/books")
    public Book add(@RequestBody Book book) throws InterruptedException, ExecutionException {
        return this.booksRepository.addBook(book);
    }

}
