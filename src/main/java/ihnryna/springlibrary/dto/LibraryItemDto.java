package ihnryna.springlibrary.dto;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;

public class LibraryItemDto {

    private Long id;
    private String title;
    private Integer publishedYear;
    private Boolean available;

    private String isbn;
    private String author;

    private Integer issueNumber;
    private String month;

    public LibraryItemDto(Long id, String title, Integer publishedYear, Boolean available) {
        this.id = id;
        this.title = title;
        this.publishedYear = publishedYear;
        this.available = available;
    }

    public LibraryItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public static LibraryItemDto fromEntity(LibraryItem item) {
        LibraryItemDto dto = new LibraryItemDto();
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setPublishedYear(item.getPublishedYear());
        dto.setAvailable(item.getAvailable());

        if (item instanceof Book book) {
            dto.setIsbn(book.getIsbn());
            dto.setAuthor(book.getAuthor());
        }

        if (item instanceof Magazine mag) {
            dto.setIssueNumber(mag.getIssueNumber());
            dto.setMonth(mag.getMonth());
        }

        return dto;
    }

    public LibraryItem toEntity() {
        if (isbn != null) {
            return new Book(title, publishedYear, available, isbn, author);
        }
        return new Magazine(title, publishedYear, available, issueNumber, month);
    }
}
