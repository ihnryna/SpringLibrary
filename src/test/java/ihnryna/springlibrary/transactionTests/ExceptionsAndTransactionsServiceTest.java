package ihnryna.springlibrary.transactionTests;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import ihnryna.springlibrary.service.transactionExperiments.ExceptionsAndTransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Sql("/data3.sql")
public class ExceptionsAndTransactionsServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    ExceptionsAndTransactionsService service;

    @Autowired
    LibraryItemRepository repository;

    @Autowired
    ReaderAccountRepository readerRepository;

    @Test
    void checkedExceptionShouldRollback() {
        Book book = (Book) repository.findLibraryItemByTitle("Effective Java")
                .orElseThrow(() -> new AssertionError("Book 'Effective Java' not found"));
        String author = book.getAuthor();

        assertThrows(Exception.class, () -> service.updateAuthorAndFailChecked(book.getTitle(), "Fake author"));

        Book newBook = (Book) repository.findLibraryItemByTitle("Effective Java")
                .orElseThrow(() -> new AssertionError("Book 'Effective Java' not found"));

        assertEquals(author, newBook.getAuthor());
    }

    @Test
    void runtimeExceptionShouldCommit() {
        Book book = (Book) repository.findLibraryItemByTitle("Effective Java")
                .orElseThrow(() -> new AssertionError("Book 'Effective Java' not found"));
        String author = book.getAuthor();

        assertThrows(RuntimeException.class, () -> service.updateAuthorAndFailRuntime(book.getTitle(), "Fake author"));

        Book newBook = (Book) repository.findLibraryItemByTitle("Effective Java")
                .orElseThrow(() -> new AssertionError("Book 'Effective Java' not found"));

        assertEquals("Fake author", newBook.getAuthor());
        assertNotEquals(author, newBook.getAuthor());
    }

    @Test
    void transactionNotOpenedOnSelfInvocation() {
        assertFalse(service.first());
    }

    @Test
    void transactionOpenedWhenCalledViaProxy() {
        assertTrue(service.firstWithOuter());
    }

    @Test
    void serializableIsolationShouldFailOneTransaction() throws Exception {
        ReaderAccount reader = (ReaderAccount) readerRepository.findBySurname("Hryshchenko").orElseThrow();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Void> t1 = () -> {
            service.withdraw(reader.getSurname(), 30);
            return null;
        };

        Callable<Void> t2 = () -> {
            service.withdraw(reader.getSurname(), 25);
            return null;
        };

        Future<Void> f1 = executor.submit(t1);
        Future<Void> f2 = executor.submit(t2);

        assertThrows(ExecutionException.class, () -> {
            f1.get();
            f2.get();
        });
    }



}
