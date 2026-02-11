package ihnryna.springlibrary.transactionTests;

import ihnryna.springlibrary.service.transactionExperiments.LibraryEntityManagerTxComponent;
import ihnryna.springlibrary.service.transactionExperiments.LibraryTransactionTemplateComponent;
import ihnryna.springlibrary.service.transactionExperiments.LibraryTransactionalComponent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Testcontainers
@Sql("/data3.sql")
public class TransactionsTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    LibraryTransactionalComponent transactionalComponent;
    @Autowired
    LibraryTransactionTemplateComponent transactionTemplateComponent;
    @Autowired
    LibraryEntityManagerTxComponent entityManagerTxComponent;

    @Test
    void transactionalMethodsShouldOpenTransactions() {
        assertTrue(transactionalComponent.borrowBook(1L, 1L));
        assertTrue(transactionalComponent.returnBook(1L));
        assertTrue(transactionalComponent.markBookUnavailableByTitle("Clean Architecture"));
    }

    @Test
    void transactionTemplateMethodsShouldOpenTransactions() {
        assertTrue(transactionTemplateComponent.borrowBook(1L, 1L));
        assertTrue(transactionTemplateComponent.returnBook(1L));
        assertTrue(transactionTemplateComponent.markBookUnavailableByTitle("Clean Architecture"));
    }

    @Test
    void entityManagerTxComponentMethodsShouldOpenTransactions() {
        assertTrue(entityManagerTxComponent.borrowBook(1L, 1L));
        assertTrue(entityManagerTxComponent.returnBook(1L));
        assertTrue(entityManagerTxComponent.markBookUnavailableByTitle("Clean Architecture"));
    }
}
