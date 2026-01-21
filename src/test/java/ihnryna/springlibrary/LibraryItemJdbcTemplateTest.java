package ihnryna.springlibrary;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test-sql")
class LibraryItemJdbcTemplateTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private LibraryItemRepository repository;

    @Test
    void testCountAllItems() {
        int count = repository.countAllItems();
        assertEquals(4, count);
    }

    @Test
    void testFindItemsPublishedAfter() {
        List<LibraryItem> recentItems = repository.findItemsPublishedAfter(2010);
        assertFalse(recentItems.isEmpty());
        assertEquals(2, recentItems.size());
        for (LibraryItem item : recentItems) {
            assertTrue(item.getPublishedYear() > 2010);
        }
    }

    @Test
    void testFindAllBookAuthors() {
        List<String> authors = repository.findAllBookAuthors();
        assertEquals(2, authors.size());
        assertTrue(authors.contains("Joshua Bloch"));
        assertTrue(authors.contains("Robert C. Martin"));
    }
}
