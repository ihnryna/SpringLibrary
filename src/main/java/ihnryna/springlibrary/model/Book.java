package ihnryna.springlibrary.model;

import jakarta.persistence.*;

@Entity
public class Book extends LibraryItem {

    private String isbn;
    private String author;

    public Book() {
    }

    public Book(String title, Integer publishedYear, Boolean available, String isbn, String author) {
        super(title, publishedYear, available);
        this.isbn = isbn;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
