package ihnryna.springlibrary.model;

import jakarta.persistence.*;

@Entity
@NamedQuery(
        name = "Book.updateAvailableFalseByAuthor",
        query = "UPDATE Book b SET b.available = false WHERE b.author = :author"
)
@NamedQuery(
        name = "Book.maxYearByAuthor",
        query = "SELECT b.author, MAX(b.publishedYear) " +
                "FROM Book b " +
                "GROUP BY b.author " +
                "ORDER BY b.author"
)
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
