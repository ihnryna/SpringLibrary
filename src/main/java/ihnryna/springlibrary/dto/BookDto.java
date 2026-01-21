package ihnryna.springlibrary.dto;

public class BookDto extends LibraryItemDto {
    private String isbn;
    private String author;

    public BookDto() {
    }

    public BookDto(String isbn, String author) {
        this.isbn = isbn;
        this.author = author;
    }

    public BookDto(Long id, String title, Integer publishedYear, Boolean available, String isbn, String author) {
        super(id, title, publishedYear, available);
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
