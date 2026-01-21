package ihnryna.springlibrary;


import ihnryna.springlibrary.dto.LibraryItemDto;
import ihnryna.springlibrary.service.LibraryItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test-sql")
public class LibraryItemServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private LibraryItemService service;

    @Test
    void testWithoutSchemaSql_shouldFail() {
        Exception exception = assertThrows(DataAccessException.class, () -> {
            service.countAllItems();
        });
    }

    @Sql("/schema.sql")
    @Test
    @Transactional
    void testFindAllWithoutData_returnsZero() {
        List<LibraryItemDto> allItems = service.findAll();
        assertEquals(0, allItems.size());
    }

    @Sql("/schema.sql")
    @Sql("/data2.sql")
    @Test
    @Transactional
    void testFindAll() {
        List<LibraryItemDto> allItems = service.findAll();
        assertEquals(3, allItems.size());
    }

}