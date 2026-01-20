package ihnryna.springlibrary;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import ihnryna.springlibrary.repository.LibraryItemDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Sql("/schema.sql")
@Sql("/data.sql")
class LibraryItemJdbcTemplateTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private LibraryItemDAO libraryItemDAO;

    @Test
    void testCountRows() {
        List<LibraryItem> items = libraryItemDAO.listLibraryItems();
        assertEquals(4, items.size(), "У базі має бути 4 елементи після data.sql");
    }

    @Test
    void testListBookTitles() {
        List<Book> books = libraryItemDAO.listBooks();
        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Clean Code")));
    }

    @Test
    void testInsertLibraryItem() {
        Magazine mag = new Magazine("Science Weekly", 2025, true, 101, "March");
        libraryItemDAO.createLibraryItem(mag);

        List<LibraryItem> items = libraryItemDAO.listLibraryItems();
        assertEquals(5, items.size());

        List<Magazine> magazines = libraryItemDAO.listMagazines();
        assertTrue(magazines.stream().anyMatch(m -> m.getTitle().equals("Science Weekly")));
    }
}
