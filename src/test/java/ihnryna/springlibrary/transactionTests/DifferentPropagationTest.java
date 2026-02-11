package ihnryna.springlibrary.transactionTests;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import ihnryna.springlibrary.service.transactionExperiments.DifferentPropagationsReaderAccountService;
import ihnryna.springlibrary.service.transactionExperiments.LibraryManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@Sql("/data3.sql")
public class DifferentPropagationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private LibraryManager manager;
    @Autowired
    private LibraryItemRepository libraryRepo;
    @Autowired
    private ReaderAccountRepository readerRepo;
    @Autowired
    private DifferentPropagationsReaderAccountService readerService;

    @Test
    void testRequiredPropagation() {
        assertThrows(RuntimeException.class, () -> manager.scenarioRequired(1L, 1L));

        LibraryItem book = libraryRepo.findById(1L).orElseThrow();
        assert(book.getAvailable());

        ReaderAccount reader = readerRepo.findById(1L).orElseThrow();
        assert(reader.getBalance() == 50);
    }

    @Test
    void testRequiresNewPropagation() {
        assertThrows(RuntimeException.class, () -> manager.scenarioRequiresNew(1L, 1L));

        LibraryItem book = libraryRepo.findById(1L).orElseThrow();
        assert(book.getAvailable());

        ReaderAccount reader = readerRepo.findById(1L).orElseThrow();
        assert(reader.getBalance() == 40);
    }

    @Test
    void testNestedCatchesPropagation() {
        manager.scenarioNested(1L, 1L);

        LibraryItem book = libraryRepo.findById(1L).orElseThrow();
        assert(!book.getAvailable());

        ReaderAccount reader = readerRepo.findById(1L).orElseThrow();
        assert(reader.getBalance() == 50);
    }

    @Test
    void testNestedPropagation() {
        assertThrows(RuntimeException.class, () -> manager.scenarioNestedNotCatches(1L, 1L));

        LibraryItem book = libraryRepo.findById(1L).orElseThrow();
        assert(book.getAvailable());

        ReaderAccount reader = readerRepo.findById(1L).orElseThrow();
        assert(reader.getBalance() == 50);
    }
}
