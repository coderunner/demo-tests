package com.inf5190.library.books.model;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String description;
    private final int nbPages;

    public Book(String id, String title, String author, String description, int nbPages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.nbPages = nbPages;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getNbPages() {
        return nbPages;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
                + ", nbPages=" + nbPages + "]";
    }

}
