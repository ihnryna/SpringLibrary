package ihnryna.springlibrary;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import ihnryna.springlibrary.repository.LibraryItemManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class LibraryItemManagerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private LibraryItemManager manager;

    @Test
    void persist_shouldMakeEntityFindable() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2024);
        book.setAvailable(true);

        manager.persist(book);

        LibraryItem found = manager.find(book.getId());

        assertNotNull(found);
        assertEquals("Test Book", found.getTitle());
    }

    @Test
    void managedEntity_shouldAutoUpdateInDatabase() {
        Book book = new Book();
        book.setTitle("Original Title");
        book.setAuthor("Original Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2024);
        book.setAvailable(true);

        manager.persist(book);

        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");

        LibraryItem found = manager.find(book.getId());

        assertNotNull(found);
        assertEquals("Updated Title", found.getTitle());
        assertEquals("Updated Author", ((Book) found).getAuthor());
    }

    @Test
    void detach_shouldDisableAutoUpdate() {
        Book book = managedBookCreate();

        manager.detach(book);
        book.setTitle("Changed title");

        Book fresh = (Book) manager.find(book.getId());

        assertNotEquals("Changed title", fresh.getTitle());
    }

    @Test
    void merge_shouldUpdateDetachedEntity() {
        Long bookId = managedBookCreate().getId();

        Book book = (Book) manager.find(bookId);
        manager.detach(book);

        book.setTitle("Merged title");

        LibraryItem merged = manager.merge(book);

        assertEquals("Merged title", merged.getTitle());
    }

    @Test
    void refresh_shouldDiscardLocalChanges() {
        Magazine magazine = managedMagazineCreate();

        String originalTitle = magazine.getTitle();
        magazine.setTitle("Temporary title");

        manager.refresh(magazine);

        assertEquals(originalTitle, magazine.getTitle());
    }

    @Test
    void remove_shouldDeleteEntity() {
        LibraryItem book = managedBookCreate();

        manager.remove(book);

        LibraryItem deleted = manager.find(1L);
        assertNull(deleted);
    }

    Book managedBookCreate(){
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2024);
        book.setAvailable(true);
        manager.persist(book);
        return book;
    }

    Magazine managedMagazineCreate(){
        Magazine mag1 = new Magazine();
        mag1.setTitle("Magazine One");
        mag1.setIssueNumber(1);
        mag1.setMonth("January");
        mag1.setPublishedYear(2023);
        mag1.setAvailable(true);
        manager.persist(mag1);
        return mag1;
    }

}


